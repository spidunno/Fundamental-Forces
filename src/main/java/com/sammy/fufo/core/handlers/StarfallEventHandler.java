package com.sammy.fufo.core.handlers;

import com.sammy.fufo.common.capability.FufoChunkDataCapability;
import com.sammy.fufo.common.capability.FufoPlayerDataCapability;
import com.sammy.fufo.common.worldevents.starfall.ScheduledStarfallEvent;
import com.sammy.fufo.core.setup.content.worldevent.StarfallActors;
import com.sammy.ortus.capability.OrtusPlayerDataCapability;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;

import static com.sammy.ortus.handlers.WorldEventHandler.addWorldEvent;

public class StarfallEventHandler {
    public static void breakBlock(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            ServerLevel level = player.getLevel();
            LevelChunk chunk = level.getChunkAt(player.blockPosition());
            FufoChunkDataCapability.getCapabilityOptional(chunk).ifPresent(chunkDataCapability -> {
                if (level.canSeeSky(player.blockPosition())) {
                    chunkDataCapability.chunkChanges++;
                }
            });
        }
    }

    public static void placeBlock(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ServerLevel level = player.getLevel();
            LevelChunk chunk = level.getChunkAt(player.blockPosition());
            FufoChunkDataCapability.getCapabilityOptional(chunk).ifPresent(chunkDataCapability -> {
                if (level.canSeeSky(player.blockPosition())) {
                    chunkDataCapability.chunkChanges++;
                }
            });
        }
    }

    public static void playerJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.level instanceof ServerLevel level) {
                FufoPlayerDataCapability.getCapabilityOptional(player).ifPresent(capability -> {
                    if (ScheduledStarfallEvent.areStarfallsAllowed(level)) {
                        if (!OrtusPlayerDataCapability.getCapability(player).hasJoinedBefore) {
                            addWorldEvent(level, new ScheduledStarfallEvent(StarfallActors.INITIAL_SPACE_DEBRIS).targetEntity(player).randomizedStartingCountdown(level).looping().determined());
                        } else {
                            ScheduledStarfallEvent.addMissingStarfall(level, player);
                        }
                    }
                });
            }
        }
    }
}
