package team.lodestar.fufo.registry.common.worldevent;

import team.lodestar.fufo.common.starfall.actors.AbstractStarfallActor;
import team.lodestar.fufo.common.starfall.actors.AsteroidStarfallActor;
import team.lodestar.fufo.common.starfall.actors.PanopticonContainerStarfallActor;

import java.util.HashMap;

public class FufoStarfallActors {
    public static HashMap<String, AbstractStarfallActor> ACTORS = new HashMap<>();

    public static final AbstractStarfallActor ASTEROID = registerActor(new AsteroidStarfallActor());
    public static final AbstractStarfallActor PANOPTICON_SUPPLY_DROP = registerActor(new PanopticonContainerStarfallActor());

    private static AbstractStarfallActor registerActor(AbstractStarfallActor actor) {
        ACTORS.put(actor.id, actor);
        return actor;
    }
}
