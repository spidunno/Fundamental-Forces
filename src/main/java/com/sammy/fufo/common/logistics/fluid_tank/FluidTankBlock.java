package com.sammy.fufo.common.logistics.fluid_tank;

import com.sammy.ortus.systems.block.OrtusEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class FluidTankBlock<T extends FluidTankBlockEntity> extends OrtusEntityBlock<T> {

    public enum Shape implements StringRepresentable {
        NORMAL, CORNER_SE, CORNER_SW, CORNER_NE, CORNER_NW;

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");

    public FluidTankBlock(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(defaultBlockState().setValue(TOP, true)
            .setValue(BOTTOM, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(TOP, BOTTOM);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = super.getStateForPlacement(pContext);
        if (state == null) {
            return null;
        }
        Level level = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();
        if (level.getBlockState(clickedPos.above()).getBlock() instanceof FluidTankBlock) {
            state = state.setValue(TOP, false);
        }
        if (level.getBlockState(clickedPos.below()).getBlock() instanceof FluidTankBlock) {
            state = state.setValue(BOTTOM, false);
        }
        return state;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        BlockState state = super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
        if (pNeighborPos.equals(pCurrentPos.above())) {
            state = state.setValue(TOP, false);
        } else if (pNeighborPos.equals(pCurrentPos.below())) {
            state = state.setValue(BOTTOM, false);
        }
        return state;
    }
}