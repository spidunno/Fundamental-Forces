package com.project_esoterica.empirical_esoterica.common.worldevent.starfall;

import com.project_esoterica.empirical_esoterica.core.config.CommonConfig;
import com.project_esoterica.empirical_esoterica.core.registry.worldevent.StarfallResults;
import com.project_esoterica.empirical_esoterica.core.systems.worldevent.WorldEventManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.Random;

import static net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;

public class StarfallResult {
    public final String id;
    public final int startingCountdown;

    public StarfallResult(String id, int startingCountdown) {
        this.startingCountdown = startingCountdown;
        this.id = id;
        StarfallResults.STARFALL_RESULTS.put(id, this);
    }

    public int randomizedCountdown(Random random, int parentCountdown) {
        return parentCountdown;
    }

    public int randomizedCountdown(Random random) {
        return randomizedCountdown(random, startingCountdown);
    }

    public void fall(ServerLevel level, BlockPos pos) {

    }

    public final boolean canFall(ServerLevel level, BlockPos pos) {
        if (level.isFluidAtPosition(pos.below(), p -> !p.isEmpty()))
        {
            return false;
        }
        boolean heightmap = WorldEventManager.heightmapCheck(level, pos, 2);
        boolean blocks = WorldEventManager.blockCheck(level, WorldEventManager.nearbyBlockList(level, pos));
        return heightmap && blocks;
    }

    public BlockPos randomizedStarfallPosition(ServerLevel level, BlockPos centerPos) {
        Random random = level.random;
        int minOffset = CommonConfig.MINIMUM_STARFALL_DISTANCE.get();
        int maxOffset = CommonConfig.MAXIMUM_STARFALL_DISTANCE.get();
        int xOffset = Mth.nextInt(random, minOffset, maxOffset) * (random.nextBoolean() ? 1 : -1);
        int zOffset = Mth.nextInt(random, minOffset, maxOffset) * (random.nextBoolean() ? 1 : -1);
        return level.getHeightmapPos(MOTION_BLOCKING_NO_LEAVES, centerPos.offset(xOffset, 0, zOffset));
    }
}