package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.world.registry.FluidPipeNetworkRegistry;
import com.sammy.fufo.core.registratation.BlockEntityRegistrate;
import com.sammy.fufo.core.systems.logistics.FluidPipeNetwork;
import com.sammy.fufo.core.systems.logistics.PipeBuilderAssistant;
import com.sammy.fufo.core.systems.logistics.PipeNode;
import com.sammy.ortus.handlers.PlacementAssistantHandler;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public class PipeNodeBlockEntity extends OrtusBlockEntity implements PipeNode {

	private static final int RANGE = 10;

    public ArrayList<BlockPos> nearbyAnchorPositions = new ArrayList<>();
    
    private FluidStack fluid = FluidStack.EMPTY;
    
    private double partialFill = 0.0;
    private boolean isOpen = false;
    private FluidPipeNetwork network;
    private int networkID;
    private BlockPos target;

    public PipeNodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public PipeNodeBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistrate.ANCHOR.get(), pos, state);
    }

    public FluidStack addFluid(Fluid f, double amount) {
    	double realAmount = partialFill + amount;
    	partialFill = realAmount % 1;
    	FluidStack fs = new FluidStack(f, (int)realAmount);
//    	FufoMod.LOGGER.info("Adding fluid");
    	if (fluid.isEmpty()) {
    		fluid = new FluidStack(fs.getFluid(), Math.min(getCapacity(), fs.getAmount()));
    		fs.shrink(fluid.getAmount());
    	}
    	else if (fluid.isFluidEqual(fs)) {
    		int transfer = Math.min(getCapacity() - fluid.getAmount(), fs.getAmount());
    		fluid.grow(transfer);
    		fs.shrink(transfer);
    	}
//    	BlockHelper.updateAndNotifyState(level, getPos());
    	return fs; // return fluid stack unchanged if it doesn't match
    }
    
    // Imperfect, but functional
    public double getBasePressure() {
    	return fluid.getAmount() + getPos().getY()*10;
    }
    
    public boolean isOpen() {
    	return isOpen;
    }
    
    public void setOpen(boolean open) {
    	isOpen = open;
    }
    
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("network", networkID);
        pTag.put("fluid", fluid.writeToNBT(new CompoundTag()));
        pTag.putDouble("partialFill", partialFill);
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
    }
    
    @Override
    public void onLoad() {
    	super.onLoad();
//    	FufoMod.LOGGER.info("Running onLoad");
    	if (networkID != 0) { // load was run and NBT data was loaded
    		FluidPipeNetwork net = FluidPipeNetworkRegistry.getRegistry(level).getOrLoadNetwork(networkID);
    		if (network != null) this.setNetwork(net, true);
    	}
    }
    
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
		// TODO Auto-generated method stub
		return fluid;
	}
	
	public void transferFluid(double amount, PipeNode dest) {
		double realAmount = fluid.getAmount() + partialFill - amount;
		partialFill = realAmount % 1;
		fluid.setAmount((int)realAmount);
		dest.addFluid(fluid.getFluid(), amount);
		BlockHelper.updateAndNotifyState(level, getPos());
		this.setChanged();
	}

	@Override
	public List<PipeNode> getConnectedNodes() {
		Level level = this.getLevel();
		return nearbyAnchorPositions.stream().map(bp -> (PipeNode)level.getBlockEntity(bp)).toList();
	}

	@Override
	public void addConnection(BlockPos bp) {
		FufoMod.LOGGER.info(String.format("%s adding connection to %s", getPos(), bp));
		if (level.getBlockEntity(bp) instanceof PipeNode other) {
			nearbyAnchorPositions.add(bp);
			if (network == null) network = other.getNetwork();
			else network.mergeWith(other.getNetwork());
		}
	}

	@Override
	public void removeConnection(BlockPos bp) {
		nearbyAnchorPositions.remove(bp);
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
		// TODO Auto-generated method stub
		return 100;
	}
}
