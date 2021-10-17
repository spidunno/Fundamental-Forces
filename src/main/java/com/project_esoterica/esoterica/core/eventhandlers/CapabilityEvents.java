package com.project_esoterica.esoterica.core.eventhandlers;

import com.project_esoterica.esoterica.EsotericHelper;
import com.project_esoterica.esoterica.common.capability.ChunkDataCapability;
import com.project_esoterica.esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.esoterica.common.capability.WorldDataCapability;
import com.project_esoterica.esoterica.core.systems.capability.SimpleCapabilityProvider;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventActivator;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEvents {

    @SubscribeEvent
    public static void attachWorldCapability(AttachCapabilitiesEvent<Level> event) {
        final WorldDataCapability capability = new WorldDataCapability();
        event.addCapability(EsotericHelper.prefix("world_data"), new SimpleCapabilityProvider<>(WorldDataCapability.CAPABILITY, () -> capability));
    }

    @SubscribeEvent
    public static void attachChunkCapability(AttachCapabilitiesEvent<LevelChunk> event) {
        final ChunkDataCapability capability = new ChunkDataCapability();
        event.addCapability(EsotericHelper.prefix("chunk_data"), new SimpleCapabilityProvider<>(ChunkDataCapability.CAPABILITY, () -> capability));
    }

    @SubscribeEvent
    public static void attachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            final PlayerDataCapability capability = new PlayerDataCapability();
            event.addCapability(EsotericHelper.prefix("player_data"), new SimpleCapabilityProvider<>(PlayerDataCapability.CAPABILITY, () -> capability));
        }
    }
    
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
