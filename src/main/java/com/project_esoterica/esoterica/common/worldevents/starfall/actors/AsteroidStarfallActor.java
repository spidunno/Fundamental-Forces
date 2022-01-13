package com.project_esoterica.esoterica.common.worldevents.starfall.actors;

import com.project_esoterica.esoterica.common.worldevents.starfall.StarfallActor;
import com.project_esoterica.esoterica.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

public class AsteroidStarfallActor extends StarfallActor {
    public AsteroidStarfallActor() {
        super("asteroid", CommonConfig.NATURAL_DEBRIS_COUNTDOWN.get());
    }

    @Override
    public int randomizedCountdown(Random random, int parentCountdown) {
        double min = CommonConfig.MINIMUM_ASTEROID_TIME_MULTIPLIER.get();
        double max = CommonConfig.MAXIMUM_ASTEROID_TIME_MULTIPLIER.get();
        return (int) (Mth.nextDouble(random, min, max) * parentCountdown);
    }

    @Override
    public void act(ServerLevel level, BlockPos targetBlockPos) {
        level.setBlock(targetBlockPos, Blocks.DIAMOND_BLOCK.defaultBlockState(), 3);
        super.act(level, targetBlockPos);
    }
}
