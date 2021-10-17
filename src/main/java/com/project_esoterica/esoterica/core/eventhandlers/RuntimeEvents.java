package com.project_esoterica.esoterica.core.eventhandlers;

import com.project_esoterica.esoterica.common.capability.ChunkDataCapability;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RuntimeEvents {
    @SubscribeEvent
    public static void breakEvent(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            ServerLevel level = player.getLevel();
            LevelChunk chunk = level.getChunkAt(player.blockPosition());
            ChunkDataCapability.getCapability(chunk).ifPresent(chunkDataCapability -> {
                chunkDataCapability.heightmapChanges++;
            });
        }
    }

    @SubscribeEvent
    public static void placeEvent(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ServerLevel level = player.getLevel();
            LevelChunk chunk = level.getChunkAt(player.blockPosition());
            ChunkDataCapability.getCapability(chunk).ifPresent(chunkDataCapability -> {
                chunkDataCapability.heightmapChanges++;
            });
        }
    }
}