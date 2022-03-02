package com.sammy.fundamental_forces.core.eventhandlers;

import com.sammy.fundamental_forces.common.capability.ChunkDataCapability;
import com.sammy.fundamental_forces.common.capability.EntityDataCapability;
import com.sammy.fundamental_forces.common.capability.PlayerDataCapability;
import com.sammy.fundamental_forces.common.capability.WorldDataCapability;
import com.sammy.fundamental_forces.core.handlers.PlayerSpellHotbarHandler;
import com.sammy.fundamental_forces.core.handlers.WorldEventHandler;
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
        WorldEventHandler.breakBlock(event);
    }

    @SubscribeEvent
    public static void placeBlock(BlockEvent.EntityPlaceEvent event) {
        WorldEventHandler.placeBlock(event);
    }

    @SubscribeEvent
    public static void entityJoin(EntityJoinWorldEvent event) {
        WorldEventHandler.playerJoin(event);
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
        WorldEventHandler.serverWorldTick(event);
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