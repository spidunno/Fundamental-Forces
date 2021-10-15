package com.space_mod_group.space_mod.common.worldevent.starfall.results;

import com.space_mod_group.space_mod.common.worldevent.starfall.StarfallResult;

public class DropPodStarfallResult extends StarfallResult {
    protected DropPodStarfallResult(int startingCountdown) {
        super(startingCountdown);
    }

    public DropPodStarfallResult() {
        super(StarfallResult.DEFAULT_STARTING_COUNTDOWN);
    }
}
