package com.project_esoterica.esoterica.core.systems.worldevent;

import com.project_esoterica.esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.esoterica.common.capability.WorldDataCapability;
import com.project_esoterica.esoterica.common.worldevents.starfall.StarfallEvent;
import com.project_esoterica.esoterica.core.config.CommonConfig;
import com.project_esoterica.esoterica.core.registry.worldevent.StarfallResults;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class WorldEventActivator {
    public static void playerJoin(ServerLevel level, Player player) {
        PlayerDataCapability.getCapability(player).ifPresent(capability -> {
            if (areStarfallsAllowed(level)) {
                if (!capability.firstTimeJoin) {
                    WorldEventManager.addWorldEvent(level, new StarfallEvent(StarfallResults.INITIAL_SPACE_DEBRIS).targetEntity(player).randomizedStartingCountdown(level).looping().determined());
                } else {
                    addSpaceDebrisIfMissing(level, player);
                }
            }
        });
    }

    public static void addSpaceDebrisIfMissing(ServerLevel level, Player player) {
        WorldDataCapability.getCapability(level).ifPresent(capability -> {
            boolean isMissingStarfall = true;
            for (WorldEventInstance instance : capability.ACTIVE_WORLD_EVENTS) {
                if (instance instanceof StarfallEvent starfallEvent) {
                    if (player.getUUID().equals(starfallEvent.targetedUUID)) {
                        isMissingStarfall = false;
                        break;
                    }
                }
            }

            if (isMissingStarfall) {
                addSpaceDebris(level, player, false);
            }
        });
    }

    public static void addSpaceDebris(ServerLevel level, LivingEntity entity, boolean inbound) {
        if (areStarfallsAllowed(level)) {
            StarfallEvent debrisInstance = WorldEventManager.addWorldEvent(level, new StarfallEvent(StarfallResults.SPACE_DEBRIS).targetEntity(entity).randomizedStartingCountdown(level).looping().determined(), inbound);
            Double chance = CommonConfig.ASTEROID_CHANCE.get();
            int maxAsteroids = CommonConfig.MAXIMUM_ASTEROID_COUNT.get();
            for (int i = 0; i < maxAsteroids; i++) {
                if (level.random.nextFloat() < chance) {
                    WorldEventManager.addWorldEvent(level, new StarfallEvent(StarfallResults.ASTEROID).targetEntity(entity).randomizedStartingCountdown(level, debrisInstance.startingCountdown).determined(), inbound);
                    chance *= 0.8f;
                } else {
                    break;
                }
            }
        }
    }

    public static boolean areStarfallsAllowed(ServerLevel level) {
        return CommonConfig.STARFALLS_ENABLED.get() && CommonConfig.STARFALL_ALLOWED_LEVELS.get().contains(level.dimension().location().toString());
    }
}