package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.core.registratation.BlockEntityRegistrate;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BurnerExtractorBlockEntity extends OrtusBlockEntity {
    public BurnerExtractorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public BurnerExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistrate.BURNER_EXTRACTOR.get(), pos, state);
    }
}
