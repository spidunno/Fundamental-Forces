package team.lodestar.fufo.common.fluid.sealed_barrel;

import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class SealedBarrelBlock<T extends SealedBarrelBlockEntity> extends LodestoneEntityBlock<T> {

    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", Shape.class);

    public enum Shape implements StringRepresentable {
        NORMAL, NO_WINDOW;

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }

    }

    public SealedBarrelBlock(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(defaultBlockState().setValue(SHAPE, Shape.NORMAL));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = super.getStateForPlacement(pContext);
        if (state != null) {
            ItemStack stack = pContext.getPlayer().getItemInHand(pContext.getHand());
            if (stack.hasTag()) {
                if (stack.getTag().contains("shape")) {
                    state.setValue(SHAPE, Shape.valueOf(stack.getTag().getString("shape")));
                }
            }
        }
        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(SHAPE);
    }
}