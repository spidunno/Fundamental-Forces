package com.sammy.fundamental_forces.common.worldevents.starfall.actors;

import com.sammy.fundamental_forces.config.CommonConfig;
import net.minecraft.util.Mth;

import java.util.Random;

public class InitialStarDebrisStarfallActor extends SpaceDebrisStarfallActor {
    public InitialStarDebrisStarfallActor() {
        super("initial_space_debris", CommonConfig.INITIAL_DEBRIS_COUNTDOWN.get());
    }

    @Override
    public int randomizedCountdown(Random random, int parentCountdown) {
        double min = CommonConfig.MINIMUM_DEBRIS_COUNTDOWN_MULTIPLIER.get() * 0.5;
        double max = CommonConfig.MAXIMUM_DEBRIS_COUNTDOWN_MULTIPLIER.get() * 1.5;
        return (int) (Mth.nextDouble(random, min, max) * parentCountdown);
    }
}
