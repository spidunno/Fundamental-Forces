package com.space_mod_group.space_mod.common.worldevent;

import com.space_mod_group.space_mod.common.capability.PlayerDataCapability;
import com.space_mod_group.space_mod.common.worldevent.starfall.StarfallInstance;
import com.space_mod_group.space_mod.core.registry.worldevent.StarfallResults;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public class WorldEventActivator {
    public static void playerJoin(ServerLevel level, Player player) {
        PlayerDataCapability.getCapability(player).ifPresent(capability -> {
            if (!capability.firstTimeJoin) {
                WorldEventManager.addWorldEvent(level, new StarfallInstance(StarfallResults.FIRST_DROP_POD, player));
            }
        });
        boolean isMissingStarfall = true;
        List<StarfallInstance> inboundStarfalls = WorldEventManager.getEvents(StarfallInstance.class);
        for (StarfallInstance instance : inboundStarfalls)
        {
            if (instance.isEntityValid(level) && instance.targetedEntity.equals(player))
            {
                isMissingStarfall = false;
                break;
            }
        }

        if (isMissingStarfall) {
            WorldEventManager.addWorldEvent(level, new StarfallInstance(StarfallResults.DROP_POD, player));
        }
    }
}
