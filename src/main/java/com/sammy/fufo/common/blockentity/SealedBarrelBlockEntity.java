package com.sammy.fufo.common.blockentity;

import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class SealedBarrelBlockEntity extends OrtusBlockEntity {

	private FluidTank storage;
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
}