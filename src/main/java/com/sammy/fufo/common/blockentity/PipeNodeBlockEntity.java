package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.core.registratation.BlockEntityRegistrate;
import com.sammy.fufo.core.systems.logistics.FluidPipeNetwork;
import com.sammy.fufo.core.systems.logistics.PipeNode;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public class PipeNodeBlockEntity extends OrtusBlockEntity implements PipeNode {

	/*
	 * Transfer rate per tick = difference in volume times this
	 */

	private static final int RANGE = 10;

    public ArrayList<BlockPos> nearbyAnchorPositions = new ArrayList<>();
    
    private FluidStack fluid = FluidStack.EMPTY;
    
    private double pressure = 0; // Pressure at this node (Pa)
    private boolean isOpen = false;
    private FluidPipeNetwork network;

    public PipeNodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public PipeNodeBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistrate.ANCHOR.get(), pos, state);
    }

    public void addFluid(FluidStack fs) { // yes yes this ignores if the fluid types don't match, it's in dev, ok?
    	if (fluid.isEmpty()) fluid = fs.copy();
    	else fluid.grow(fs.getAmount());
    }
    
    public double getPressure() {
    	return (double)fluid.getAmount();
    }
    
    public boolean isOpen() {
    	return isOpen;
    }
    
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (!nearbyAnchorPositions.isEmpty()) {
            CompoundTag compound = new CompoundTag();
            compound.putInt("anchorAmount", nearbyAnchorPositions.size());
            for (int i = 0; i < nearbyAnchorPositions.size(); i++) {
                BlockHelper.saveBlockPos(compound, nearbyAnchorPositions.get(i), "" + i);
            }
            pTag.put("anchorData", compound);
        }
    	System.out.println("Saved " + pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
    	System.out.println("Loading " + pTag);
        super.load(pTag);
        System.out.println(this.getTileData());
        nearbyAnchorPositions.clear();
//        nearbyAnchors.clear();
        CompoundTag compound = pTag.getCompound("anchorData");
        int amount = compound.getInt("anchorAmount");
        for (int i = 0; i < amount; i++) {
            BlockPos pos = BlockHelper.loadBlockPos(compound, "" + i);
            nearbyAnchorPositions.add(pos);
        }
        System.out.println("Anchors loaded:" + nearbyAnchorPositions.size());
    }
    @Override
    public void onPlace(LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide) {
        	List<PipeNode> nearbyAnchors = BlockHelper.getBlockEntities(PipeNode.class, level, this.getBlockPos(), RANGE);
//            List<PipeNode> nearbyAnchors = BlockHelper.getBlockEntities(PipeNode.class, level, this.getBlockPos(), RANGE);
            nearbyAnchors.remove(this);
            nearbyAnchors.stream().map(PipeNode::getPos).forEach(pos -> addConnection(pos));
//            nearbyAnchorPositions = nearbyAnchors.stream().map(PipeNode::getPos).collect(Collectors.toCollection(ArrayList::new));
            BlockHelper.updateState(level, getBlockPos());
            placer.sendMessage(Component.nullToEmpty("Found " + nearbyAnchors.size() + " anchors"), placer.getUUID());
            for(PipeNode anchor : nearbyAnchors) {
                ArrayList<BlockPos> path = BlockHelper.getPath(this.getBlockPos(), anchor.getPos(), 4, true, level);
                for (BlockPos pos : path) {
//                    System.out.println(pos);
                    if (level.getBlockState(pos).getBlock() == Blocks.AIR || level.getBlockState(pos).getMaterial().isReplaceable()) {
                        level.destroyBlock(pos, true);
                        level.setBlock(pos, Blocks.GLASS.defaultBlockState(), 3);
                    }
                }
                anchor.addConnection(getBlockPos());
            }
        }
    }
    
    @Override
    public void onBreak(@Nullable Player player) {
    	super.onBreak(player);
    	for (BlockPos bp : nearbyAnchorPositions) {
    		PipeNodeBlockEntity te = (PipeNodeBlockEntity) getLevel().getBlockEntity(bp);
    		te.nearbyAnchorPositions.remove(this.getBlockPos());
    		BlockHelper.updateState(getLevel(), bp);
    	}
//    	nearbyAnchorPositions.stream().map(p -> this.getLevel().getBlockEntity(p)).forEach(te -> ((PipeNodeBlockEntity)te).nearbyAnchorPositions.remove(this.getBlockPos()));
    }

    public int countNeighbours() {
    	return nearbyAnchorPositions.size();
    }

	@Override
	public FluidStack getStoredFluid() {
		// TODO Auto-generated method stub
		return null;
	}

	public void transferFluid() {
//		for (PipeNode node : getConnectedNodes()) {
//			double dP = this.getPressure() - node.getPressure();
//			if (dP > 0) {
//				this.getStoredFluid().shrink((int)(dP * PRESSURE_TRANSFER_COEFF));
//				node.getStoredFluid().grow((int)(dP * PRESSURE_TRANSFER_COEFF));
//			}
//		}
//		if (isOpen) {
//			fluid.shrink((int)(fluid.getAmount() * DRAIN_COEFF));
//		}
	}

	@Override
	public List<PipeNode> getConnectedNodes() {
		Level level = this.getLevel();
		return nearbyAnchorPositions.stream().map(bp -> (PipeNode)level.getBlockEntity(bp)).toList();
	}

	@Override
	public void addConnection(BlockPos bp) {
		nearbyAnchorPositions.add(bp);
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
	public void transferFluid(int amount, PipeNode dest) {
		this.fluid.shrink(amount);
		dest.getStoredFluid().grow(amount);
	}

	@Override
	public void setNetwork(FluidPipeNetwork network) {
		this.network = network;
	}

	@Override
	public FluidPipeNetwork getNetwork() {
		// TODO Auto-generated method stub
		return network;
	}
}
