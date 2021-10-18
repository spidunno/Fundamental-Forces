package com.project_esoterica.esoterica.common.worldevents.starfall.results;

import com.project_esoterica.esoterica.common.worldevents.starfall.StarfallResult;
import com.project_esoterica.esoterica.core.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

public class AsteroidStarfallResult extends StarfallResult {
    public AsteroidStarfallResult() {
        super("asteroid", CommonConfig.NATURAL_SPACE_DEBRIS_COUNTDOWN.get());
    }

    @Override
    public int randomizedCountdown(Random random, int parentCountdown) {
        double min = CommonConfig.MINIMUM_ASTEROID_COUNTDOWN_MULTIPLIER.get();
        double max = CommonConfig.MAXIMUM_ASTEROID_COUNTDOWN_MULTIPLIER.get();
        return (int) (Mth.nextDouble(random, min, max) * parentCountdown);
    }

    @Override
    public void fall(ServerLevel level, BlockPos pos) {
        level.setBlockAndUpdate(pos, Blocks.IRON_BLOCK.defaultBlockState());
        super.fall(level, pos);
    }
}
