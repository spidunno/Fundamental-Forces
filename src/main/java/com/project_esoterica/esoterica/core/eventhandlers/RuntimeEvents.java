package com.project_esoterica.esoterica.core.eventhandlers;

import com.project_esoterica.esoterica.common.capability.ChunkDataCapability;
import com.project_esoterica.esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RuntimeEvents {
    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event) {
        WorldEventManager.breakBlock(event);
    }

    @SubscribeEvent
    public static void placeBlock(BlockEvent.EntityPlaceEvent event) {
        WorldEventManager.placeBlock(event);
    }
    @SubscribeEvent
    public static void playerJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.level instanceof ServerLevel level) {
                WorldEventManager.playerJoin(level, player);
            }

        }
    }
    @SubscribeEvent
    public static void worldTick(TickEvent.WorldTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) {
            if (event.world instanceof ServerLevel serverLevel) {
                WorldEventManager.serverWorldTick(serverLevel);
            }
        }
    }
}