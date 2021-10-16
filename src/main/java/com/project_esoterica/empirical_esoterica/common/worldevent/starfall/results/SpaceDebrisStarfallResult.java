package com.project_esoterica.empirical_esoterica.common.worldevent.starfall.results;

import com.project_esoterica.empirical_esoterica.common.worldevent.starfall.StarfallResult;
import com.project_esoterica.empirical_esoterica.core.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;

import java.util.Random;

public class SpaceDebrisStarfallResult extends StarfallResult {
    protected SpaceDebrisStarfallResult(int startingCountdown) {
        super(startingCountdown);
    }

    public SpaceDebrisStarfallResult() {
        super(CommonConfig.NATURAL_SPACE_DEBRIS_COUNTDOWN.get());
    }

    @Override
    public int randomizeCountdown(Random random, int parentCountdown) {
        double min = CommonConfig.MINIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER.get();
        double max = CommonConfig.MAXIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER.get();
        return (int) (Mth.nextDouble(random, min, max) * parentCountdown);
    }

    @Override
    public void fall(ServerLevel level, BlockPos pos) {
        Chicken chicken = EntityType.CHICKEN.create(level);
        chicken.setPos(pos.getX(), pos.getY(), pos.getZ());
        level.addFreshEntity(chicken);
        super.fall(level, pos);
    }
}
