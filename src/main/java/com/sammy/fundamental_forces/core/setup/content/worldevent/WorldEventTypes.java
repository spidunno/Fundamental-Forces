package com.sammy.fundamental_forces.core.setup.content.worldevent;

import com.sammy.fundamental_forces.common.worldevents.starfall.FallingStarfallEvent;
import com.sammy.fundamental_forces.common.worldevents.starfall.ScheduledStarfallEvent;
import com.sammy.fundamental_forces.core.systems.worldevent.WorldEventType;

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
