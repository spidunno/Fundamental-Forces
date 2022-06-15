package com.sammy.fufo.common.blockentity;

import java.util.List;

import com.sammy.fufo.core.systems.logistics.FluidPipeNetwork;
import com.sammy.fufo.core.systems.logistics.PipeNode;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

// Still a WIP
public class SealedBarrelBlockEntity extends OrtusBlockEntity implements PipeNode {

	private FluidTank storage;
	private FluidPipeNetwork network;
	public SealedBarrelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		storage = new FluidTank(8000);
	}

	public int getStored() {
		return storage.getFluidAmount();
	}
	
	public Fluid getFluid() {
		return storage.getFluid().getFluid();
	}

	@Override
	public FluidStack getStoredFluid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PipeNode> getConnectedNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addConnection(BlockPos bp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeConnection(BlockPos bp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BlockPos getPos() {
		return getBlockPos();
	}

	@Override
	public double getPressure() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setNetwork(FluidPipeNetwork network, boolean reciprocate) {
		this.network = network;
	}

	@Override
	public FluidPipeNetwork getNetwork() {
		// TODO Auto-generated method stub
		return network;
	}
	
	public void setTarget(BlockPos target) {
		
	}

	@Override
	public FluidStack addFluid(FluidStack stack) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void transferFluid(int amount, PipeNode dest) {
		this.getStoredFluid().shrink(amount);
		dest.addFluid(new FluidStack(getStoredFluid().getFluid(), amount));
		this.setChanged();
	}

	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return 8000;
	}
}