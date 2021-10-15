package com.space_mod_group.space_mod.core.registry.worldevent;

import com.space_mod_group.space_mod.common.worldevent.starfall.StarfallResult;
import com.space_mod_group.space_mod.common.worldevent.starfall.results.AsteroidStarfallResult;
import com.space_mod_group.space_mod.common.worldevent.starfall.results.SpaceDebrisStarfallResult;
import com.space_mod_group.space_mod.common.worldevent.starfall.results.InitialStarDebrisStarfallResult;

import java.util.ArrayList;

public class StarfallResults {
    public static ArrayList<StarfallResult> STARFALL_RESULTS = new ArrayList<>();

    public static final AsteroidStarfallResult ASTEROID = new AsteroidStarfallResult();
    public static final SpaceDebrisStarfallResult SPACE_DEBRIS = new SpaceDebrisStarfallResult();
    public static final InitialStarDebrisStarfallResult INITIAL_SPACE_DEBRIS = new InitialStarDebrisStarfallResult();
}
