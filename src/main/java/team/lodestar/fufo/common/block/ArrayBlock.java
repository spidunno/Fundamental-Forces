package team.lodestar.fufo.common.block;

import team.lodestar.fufo.common.blockentity.ArrayBlockEntity;
import team.lodestar.fufo.common.entity.weave.HologramWeaveEntity;
import team.lodestar.fufo.common.entity.weave.WeaveEntity;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;


public class ArrayBlock<T extends ArrayBlockEntity> extends LodestoneEntityBlock<T> {
    private boolean toggled = false; //TODO: what is this.

    public final VoxelShape SHAPE = Stream.of(
            Shapes.join(Block.box(5, 5, 12, 11, 11, 16), Shapes.join(Block.box(4, 4, 4, 12, 13, 12), Shapes.join(Block.box(5, 7, 3, 7, 14, 3), Block.box(9, 7, 3, 11, 14, 3), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR),
            Shapes.join(Shapes.join(Block.box(4, 13, 0, 12, 16, 4), Shapes.join(Block.box(4, 16.2, 1, 12, 16.2, 3), Shapes.join(Block.box(0, 13, 4, 16, 16, 12), Block.box(4, 13, 12, 12, 16, 16), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), Shapes.join(Block.box(4, 12.75, 4, 12, 12.75, 12), Shapes.join(Block.box(12, 12, 12, 16, 16, 16), Shapes.join(Block.box(0, 12, 12, 4, 16, 16), Shapes.join(Block.box(-0.3, 10.7, -0.3, 4.3, 16.3, 4.3), Block.box(11.7, 10.7, -0.3, 16.3, 16.3, 4.3), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR),
            Shapes.join(Block.box(13, 0, 1, 15, 2, 15), Shapes.join(Block.box(1, 0, 1, 3, 2, 15), Shapes.join(Block.box(14, 2, 1, 14, 13, 15), Shapes.join(Block.box(13, 2, 1, 13, 13, 15), Shapes.join(Block.box(2, 2, 1, 2, 13, 15), Shapes.join(Block.box(3, 2, 1, 3, 13, 15), Shapes.join(Block.box(3, 0, 14, 13, 2, 14), Block.box(3, 0, 2, 13, 2, 2), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public ArrayBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        if(!level.isClientSide && !toggled){
            HologramWeaveEntity hologram = new HologramWeaveEntity(level);
            hologram.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
            WeaveEntity weave1 = new WeaveEntity(level);
            level.addFreshEntity(hologram);
            weave1.setPos(pos.getX() + 1.5, pos.getY() + 2, pos.getZ() + 0.5);
            WeaveEntity weave2 = new WeaveEntity(level);
            weave2.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 1.5);
            level.addFreshEntity(weave1);
            level.addFreshEntity(weave2);
            toggled = true;
            return InteractionResult.SUCCESS;
        }
        if(!level.isClientSide && toggled){
            if(player.isShiftKeyDown()){
                toggled = false;
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
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
