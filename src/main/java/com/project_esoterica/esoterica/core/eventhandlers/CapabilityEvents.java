package com.project_esoterica.esoterica.core.eventhandlers;

import com.project_esoterica.esoterica.common.capability.ChunkDataCapability;
import com.project_esoterica.esoterica.common.capability.EntityDataCapability;
import com.project_esoterica.esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.esoterica.common.capability.WorldDataCapability;
import com.project_esoterica.esoterica.core.helper.DataHelper;
import com.project_esoterica.esoterica.core.systems.capability.SimpleCapabilityProvider;
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
        event.addCapability(DataHelper.prefix("world_data"), new SimpleCapabilityProvider<>(WorldDataCapability.CAPABILITY, () -> capability));
    }

    @SubscribeEvent
    public static void attachChunkCapability(AttachCapabilitiesEvent<LevelChunk> event) {
        final ChunkDataCapability capability = new ChunkDataCapability();
        event.addCapability(DataHelper.prefix("chunk_data"), new SimpleCapabilityProvider<>(ChunkDataCapability.CAPABILITY, () -> capability));
    }

    @SubscribeEvent
    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(DataHelper.prefix("player_data"), new SimpleCapabilityProvider<>(PlayerDataCapability.CAPABILITY, PlayerDataCapability::new));
        }
        event.addCapability(DataHelper.prefix("entity_data"), new SimpleCapabilityProvider<>(EntityDataCapability.CAPABILITY, EntityDataCapability::new));

    }

    @SubscribeEvent
    public static void playerJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player player) {
            PlayerDataCapability.getCapability(player).ifPresent(capability -> capability.firstTimeJoin = true);
        }
    }
}
