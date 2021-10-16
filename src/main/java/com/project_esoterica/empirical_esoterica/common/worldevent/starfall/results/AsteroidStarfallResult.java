package com.project_esoterica.empirical_esoterica.common.worldevent.starfall.results;

import com.project_esoterica.empirical_esoterica.common.worldevent.starfall.StarfallResult;
import com.project_esoterica.empirical_esoterica.core.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cow;

import java.util.Random;

public class AsteroidStarfallResult extends StarfallResult {
    public AsteroidStarfallResult() {
        super("asteroid", CommonConfig.NATURAL_SPACE_DEBRIS_COUNTDOWN.get());
    }

    @Override
    public int randomizeCountdown(Random random, int parentCountdown) {
        double min = CommonConfig.MINIMUM_ASTEROID_COUNTDOWN_MULTIPLIER.get();
        double max = CommonConfig.MAXIMUM_ASTEROID_COUNTDOWN_MULTIPLIER.get();
        return (int) (Mth.nextDouble(random, min, max) * parentCountdown);
    }

    @Override
    public void fall(ServerLevel level, BlockPos pos) {
        Cow cow = EntityType.COW.create(level);
        cow.setPos(pos.getX(), pos.getY(), pos.getZ());
        level.addFreshEntity(cow);
        super.fall(level, pos);
    }
}
