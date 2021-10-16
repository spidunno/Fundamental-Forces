package com.project_esoterica.empirical_esoterica.common.worldevent.starfall.results;

import com.project_esoterica.empirical_esoterica.core.config.CommonConfig;
import net.minecraft.util.Mth;

import java.util.Random;

public class InitialStarDebrisStarfallResult extends SpaceDebrisStarfallResult {
    public InitialStarDebrisStarfallResult() {
        super("initial_space_debris", CommonConfig.INITIAL_SPACE_DEBRIS_COUNTDOWN.get());
    }

    @Override
    public int randomizedCountdown(Random random, int parentCountdown) {
        double min = CommonConfig.MINIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER.get() * 0.5;
        double max = CommonConfig.MAXIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER.get() * 1.5;
        return (int) (Mth.nextDouble(random, min, max) * parentCountdown);
    }
}
