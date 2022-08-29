package team.lodestar.fufo.unsorted.handlers;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.level.BlockEvent;
import team.lodestar.fufo.common.capability.FufoChunkDataCapability;
import team.lodestar.fufo.common.capability.FufoPlayerDataCapability;
import team.lodestar.fufo.common.worldevents.starfall.ScheduledStarfallEvent;
import team.lodestar.fufo.registry.common.worldevent.FufoStarfallActors;
import team.lodestar.lodestone.capability.LodestonePlayerDataCapability;

import static team.lodestar.lodestone.handlers.WorldEventHandler.addWorldEvent;

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

    public static void playerJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.level instanceof ServerLevel level) {
                FufoPlayerDataCapability.getCapabilityOptional(player).ifPresent(capability -> {
                    if (ScheduledStarfallEvent.areStarfallsAllowed(level)) {
                        if (!LodestonePlayerDataCapability.getCapability(player).hasJoinedBefore) {
                            addWorldEvent(level, new ScheduledStarfallEvent(FufoStarfallActors.INITIAL_SPACE_DEBRIS).targetEntity(player).randomizedStartingCountdown(level).looping().determined());
                        } else {
                            ScheduledStarfallEvent.addMissingStarfall(level, player);
                        }
                    }
                });
            }
        }
    }
}
