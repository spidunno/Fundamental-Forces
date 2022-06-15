package com.sammy.fufo.core.systems.logistics;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public interface PipeNode {
	public FluidStack getStoredFluid();
	
	/**
	 * Add a fluid to the node.
	 * @param stack
	 * @return Any fluid not added
	 */
	public FluidStack addFluid(FluidStack stack);
	
	public void transferFluid(int amount, PipeNode dest);
	
	public List<PipeNode> getConnectedNodes();
	
	public void addConnection(BlockPos bp);
	
	public void removeConnection(BlockPos bp);
	
	public BlockPos getPos();
	
	public double getPressure();
	
	public void setNetwork(FluidPipeNetwork network, boolean reciprocate);
	
	public FluidPipeNetwork getNetwork();
	
	public void setTarget(BlockPos target);
	
	public int getCapacity();
}