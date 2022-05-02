package com.sammy.fundamental_forces.common.worldevents.starfall.actors;

import com.sammy.fundamental_forces.common.worldevents.starfall.StarfallActor;
import com.sammy.fundamental_forces.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

public class SpaceDebrisStarfallActor extends StarfallActor {
    protected SpaceDebrisStarfallActor(String id, int startingCountdown) {
        super(id, startingCountdown);
    }

    public SpaceDebrisStarfallActor() {
        super("space_debris", CommonConfig.NATURAL_DEBRIS_COUNTDOWN.getConfigValue());
    }

    @Override
    public int randomizedCountdown(Random random, int parentCountdown) {
        double min = CommonConfig.MINIMUM_DEBRIS_COUNTDOWN_MULTIPLIER.getConfigValue();
        double max = CommonConfig.MAXIMUM_DEBRIS_COUNTDOWN_MULTIPLIER.getConfigValue();
        return (int) (Mth.nextDouble(random, min, max) * parentCountdown);
    }

    @Override
    public void act(ServerLevel level, BlockPos targetBlockPos) {
        level.setBlock(targetBlockPos, Blocks.DIAMOND_BLOCK.defaultBlockState(), 3);
        super.act(level, targetBlockPos);
    }
}
