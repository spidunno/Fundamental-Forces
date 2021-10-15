package com.space_mod_group.space_mod.core.registry.worldevent;

import com.space_mod_group.space_mod.common.worldevent.starfall.StarfallResult;
import com.space_mod_group.space_mod.common.worldevent.starfall.results.AsteroidStarfallResult;
import com.space_mod_group.space_mod.common.worldevent.starfall.results.DropPodStarfallResult;
import com.space_mod_group.space_mod.common.worldevent.starfall.results.InitialDropPodStarfallResult;

import java.util.ArrayList;

public class StarfallResults {
    public static ArrayList<StarfallResult> STARFALL_RESULTS = new ArrayList<>();

    public static final AsteroidStarfallResult ASTEROID = new AsteroidStarfallResult();
    public static final DropPodStarfallResult DROP_POD = new DropPodStarfallResult();
    public static final InitialDropPodStarfallResult FIRST_DROP_POD = new InitialDropPodStarfallResult();
}
