package team.lodestar.fufo.common.blockentity;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.world.registry.FluidPipeNetworkRegistry;
import team.lodestar.fufo.core.fluid.FluidStats;
import team.lodestar.fufo.registry.common.FufoBlockEntities;
import team.lodestar.fufo.core.fluid.FlowDir;
import team.lodestar.fufo.core.fluid.FluidPipeNetwork;
import team.lodestar.fufo.core.fluid.PipeBuilderAssistant;
import team.lodestar.fufo.core.fluid.PipeNode;
import team.lodestar.fufo.core.fluid.PressureSource;
import team.lodestar.fufo.unsorted.util.Debuggable;
import team.lodestar.fufo.unsorted.util.DevToolResponse;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Triple;

import static team.lodestar.fufo.unsorted.ForcesThatAreActuallyFundamental.g;

@SuppressWarnings("unused")
public class PipeNodeBlockEntity extends LodestoneBlockEntity implements PipeNode, Debuggable, DevToolResponse {

	private static final int RANGE = 10;

    public ArrayList<BlockPos> nearbyAnchorPositions = new ArrayList<>();
    public ArrayList<PipeNode> nearbyAnchors = new ArrayList<>();
    
    private FluidStack fluid = FluidStack.EMPTY;
    
    private List<Triple<PressureSource, FlowDir, Double>> sources = new ArrayList<>();
    private double partialFill = 0.0;
    private boolean isOpen = false;
    private FluidPipeNetwork network;
    private int networkID;
    private BlockPos target;

    public PipeNodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public PipeNodeBlockEntity(BlockPos pos, BlockState state) {
        super(FufoBlockEntities.ANCHOR.get(), pos, state);
    }

    // TODO: fix this method for the 41261816th time
    public double addFluid(Fluid f, double amount) {
    	double realAmount = Math.min(fluid.getAmount() + partialFill + amount, getCapacity());
    	double toReturn = Math.max(0, realAmount - getCapacity());
    	if (fluid.isEmpty()) {
    		fluid = new FluidStack(f, (int)realAmount);
    		partialFill = realAmount % 1;
    	}
    	else {
    		fluid.setAmount((int)realAmount);
    		partialFill = realAmount % 1;
    	}
    	BlockHelper.updateAndNotifyState(level, getPos());
    	this.setChanged();
    	return toReturn;
    }
    
	public void transferFluid(double amount, PipeNode dest) {
		if (FluidPipeNetwork.MANUAL_TICKING) FufoMod.LOGGER.info(String.format("Sending %s from %s to %s", amount, this, dest)); 
		
		double realAmount = Math.min(dest.getCapacity() - dest.getFluidAmount(), amount);
		dest.addFluid(fluid.getFluid(), realAmount);
		double remaining = this.getFluidAmount() - realAmount;
		partialFill = remaining % 1;
		fluid.setAmount((int)remaining);
		BlockHelper.updateAndNotifyState(level, getPos());
		this.setChanged();
	}

    // this method is held together by duct tape and bubble gum
    private static final double DISTANCE_COEFF = 0.1;
    public double getPressure() {
    	double pressure = 0;
    	
    	// Pumps, etc
    	for (Triple<PressureSource, FlowDir, Double> t : sources) {
    		PressureSource source = t.getLeft();
    		FlowDir dir = t.getMiddle();
    		double distance = t.getRight();
    		double contrib = Math.max((source.getForce(dir) - DISTANCE_COEFF * distance), 0);
    		pressure += contrib;
    	}
    	
    	// Height difference from neighbours
    	if ((fluid.getAmount() + partialFill) / getCapacity() > 0.98) { // This might not be needed, idk
	    	for (PipeNode p : nearbyAnchors) {
	    		int dy = p.getPos().getY() - getPos().getY();
	    		pressure += Math.max((p.getFluidAmount()*dy*FluidStats.getInfo(fluid.getFluid()).rho*g)/1000, 0); // Divide by 1000 because 1 mB = 1/1000 m^3
	    	}
    	}
    	return pressure + (getFluidAmount() / 100); // Volume equals pressure, at least a little bit
    	// ignore the height difference if the node has no fluid
//    	return pressure + (fluid.isEmpty() ? 0 : (g * (getPos().getY()+64) * FluidStats.getInfo(fluid.getFluid()).rho));
    }
    
