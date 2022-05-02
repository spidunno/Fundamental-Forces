package com.sammy.fufo.common.worldevents.starfall.actors;

import com.sammy.fufo.config.CommonConfig;
import net.minecraft.util.Mth;

import java.util.Random;

public class InitialStarDebrisStarfallActor extends SpaceDebrisStarfallActor {
    public InitialStarDebrisStarfallActor() {
        super("initial_space_debris", CommonConfig.INITIAL_DEBRIS_COUNTDOWN.getConfigValue());
    }

    @Override
    public int randomizedCountdown(Random random, int parentCountdown) {
        double min = CommonConfig.MINIMUM_DEBRIS_COUNTDOWN_MULTIPLIER.getConfigValue() * 0.5;
        double max = CommonConfig.MAXIMUM_DEBRIS_COUNTDOWN_MULTIPLIER.getConfigValue() * 1.5;
        return (int) (Mth.nextDouble(random, min, max) * parentCountdown);
    }
}
