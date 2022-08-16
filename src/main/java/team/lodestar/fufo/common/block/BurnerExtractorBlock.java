package team.lodestar.fufo.common.block;

import team.lodestar.fufo.common.blockentity.BurnerExtractorBlockEntity;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class BurnerExtractorBlock<T extends BurnerExtractorBlockEntity> extends LodestoneEntityBlock<T> {
    public static final BooleanProperty BURNING = BooleanProperty.create("burning");
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public BurnerExtractorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BURNING, OPEN);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND && player.isShiftKeyDown()) {
            boolean currentState = state.getValue(OPEN);
            level.setBlock(pos, state.setValue(OPEN, !currentState), 3);
        }
        return InteractionResult.SUCCESS;
    }
}
