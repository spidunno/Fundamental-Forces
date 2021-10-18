package com.project_esoterica.esoterica.core.registry.worldevent;

import com.project_esoterica.esoterica.common.worldevents.starfall.StarfallResult;
import com.project_esoterica.esoterica.common.worldevents.starfall.results.AsteroidStarfallResult;
import com.project_esoterica.esoterica.common.worldevents.starfall.results.InitialStarDebrisStarfallResult;
import com.project_esoterica.esoterica.common.worldevents.starfall.results.SpaceDebrisStarfallResult;

import java.util.HashMap;

public class StarfallResults {
    public static HashMap<String, StarfallResult> STARFALL_RESULTS = new HashMap<>();

    public static final AsteroidStarfallResult ASTEROID = new AsteroidStarfallResult();
    public static final SpaceDebrisStarfallResult SPACE_DEBRIS = new SpaceDebrisStarfallResult();
    public static final InitialStarDebrisStarfallResult INITIAL_SPACE_DEBRIS = new InitialStarDebrisStarfallResult();
}
