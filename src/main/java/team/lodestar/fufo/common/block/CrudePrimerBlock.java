package team.lodestar.fufo.common.block;

import team.lodestar.fufo.common.blockentity.CrudePrimerBlockEntity;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class CrudePrimerBlock<T extends CrudePrimerBlockEntity> extends LodestoneEntityBlock<T> {

    public final VoxelShape SHAPE = Stream.of(
            Block.box(3, 13, 3, 13, 16, 13),
            Block.box(6, 2, 6, 10, 3, 10),
            Block.box(6, 3, 6, 10, 13, 10),
            Block.box(5, 5, 1, 11, 11, 5),
            Block.box(6, 6, 5, 10, 10, 6),
            Shapes.join(Shapes.join(Block.box(4, 0, 4, 12, 2, 12), Shapes.join(Block.box(10, 0, 3, 13, 2, 6), Shapes.join(Block.box(3, 0, 3, 6, 2, 6), Shapes.join(Block.box(3, 0, 10, 6, 2, 13), Block.box(10, 0, 10, 13, 2, 13), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), Shapes.join(Shapes.join(Block.box(5, 7, 5, 11, 8, 5), Shapes.join(Block.box(5, 7, 11, 11, 8, 11), Shapes.join(Block.box(5, 7, 5, 5, 8, 11), Block.box(11, 7, 5, 11, 8, 11), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), Shapes.join(Block.box(10, 2, 0, 10, 13, 5), Shapes.join(Block.box(11, 2, 10, 16, 13, 10), Shapes.join(Block.box(6, 2, 11, 6, 13, 16), Block.box(0, 2, 6, 5, 13, 6), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public CrudePrimerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
