package com.space_mod_group.space_mod.core.systems.starfall;

import com.space_mod_group.space_mod.SpaceMod;
import com.space_mod_group.space_mod.common.starfall.StarfallManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class StarfallResult {
    public static final int FIRST_TIME_STARTING_COUNTDOWN = 15000;
    public static final int DEFAULT_STARTING_COUNTDOWN = 114000;

    public final int id;
    public final int startingCountdown;
    public StarfallResult(int startingCountdown) {
        this.startingCountdown = startingCountdown;
        this.id = StarfallManager.STARFALL_RESULTS.size();
        StarfallManager.STARFALL_RESULTS.add(this);
    }

    public void fall(ServerLevel level, BlockPos pos)
    {

    }
}
