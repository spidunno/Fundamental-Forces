package com.sammy.fundamental_forces.core.setup.content.worldevent;

import com.sammy.fundamental_forces.common.worldevents.starfall.FallingStarfallEvent;
import com.sammy.fundamental_forces.common.worldevents.starfall.ScheduledStarfallEvent;
import com.sammy.ortus.systems.worldevent.WorldEventType;

import static com.sammy.ortus.setup.worldevent.OrtusWorldEventTypeRegistry.registerEventType;

public class WorldEventTypes {
    public static WorldEventType SCHEDULED_STARFALL = registerEventType(new WorldEventType("scheduled_starfall", ScheduledStarfallEvent::new));
    public static WorldEventType FALLING_STARFALL = registerEventType(new WorldEventType("falling_starfall", FallingStarfallEvent::new));

}
