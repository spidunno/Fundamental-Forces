package team.lodestar.fufo.registry.common.worldevent;

import team.lodestar.fufo.common.starfall.FallingStarfallEvent;
import team.lodestar.fufo.common.starfall.ScheduledStarfallEvent;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

import static team.lodestar.lodestone.setup.worldevent.LodestoneWorldEventTypeRegistry.registerEventType;

public class FufoWorldEventTypes {
    public static WorldEventType SCHEDULED_STARFALL = registerEventType(new WorldEventType("scheduled_starfall", ScheduledStarfallEvent::deserializeNBT));
    public static WorldEventType FALLING_STARFALL = registerEventType(new WorldEventType("falling_starfall", FallingStarfallEvent::deserializeNBT));

}
