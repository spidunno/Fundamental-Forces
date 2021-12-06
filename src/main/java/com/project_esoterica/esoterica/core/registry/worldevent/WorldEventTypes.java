package com.project_esoterica.esoterica.core.registry.worldevent;

import com.project_esoterica.esoterica.common.worldevents.starfall.FallingStarfallEvent;
import com.project_esoterica.esoterica.common.worldevents.starfall.ScheduledStarfallEvent;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventType;

import java.util.HashMap;

public class WorldEventTypes {

    public static HashMap<String, WorldEventType> EVENT_TYPES = new HashMap<>();

    public static WorldEventType SCHEDULED_STARFALL = registerEventType(new WorldEventType("scheduled_starfall", ScheduledStarfallEvent::fromNBT));
    public static WorldEventType FALLING_STARFALL = registerEventType(new WorldEventType("falling_starfall", FallingStarfallEvent::fromNBT));

    private static WorldEventType registerEventType(WorldEventType eventType) {
        EVENT_TYPES.put(eventType.id, eventType);
        return eventType;
    }
}
