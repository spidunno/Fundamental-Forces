package com.sammy.fufo.core.eventhandlers;

import com.sammy.fufo.common.capability.*;
import com.sammy.fufo.common.world.registry.FluidPipeNetworkRegistry;
import com.sammy.fufo.core.handlers.PlayerSpellHotbarHandler;
import com.sammy.fufo.core.handlers.StarfallEventHandler;
import com.sammy.fufo.core.systems.logistics.FluidPipeNetwork;
import com.sammy.ortus.events.types.RightClickEmptyServer;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RuntimeEvents {
	

    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event) {
        StarfallEventHandler.breakBlock(event);
    }

    @SubscribeEvent
    public static void placeBlock(BlockEvent.EntityPlaceEvent event) {
        StarfallEventHandler.placeBlock(event);
    }

    @SubscribeEvent
    public static void entityJoin(EntityJoinWorldEvent event) {
        FufoPlayerDataCapability.playerJoin(event);
        StarfallEventHandler.playerJoin(event);
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        FufoPlayerDataCapability.playerClone(event);
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        PlayerSpellHotbarHandler.playerTick(event);
    }

    @SubscribeEvent
    public static void playerInteract(PlayerInteractEvent.RightClickBlock event) {
        PlayerSpellHotbarHandler.playerInteract(event);
    }

    @SubscribeEvent
    public static void playerInteract(PlayerInteractEvent.RightClickEmpty event) {

    }

    @SubscribeEvent
    public static void onRightClickEmptyServer(RightClickEmptyServer event){
    }

    @SubscribeEvent
    public static void worldLoad(WorldEvent.Load event) {
//    	FluidPipeNetworkRegistry.getRegistry(event.getWorld()).load();
    }
    
    @SubscribeEvent
    public static void worldUnload(WorldEvent.Unload event) {
    	
    }
    
    @SubscribeEvent
    public static void worldTick(TickEvent.WorldTickEvent event) {
    	if (!FluidPipeNetwork.MANUAL_TICKING) FluidPipeNetworkRegistry.getRegistry(event.world).tickNetworks();
    }

    @SubscribeEvent
    public static void attachWorldCapability(AttachCapabilitiesEvent<Level> event) {
        FufoWorldDataCapability.attachWorldCapability(event);
    }

    @SubscribeEvent
    public static void attachChunkCapability(AttachCapabilitiesEvent<LevelChunk> event) {
        FufoChunkDataCapability.attachChunkCapability(event);
    }

    @SubscribeEvent
    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        FufoPlayerDataCapability.attachPlayerCapability(event);
        FufoEntityDataCapability.attachEntityCapability(event);
    }

    @SubscribeEvent
    public static void attachItemStackCapability(AttachCapabilitiesEvent<ItemStack> event) {
        FufoItemStackCapability.attachItemCapability(event);
    }

    @SubscribeEvent
    public static void startTracking(PlayerEvent.StartTracking event) {
        FufoPlayerDataCapability.syncPlayerCapability(event);
        FufoEntityDataCapability.syncEntityCapability(event);
    }
}