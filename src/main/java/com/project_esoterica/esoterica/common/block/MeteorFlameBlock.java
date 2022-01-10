package com.project_esoterica.esoterica.common.block;

import com.project_esoterica.esoterica.common.blockentity.MeteorFlameBlockEntity;
import com.project_esoterica.esoterica.core.registry.block.BlockEntityRegistry;
import com.project_esoterica.esoterica.core.systems.block.SimpleBlock;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class MeteorFlameBlock extends SimpleBlock<MeteorFlameBlockEntity> {

    public MeteorFlameBlock(Properties properties) {
        super(properties);
        setTile(BlockEntityRegistry.METEOR_FLAME);
    }
}
