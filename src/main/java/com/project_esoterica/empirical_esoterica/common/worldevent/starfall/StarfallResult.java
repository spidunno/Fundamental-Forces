package com.project_esoterica.empirical_esoterica.common.worldevent.starfall;

import com.project_esoterica.empirical_esoterica.core.registry.worldevent.StarfallResults;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class StarfallResult {
    public final int id;
    public final int startingCountdown;
    public StarfallResult(int startingCountdown) {
        this.startingCountdown = startingCountdown;
        this.id = StarfallResults.STARFALL_RESULTS.size();
        StarfallResults.STARFALL_RESULTS.add(this);
    }

    public void fall(ServerLevel level, BlockPos pos)
    {

    }
    public int randomizeCountdown(Random random, int parentCountdown)
    {
        return parentCountdown;
    }
    public int randomizeCountdown(Random random)
    {
        return randomizeCountdown(random, startingCountdown);
    }
}
