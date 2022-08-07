package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.core.registratation.BlockEntityRegistrate;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FluidTankBlockEntity extends OrtusBlockEntity {
    public FluidTankBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistrate.FLUID_TANK.get(), pos, state);
    }

    public FluidTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void onNeighborUpdate(BlockState state, BlockPos pos, BlockPos neighbor) {
        super.onNeighborUpdate(state, pos, neighbor);
    }
}