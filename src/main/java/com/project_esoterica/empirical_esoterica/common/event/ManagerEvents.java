package com.project_esoterica.empirical_esoterica.common.event;

import com.project_esoterica.empirical_esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.empirical_esoterica.common.worldevent.WorldEventManager;
import com.project_esoterica.empirical_esoterica.common.worldevent.WorldEventActivator;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ManagerEvents {

    @SubscribeEvent
    public static void playerJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.level instanceof ServerLevel level) {
                WorldEventActivator.playerJoin(level, player);
            }
            PlayerDataCapability.getCapability(player).ifPresent(capability -> capability.firstTimeJoin = true);
        }
    }
    @SubscribeEvent
    public static void worldTick(TickEvent.WorldTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) {
            if (event.world instanceof ServerLevel level) {
                WorldEventManager.worldTick(level);
            }
        }
    }
}
