package com.project_esoterica.empirical_esoterica.common.worldevent.starfall;

import com.project_esoterica.empirical_esoterica.core.registry.worldevent.StarfallResults;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class StarfallResult {
    public final String id;
    public final int startingCountdown;

    public StarfallResult(String id, int startingCountdown) {
        this.startingCountdown = startingCountdown;
        this.id = id;
        StarfallResults.STARFALL_RESULTS.put(id, this);
    }

    public void fall(ServerLevel level, BlockPos pos) {

    }

    public int randomizedCountdown(Random random, int parentCountdown) {
        return parentCountdown;
    }

    public int randomizedCountdown(Random random) {
        return randomizedCountdown(random, startingCountdown);
    }
}
