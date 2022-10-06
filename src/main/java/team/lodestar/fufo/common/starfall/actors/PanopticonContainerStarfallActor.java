package team.lodestar.fufo.common.starfall.actors;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import team.lodestar.fufo.config.CommonConfig;

public class PanopticonContainerStarfallActor extends AbstractStarfallActor {
    public PanopticonContainerStarfallActor() {
        super("panopticon_supply_drop", CommonConfig.PANOPTICON_SUPPLY_DROP_COUNTDOWN.getConfigValue());
    }

    @Override
    public int getStarfallCountdown(RandomSource random) {
        return (int) (Mth.nextDouble(random,
                CommonConfig.MINIMUM_ASTEROID_TIME_MULTIPLIER.getConfigValue(),
                CommonConfig.MAXIMUM_ASTEROID_TIME_MULTIPLIER.getConfigValue()) * startingCountdown);
    }

    @Override
    public void act(ServerLevel level, BlockPos targetBlockPos) {
        level.setBlock(targetBlockPos, Blocks.DIAMOND_BLOCK.defaultBlockState(), 3);
        super.act(level, targetBlockPos);
    }
}
