package com.project_esoterica.esoterica.common.worldevents.starfall.actors;

import com.project_esoterica.esoterica.common.worldevents.starfall.StarfallActor;
import com.project_esoterica.esoterica.core.config.CommonConfig;
import net.minecraft.util.Mth;

import java.util.Random;

public class SpaceDebrisStarfallActor extends StarfallActor {
    protected SpaceDebrisStarfallActor(String id, int startingCountdown) {
        super(id, startingCountdown);
    }

    public SpaceDebrisStarfallActor() {
        super("space_debris", CommonConfig.NATURAL_SPACE_DEBRIS_COUNTDOWN.get());
    }

    @Override
    public int randomizedCountdown(Random random, int parentCountdown) {
        double min = CommonConfig.MINIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER.get();
        double max = CommonConfig.MAXIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER.get();
        return (int) (Mth.nextDouble(random, min, max) * parentCountdown);
    }
}
