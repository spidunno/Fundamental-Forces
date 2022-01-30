package com.project_esoterica.esoterica.core.eventhandlers;

import com.project_esoterica.esoterica.common.capability.ChunkDataCapability;
import com.project_esoterica.esoterica.common.capability.EntityDataCapability;
import com.project_esoterica.esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.esoterica.common.capability.WorldDataCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEvents {

    @SubscribeEvent
    public static void attachWorldCapability(AttachCapabilitiesEvent<Level> event) {
        WorldDataCapability.attachWorldCapability(event);
    }

    @SubscribeEvent
    public static void attachChunkCapability(AttachCapabilitiesEvent<LevelChunk> event) {
        ChunkDataCapability.attachChunkCapability(event);
    }

    @SubscribeEvent
    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        PlayerDataCapability.attachPlayerCapability(event);
        EntityDataCapability.attachEntityCapability(event);
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        PlayerDataCapability.syncPlayerCapability(event);
        EntityDataCapability.syncEntityCapability(event);
    }
    @SubscribeEvent
    public static void playerJoin(EntityJoinWorldEvent event) {
        PlayerDataCapability.playerJoin(event);
    }
}