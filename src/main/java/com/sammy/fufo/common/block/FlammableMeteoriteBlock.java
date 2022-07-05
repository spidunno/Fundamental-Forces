package com.sammy.fufo.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FlammableMeteoriteBlock extends Block {

    public static final IntegerProperty DEPLETION_STATE = IntegerProperty.create("depletion", 0, 4);

    private final FlameStateProvider stateProvider;

    public FlammableMeteoriteBlock(Properties properties, FlameStateProvider stateProvider) {
        super(properties);
        this.stateProvider = stateProvider;
        this.registerDefaultState(this.defaultBlockState().setValue(DEPLETION_STATE, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DEPLETION_STATE);
    }

    public BlockState getFlameState(BlockState state, BlockPos pos) {
        return stateProvider.getFlameState(state, pos);
    }

    public interface FlameStateProvider {
        BlockState getFlameState(BlockState state, BlockPos pos);
    }
}