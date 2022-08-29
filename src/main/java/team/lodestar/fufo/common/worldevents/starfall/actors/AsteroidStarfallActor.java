package team.lodestar.fufo.common.worldevents.starfall.actors;

import net.minecraft.util.RandomSource;
import team.lodestar.fufo.common.worldevents.starfall.StarfallActor;
import team.lodestar.fufo.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

public class AsteroidStarfallActor extends StarfallActor {
    public AsteroidStarfallActor() {
        super("asteroid", CommonConfig.NATURAL_DEBRIS_COUNTDOWN.getConfigValue());
    }

    @Override
    public int randomizedCountdown(RandomSource random, int parentCountdown) {
        double min = CommonConfig.MINIMUM_ASTEROID_TIME_MULTIPLIER.getConfigValue();
        double max = CommonConfig.MAXIMUM_ASTEROID_TIME_MULTIPLIER.getConfigValue();
        return (int) (Mth.nextDouble(random, min, max) * parentCountdown);
    }

    @Override
    public void act(ServerLevel level, BlockPos targetBlockPos) {
        level.setBlock(targetBlockPos, Blocks.DIAMOND_BLOCK.defaultBlockState(), 3);
        super.act(level, targetBlockPos);
    }
}
