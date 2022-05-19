package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.core.setup.content.block.BlockEntityRegistry;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BurnerExtractorBlockEntity extends OrtusBlockEntity {
    public BurnerExtractorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public BurnerExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.BURNER_EXTRACTOR.get(), pos, state);
    }
}
