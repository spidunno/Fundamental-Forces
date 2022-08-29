package team.lodestar.fufo.common.fluid.fluid_tank;

import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class FluidTankBlock<T extends FluidTankBlockEntity> extends LodestoneEntityBlock<T> {

    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", Shape.class);
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
        return createBlockState(state, pContext.getLevel(), pContext.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        return createBlockState(super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos), pLevel, pCurrentPos);
    }

    public static BlockState createBlockState(BlockState state, LevelAccessor level, BlockPos pos) {
        if (level.getBlockState(pos.above()).getBlock() instanceof FluidTankBlock) {
            state = state.setValue(TOP, false);
        } else {
            state = state.setValue(TOP, true);
        }
        if (level.getBlockState(pos.below()).getBlock() instanceof FluidTankBlock) {
            state = state.setValue(BOTTOM, false);
        } else {
            state = state.setValue(BOTTOM, true);
        }
        return state;
    }
}