    public boolean isOpen() {
    	return isOpen;
    }
    
    public void setOpen(boolean open) {
    	isOpen = open;
    	setChanged();
    	BlockHelper.updateAndNotifyState(level, getPos());
    }
    
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("network", networkID);
        pTag.put("fluid", fluid.writeToNBT(new CompoundTag()));
        pTag.putDouble("partialFill", partialFill);
        pTag.putBoolean("isOpen", isOpen);
        if (!nearbyAnchorPositions.isEmpty()) {
            CompoundTag compound = new CompoundTag();
            compound.putInt("anchorAmount", nearbyAnchorPositions.size());
            for (int i = 0; i < nearbyAnchorPositions.size(); i++) {
                BlockHelper.saveBlockPos(compound, nearbyAnchorPositions.get(i), "" + i);
            }
            pTag.put("anchorData", compound);
        }
    }

    // When this method is called, check the ID against the FluidPipeNetworkRegistry.
    // If the ID exists in registry, add it to the corresponding network.
    // Otherwise, load the network from NBT into the registry and then add this node to it
    // Probably also check to make sure that this node is supposed to be in the network
    // If the network is not in NBT, create an entry for it
    // Note: level is still null at the time of this method's calling
    // Need to move the network-setting code somewhere else
    // probably to the data capability loading
    @Override
    public void load(CompoundTag pTag) {
//    	Minecraft.getInstance().mouseHandler.releaseMouse();
        super.load(pTag);
        fluid = FluidStack.loadFluidStackFromNBT(pTag.getCompound("fluid"));
        partialFill = pTag.getDouble("partialFill");
        isOpen = pTag.getBoolean("isOpen");
        nearbyAnchorPositions.clear();
        CompoundTag compound = pTag.getCompound("anchorData");
        int amount = compound.getInt("anchorAmount");
        for (int i = 0; i < amount; i++) {
            BlockPos pos = BlockHelper.loadBlockPos(compound, "" + i);
            nearbyAnchorPositions.add(pos);
        }
        networkID = pTag.getInt("network");
//    	int id = pTag.getInt("network");
//    	FufoMod.LOGGER.info("Loading network " + id);
//    	if (level != null) {
//	    	FluidPipeNetworkRegistry registry = FluidPipeNetworkRegistry.getRegistry(level);
//	    	setNetwork(registry.getOrLoadNetwork(pTag.getInt("network")), true);
//    	}
//    	else FufoMod.LOGGER.error("Null world, not loading");
    }
    
    @Override
    public void onPlace(LivingEntity placer, ItemStack stack) {
    	BlockPos prevPos = PipeBuilderAssistant.INSTANCE.prevAnchorPos;
    	if (!level.isClientSide() && prevPos != null && level.getBlockEntity(prevPos) instanceof PipeNode prev && prev.getNetwork() != null) {
    		addConnection(prevPos);
    		prev.addConnection(getPos());
    		setNetwork(prev.getNetwork(), true);
    	}
    	else setNetwork(new FluidPipeNetwork(getLevel()), true);
    	
    	if (!level.isClientSide() && this instanceof PressureSource p) {
    		getNetwork().addSource(p);
    	}
    }
    
    @Override
    public void onLoad() {
    	super.onLoad();
    	for (BlockPos bp : nearbyAnchorPositions) {
    		if (level.getBlockEntity(bp) instanceof PipeNode node) {
    			nearbyAnchors.add(node);
    		}
    		else {
    			nearbyAnchorPositions.remove(bp);
    		}
    	}
//    	FufoMod.LOGGER.info("Running onLoad");
    	if (networkID != 0) { // load was run and NBT data was loaded
    		FluidPipeNetwork net = FluidPipeNetworkRegistry.getRegistry(level).getOrLoadNetwork(networkID);
    		if (network != null) this.setNetwork(net, true);
    	}
    }
    
    // TODO: Have this use nearbyAnchors
    @Override
    public void onBreak(@Nullable Player player) {
    	super.onBreak(player);
    	List<PipeNode> nodes = new ArrayList<>();
    	for (BlockPos bp : nearbyAnchorPositions) {
    		PipeNode node = (PipeNodeBlockEntity) getLevel().getBlockEntity(bp);
    		nodes.add(node);
    		node.removeConnection(this.getBlockPos());
    		BlockHelper.updateState(getLevel(), bp);
    	}
    	if (!level.isClientSide && network != null) network.splitNetwork(nodes);
//    	nearbyAnchorPositions.stream().map(p -> this.getLevel().getBlockEntity(p)).forEach(te -> ((PipeNodeBlockEntity)te).nearbyAnchorPositions.remove(this.getBlockPos()));
    }

    public int countNeighbours() {
    	return nearbyAnchorPositions.size();
    }

	@Override
	public FluidStack getStoredFluid() {
		return fluid;
	}
	

	@Override
	public List<PipeNode> getConnectedNodes() {
		Level level = this.getLevel();
		return nearbyAnchorPositions.stream().map(bp -> (PipeNode)level.getBlockEntity(bp)).toList();
	}

	@Override
	public boolean addConnection(BlockPos bp) {
		if (FluidPipeNetwork.MANUAL_TICKING) FufoMod.LOGGER.info(String.format("%s adding connection to %s", getPos(), bp));
		if (level.getBlockEntity(bp) instanceof PipeNode other) {
			nearbyAnchorPositions.add(bp);
			nearbyAnchors.add(other);
			if (network == null) setNetwork(other.getNetwork(), false);
			else network.mergeWith(other.getNetwork());
			BlockHelper.updateAndNotifyState(level, getPos());
			return true;
		}
		return false;
	}

	@Override
	public boolean removeConnection(BlockPos bp) {
		return nearbyAnchorPositions.remove(bp) && nearbyAnchors.remove((PipeNode)level.getBlockEntity(bp));
	}

	@Override
	public BlockPos getPos() {
		return getBlockPos();
	}

	@Override
	public void setNetwork(FluidPipeNetwork network, boolean reciprocate) {
		this.network = network;
		this.networkID = network.getID();
		if (reciprocate) network.addNode(this, false);
	}

	@Override
	public FluidPipeNetwork getNetwork() {
		// TODO Auto-generated method stub
		return network;
	}
	
	public void setTarget(BlockPos target) {
		this.target = target;
	}

	@Override
	public int getCapacity() {
		return 100;
	}

	@Override
	public void updateSource(PressureSource p, FlowDir dir, double dist) {
		boolean found = false;
		for (Triple<PressureSource, FlowDir, Double> set : sources) {
			if (set.getLeft() == p && set.getMiddle() == dir) set = Triple.of(p, dir, dist); // Will this CME?
			found = true;
		}
		if (!found) {
			sources.add(Triple.of(p, dir, dist));
		}
	}

	@Override
	public double getDistFromSource(PressureSource p, FlowDir dir) {
		for (Triple<PressureSource, FlowDir, Double> set : sources) {
			if (set.getLeft() == p && set.getMiddle() == dir) return set.getRight();
		}
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public String getDebugMessage(boolean sneak) {
		if (sneak) {
			return getNetwork().getInfo();
		}
		else {
			return String.format("Network: %s Pressure: %s with %s (%s) neighbours", getNetwork() == null ? "none" : getNetwork().getID(), getPressure(), nearbyAnchorPositions.size(), nearbyAnchors.size());
		}
	}
	
	@Override
	public void doExtraAction() {
		if (isOpen && !fluid.isEmpty()) {
			double v = this.getFluidAmount();
			v -= this.getPressure() * FluidPipeNetwork.PRESSURE_TRANSFER_COEFF;
			fluid.setAmount((int)v);
			partialFill = v % 1;
		}
	}
	
	@Override
	public double getFluidAmount() {
		return fluid.getAmount() + partialFill;
	}
	
	public String toString() {
		return "NODE at " + getPos();
	}

	@Override
	public void onDevTool(UseOnContext context) {
		if (context.getPlayer().isShiftKeyDown() && FluidPipeNetwork.MANUAL_TICKING) {
    		getNetwork().tick();
    	}
    	else if (context.getPlayer().isShiftKeyDown()) {
    		FufoMod.LOGGER.info("Toggling openness");
    		setOpen(!isOpen());
    	}
    	else {
        	FufoMod.LOGGER.info("Adding water");
        	addFluid(Fluids.WATER, 100.0);
    	}
	}
}