package com.space_mod_group.space_mod.common.starfall.results;

import com.space_mod_group.space_mod.core.systems.starfall.StarfallResult;

public class DropPodStarfallResult extends StarfallResult {
    protected DropPodStarfallResult(int startingCountdown) {
        super(startingCountdown);
    }

    public DropPodStarfallResult() {
        super(DEFAULT_STARTING_COUNTDOWN);
    }
}
