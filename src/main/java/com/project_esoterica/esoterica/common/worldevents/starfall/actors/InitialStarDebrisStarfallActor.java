package com.project_esoterica.esoterica.common.worldevents.starfall.actors;

import com.project_esoterica.esoterica.common.entity.falling.FallingCrashpodEntity;
import com.project_esoterica.esoterica.core.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class InitialStarDebrisStarfallActor extends SpaceDebrisStarfallActor {
    public InitialStarDebrisStarfallActor() {
        super("initial_space_debris", CommonConfig.INITIAL_SPACE_DEBRIS_COUNTDOWN.get());
    }

    @Override
    public int randomizedCountdown(Random random, int parentCountdown) {
        double min = CommonConfig.MINIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER.get() * 0.5;
        double max = CommonConfig.MAXIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER.get() * 1.5;
        return (int) (Mth.nextDouble(random, min, max) * parentCountdown);
    }
}
