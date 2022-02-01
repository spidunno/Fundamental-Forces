package com.project_esoterica.esoterica.core.eventhandlers;

import com.project_esoterica.esoterica.common.capability.ChunkDataCapability;
import com.project_esoterica.esoterica.common.capability.EntityDataCapability;
import com.project_esoterica.esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.esoterica.common.capability.WorldDataCapability;
import com.project_esoterica.esoterica.core.systems.magic.spell.hotbar.PlayerSpellHotbarHandler;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
    public static void entityJoin(EntityJoinWorldEvent event) {
        WorldEventManager.playerJoin(event);
        PlayerDataCapability.playerJoin(event);
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        PlayerDataCapability.playerClone(event);
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        PlayerSpellHotbarHandler.playerTick(event);
        PlayerDataCapability.playerTick(event);
    }

    @SubscribeEvent
    public static void playerInteract(PlayerInteractEvent.RightClickBlock event) {
        PlayerSpellHotbarHandler.playerInteract(event);

    }

    @SubscribeEvent
    public static void playerInteract(PlayerInteractEvent.RightClickEmpty event) {
    }

    public static void serverSidePlayerInteract(ServerPlayer player) {
    }

    @SubscribeEvent
    public static void worldTick(TickEvent.WorldTickEvent event) {
        WorldEventManager.serverWorldTick(event);
    }

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
    public static void startTracking(PlayerEvent.StartTracking event) {
        PlayerDataCapability.syncPlayerCapability(event);
        EntityDataCapability.syncEntityCapability(event);
    }
}