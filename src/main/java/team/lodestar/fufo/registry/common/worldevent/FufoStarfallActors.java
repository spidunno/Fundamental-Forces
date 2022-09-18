package team.lodestar.fufo.registry.common.worldevent;

import team.lodestar.fufo.common.starfall.actors.AbstractStarfallActor;
import team.lodestar.fufo.common.starfall.actors.AsteroidStarfallActor;
import team.lodestar.fufo.common.starfall.actors.BlankStarfallActor;
import team.lodestar.fufo.common.starfall.actors.InitialStarDebrisStarfallActor;
import team.lodestar.fufo.common.starfall.actors.SpaceDebrisStarfallActor;

import java.util.HashMap;

public class FufoStarfallActors {
    public static HashMap<String, AbstractStarfallActor> ACTORS = new HashMap<>();

    public static final AbstractStarfallActor BLANK = registerActor(new BlankStarfallActor());
    public static final AbstractStarfallActor ASTEROID = registerActor(new AsteroidStarfallActor());
    public static final AbstractStarfallActor SPACE_DEBRIS = registerActor(new SpaceDebrisStarfallActor());
    public static final AbstractStarfallActor INITIAL_SPACE_DEBRIS = registerActor(new InitialStarDebrisStarfallActor());

    private static AbstractStarfallActor registerActor(AbstractStarfallActor actor) {
        ACTORS.put(actor.id, actor);
        return actor;
    }
}
