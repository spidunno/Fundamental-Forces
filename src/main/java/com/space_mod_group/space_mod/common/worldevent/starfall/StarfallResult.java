package com.space_mod_group.space_mod.common.worldevent.starfall;

import com.space_mod_group.space_mod.core.registry.worldevent.StarfallResults;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class StarfallResult {
    public static final int FIRST_TIME_STARTING_COUNTDOWN = 150;
    public static final int DEFAULT_STARTING_COUNTDOWN = 1200;
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
}
