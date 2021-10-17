package com.project_esoterica.empirical_esoterica.common.worldevent.starfall.results;

import com.project_esoterica.empirical_esoterica.common.worldevent.starfall.StarfallResult;
import com.project_esoterica.empirical_esoterica.core.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

public class SpaceDebrisStarfallResult extends StarfallResult {
    protected SpaceDebrisStarfallResult(String id, int startingCountdown) {
        super(id, startingCountdown);
    }

    public SpaceDebrisStarfallResult() {
        super("space_debris", CommonConfig.NATURAL_SPACE_DEBRIS_COUNTDOWN.get());
    }

    @Override
    public int randomizedCountdown(Random random, int parentCountdown) {
        double min = CommonConfig.MINIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER.get();
        double max = CommonConfig.MAXIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER.get();
        return (int) (Mth.nextDouble(random, min, max) * parentCountdown);
    }

    @Override
    public void fall(ServerLevel level, BlockPos pos) {
        level.setBlockAndUpdate(pos, Blocks.DIAMOND_BLOCK.defaultBlockState());
        super.fall(level, pos);
    }
}
