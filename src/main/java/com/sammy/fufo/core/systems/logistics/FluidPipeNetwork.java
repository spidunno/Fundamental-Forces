package com.sammy.fufo.core.systems.logistics;

import java.util.ArrayList;
import java.util.List;

import com.sammy.fufo.common.blockentity.PipeNodeBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

// Preliminary thoughts:
// - The capacity of a length of pipe depends solely on the distance between nodes
// - Pressure drop can be calculated by the density of the moving fluid and the height distance between nodes
// - If each "pipe network" is guaranteed to only be transporting one fluid (whether that's a pure fluid or a 
// mix; air counts as a fluid) at any given time, each node can know its own pressure. An open pipe end's pressure
// is always equal to ambient.
// - Main challenge seems to be how to best keep track of fluid in transit; I'm inclined to have each node keep 
// all its connections (defined as a length of pipe connected to another node or open end) and have a function 
// to return amount of fluid in a given connection.
// - I feel like open pipe ends should also be considered nodes, even if only internally

// Anyway, I might be able to reuse some code from Inventory Link and also improve that code.
public class FluidPipeNetwork {
	
	public List<PipeNodeBlockEntity> nodes = new ArrayList<>();
	private Fluid storedFluid;
	
	public FluidPipeNetwork() {
	}
	
	public void tick() {
	}
}