package team.lodestar.fufo.common.block;

import team.lodestar.fufo.client.ui.programming.ProgrammingScreen;
import team.lodestar.fufo.common.blockentity.UITestBlockEntity;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class UITestBlock<T extends UITestBlockEntity> extends LodestoneEntityBlock<T> {
    public UITestBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {

        if(level.isClientSide) Minecraft.getInstance().setScreen(new ProgrammingScreen(null));

        return super.use(state, level, pos, player, hand, ray);
    }
}
