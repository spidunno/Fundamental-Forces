package com.project_esoterica.esoterica.common.blockentity;

import com.project_esoterica.esoterica.core.registry.block.BlockEntityRegistry;
import com.project_esoterica.esoterica.core.systems.blockentity.SimpleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MeteorFlameBlockEntity extends SimpleBlockEntity {
    public MeteorFlameBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public MeteorFlameBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.METEOR_FLAME.get(), pos, state);
    }
}
