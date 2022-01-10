package com.project_esoterica.esoterica.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FlammableMeteoriteBlock extends Block {

    private final FlameStateProvider stateProvider;
    public FlammableMeteoriteBlock(Properties properties, FlameStateProvider stateProvider) {
        super(properties);
        this.stateProvider = stateProvider;
    }

    public BlockState getFlameState(BlockState state, BlockPos pos)
    {
        return stateProvider.getFlameState(state, pos);
    }
    public interface FlameStateProvider
    {
        BlockState getFlameState(BlockState state, BlockPos pos);
    }
}
