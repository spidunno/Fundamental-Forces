package com.sammy.fufo.core.systems.logistics;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public interface PipeNode {
	public FluidStack getStoredFluid();
	
	public void transferFluid();
	
	public List<PipeNode> getConnectedNodes();
	
	public void addConnection(BlockPos bp);
	
	public void removeConnection(BlockPos bp);
	
	public BlockPos getPos();
}