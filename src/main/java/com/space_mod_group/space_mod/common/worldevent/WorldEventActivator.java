package com.space_mod_group.space_mod.common.worldevent;

import com.space_mod_group.space_mod.common.capability.PlayerDataCapability;
import com.space_mod_group.space_mod.common.capability.WorldDataCapability;
import com.space_mod_group.space_mod.common.worldevent.starfall.StarfallInstance;
import com.space_mod_group.space_mod.core.registry.worldevent.StarfallResults;
import com.space_mod_group.space_mod.core.systems.worldevent.WorldEventInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorldEventActivator {
    public static void playerJoin(ServerLevel level, Player player) {
        PlayerDataCapability.getCapability(player).ifPresent(capability -> {
            if (!capability.firstTimeJoin) {
                WorldEventManager.addWorldEvent(level, new StarfallInstance(StarfallResults.FIRST_DROP_POD, player).setLooping());
            } else {
                addStarfallIfMissing(level, player);
            }
        });
    }

    public static void addStarfallIfMissing(ServerLevel level, Player player) {
        WorldDataCapability.getCapability(level).ifPresent(capability -> {
            boolean isMissingStarfall = true;
            for (WorldEventInstance instance : capability.ACTIVE_WORLD_EVENTS) {
                if (instance instanceof StarfallInstance starfallInstance) {
                    if (player.getUUID().equals(starfallInstance.targetedUUID)) {
                        isMissingStarfall = false;
                        break;
                    }
                }
            }

            if (isMissingStarfall) {
                WorldEventManager.addWorldEvent(level, new StarfallInstance(StarfallResults.DROP_POD, player).setLooping());
            }
        });
    }
}