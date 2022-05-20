package com.sammy.fufo.common.block;

import com.sammy.fufo.common.blockentity.AnchorBlockEntity;
import com.sammy.ortus.helpers.placement.IPlacementHelper;
import com.sammy.ortus.helpers.placement.PlacementHelpers;
import com.sammy.ortus.helpers.placement.util.PoleHelper;
import com.sammy.ortus.systems.block.OrtusEntityBlock;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;


public class AnchorBlock<T extends AnchorBlockEntity> extends OrtusEntityBlock<T> {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    private static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());
    public static final VoxelShape SHAPE = Block.box(5, 5, 5, 11, 11, 11);

    public AnchorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        switch (direction) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch(state.getValue(AXIS)) {
                    case X:
                        return state.setValue(AXIS, Direction.Axis.Z);
                    case Z:
                        return state.setValue(AXIS, Direction.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AXIS);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(AXIS, pContext.getPlayer().isShiftKeyDown() ? pContext.getClickedFace().getAxis() : pContext.getNearestLookingDirection().getAxis());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        if (player.isShiftKeyDown() || !player.mayBuild())  return InteractionResult.PASS;
        ItemStack held = player.getItemInHand(hand);
        IPlacementHelper helper = PlacementHelpers.get(placementHelperId);
        System.out.println(helper.toString());
        if(helper.matchesItem(held))
            return helper.getOffset(player, level, state, pos, ray).placeInWorld(level, (BlockItem) held.getItem(), player, hand, ray);
        return InteractionResult.PASS;
    }

    @MethodsReturnNonnullByDefault
    private static class PlacementHelper extends PoleHelper<Direction.Axis> {
        //used for extending a shaft in its axis, like the piston poles. works with shafts and cogs

        private PlacementHelper(){
            super(
                    state -> state.getBlock() instanceof OrtusEntityBlock,
                    state -> state.getValue(AXIS),
                    AXIS
            );
        }

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return i -> i.getItem() instanceof BlockItem && ((BlockItem) i.getItem()).getBlock() instanceof OrtusEntityBlock<?>;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return state -> state.getBlock() instanceof AnchorBlock<?>;
        }
    }
}
