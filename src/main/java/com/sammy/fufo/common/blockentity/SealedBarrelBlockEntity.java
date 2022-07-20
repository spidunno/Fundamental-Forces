package com.sammy.fufo.common.blockentity;

import java.util.List;

import com.sammy.fufo.core.systems.logistics.FlowDir;
import com.sammy.fufo.core.systems.logistics.FluidPipeNetwork;
import com.sammy.fufo.core.systems.logistics.PipeNode;
import com.sammy.fufo.core.systems.logistics.PressureSource;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

// Still a WIP
// How long has this been on the backburner for?
public class SealedBarrelBlockEntity extends OrtusBlockEntity implements PipeNode {

	private FluidTank storage;
	private FluidPipeNetwork network;
	private PipeNode connection;
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
		return storage.getFluid();
	}

	@Override
	public List<PipeNode> getConnectedNodes() {
		// TODO Auto-generated method stub
		return List.of(connection);
	}

	@Override
	public boolean addConnection(BlockPos bp) {
		connection = (PipeNode)getLevel().getBlockEntity(bp);
		return true;
	}

	@Override
	public boolean removeConnection(BlockPos bp) {
		connection = null;
		return true;
	}

	@Override
	public BlockPos getPos() {
		return getBlockPos();
	}
	
	public void setNetwork(FluidPipeNetwork network, boolean reciprocate) {
		this.network = network;
	}

	@Override
	public FluidPipeNetwork getNetwork() {
		// TODO Auto-generated method stub
		return network;
	}

	@Override
	public FluidStack addFluid(Fluid f, double amt) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void transferFluid(double amount, PipeNode dest) {
		this.getStoredFluid().shrink((int)amount);
		dest.addFluid(getStoredFluid().getFluid(), amount);
		this.setChanged();
	}

	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return 8000;
	}

	@Override
	public void updateSource(PressureSource p, FlowDir dir, double dist) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getDistFromSource(PressureSource p, FlowDir d) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPressure() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTarget(BlockPos target) {
		// TODO Auto-generated method stub
		
	}
}