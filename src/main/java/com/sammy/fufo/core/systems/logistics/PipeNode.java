package com.sammy.fufo.core.systems.logistics;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface PipeNode {
	public FluidStack getStoredFluid();
	
	/**
	 * Add a fluid to the node.
	 * @param stack
	 * @return Any fluid not added
	 */
	public FluidStack addFluid(Fluid fluid, double amount);
	
	public void transferFluid(double amount, PipeNode dest);
	
	public List<PipeNode> getConnectedNodes();
	
	public void addConnection(BlockPos bp);
	
	public void removeConnection(BlockPos bp);
	
	public BlockPos getPos();
	
	/**
	 * Returns the "base" pressure of the node. Note that this does NOT include external factors
	 * such as gravity, other nodes, neighbouring pumps, etc
	 * @return
	 */
	public double getBasePressure();
	
	public void setNetwork(FluidPipeNetwork network, boolean reciprocate);
	
	public FluidPipeNetwork getNetwork();
	
	public void setTarget(BlockPos target);
	
	public int getCapacity();
}