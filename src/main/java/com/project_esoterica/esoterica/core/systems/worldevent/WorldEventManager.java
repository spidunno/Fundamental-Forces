package com.project_esoterica.esoterica.core.systems.worldevent;

import com.project_esoterica.esoterica.common.capability.ChunkDataCapability;
import com.project_esoterica.esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.esoterica.common.capability.WorldDataCapability;
import com.project_esoterica.esoterica.common.worldevents.starfall.ScheduledStarfallEvent;
import com.project_esoterica.esoterica.common.worldevents.starfall.StarfallEvent;
import com.project_esoterica.esoterica.core.config.ClientConfig;
import com.project_esoterica.esoterica.core.config.CommonConfig;
import com.project_esoterica.esoterica.core.registry.block.BlockTagRegistry;
import com.project_esoterica.esoterica.core.registry.worldevent.StarfallActors;
import com.project_esoterica.esoterica.core.systems.rendering.RenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.HashMap;

public class WorldEventManager {

    public static HashMap<String, WorldEventReader> READERS = new HashMap<>();

    static {
        READERS.put(ScheduledStarfallEvent.ID, ScheduledStarfallEvent.READER);
        READERS.put(StarfallEvent.ID, StarfallEvent.READER);
    }

    public static <T extends WorldEventInstance> T addWorldEvent(ServerLevel level, T instance, boolean inbound) {
        return inbound ? addInboundWorldEvent(level, instance) : addWorldEvent(level, instance);
    }

    public static <T extends WorldEventInstance> T addInboundWorldEvent(ServerLevel level, T instance) {
        WorldDataCapability.getCapability(level).ifPresent(capability -> {
            capability.INBOUND_WORLD_EVENTS.add(instance);
            instance.start(level);
        });
        return instance;
    }

    public static <T extends WorldEventInstance> T addWorldEvent(ServerLevel level, T instance) {
        WorldDataCapability.getCapability(level).ifPresent(capability -> {
            capability.ACTIVE_WORLD_EVENTS.add(instance);
            instance.start(level);
        });
        return instance;
    }

    public static <T extends WorldEventInstance> T addClientWorldEvent(ClientLevel level, T instance) {
        WorldDataCapability.getCapability(level).ifPresent(capability -> {
            capability.ACTIVE_WORLD_EVENTS.add(instance);
            instance.clientStart(level);
        });
        return instance;
    }

    public static void playerJoin(Level level, Player player) {
        PlayerDataCapability.getCapability(player).ifPresent(capability -> {
            if (level instanceof ServerLevel serverLevel) {
                if (ScheduledStarfallEvent.areStarfallsAllowed(serverLevel)) {
                    if (!capability.firstTimeJoin) {
                        addWorldEvent(serverLevel, new ScheduledStarfallEvent(StarfallActors.INITIAL_SPACE_DEBRIS).targetEntity(player).randomizedStartingCountdown(serverLevel).looping().determined());
                    } else {
                        ScheduledStarfallEvent.addMissingStarfall(serverLevel, player);
                    }
                }
            }
        });
    }

    public static void serverWorldTick(ServerLevel level) {
        WorldDataCapability.getCapability(level).ifPresent(capability -> {
            for (WorldEventInstance instance : capability.ACTIVE_WORLD_EVENTS) {
                instance.tick(level);
            }
            capability.ACTIVE_WORLD_EVENTS.removeIf(e -> e.invalidated);
            capability.ACTIVE_WORLD_EVENTS.addAll(capability.INBOUND_WORLD_EVENTS);
            capability.INBOUND_WORLD_EVENTS.clear();
        });
    }
    public static void clientWorldTick(ClientLevel level) {
        WorldDataCapability.getCapability(level).ifPresent(capability -> {
            for (WorldEventInstance instance : capability.ACTIVE_WORLD_EVENTS) {
                instance.clientTick(level);
            }
            capability.ACTIVE_WORLD_EVENTS.removeIf(e -> e.invalidated);
            capability.ACTIVE_WORLD_EVENTS.addAll(capability.INBOUND_WORLD_EVENTS);
            capability.INBOUND_WORLD_EVENTS.clear();
        });
    }

    public static void serializeNBT(WorldDataCapability capability, CompoundTag tag) {
        tag.putInt("worldEventCount", capability.ACTIVE_WORLD_EVENTS.size());
        for (int i = 0; i < capability.ACTIVE_WORLD_EVENTS.size(); i++) {
            WorldEventInstance instance = capability.ACTIVE_WORLD_EVENTS.get(i);
            CompoundTag instanceTag = new CompoundTag();
            instance.serializeNBT(instanceTag);
            tag.put("worldEvent_" + i, instanceTag);
        }
    }

    public static void deserializeNBT(WorldDataCapability capability, CompoundTag tag) {
        capability.ACTIVE_WORLD_EVENTS.clear();
        int starfallCount = tag.getInt("worldEventCount");
        for (int i = 0; i < starfallCount; i++) {
            CompoundTag instanceTag = tag.getCompound("worldEvent_" + i);
            WorldEventReader reader = READERS.get(instanceTag.getString("type"));
            WorldEventInstance eventInstance = reader.createInstance(instanceTag);
            capability.ACTIVE_WORLD_EVENTS.add(eventInstance);
        }
    }

    public static boolean heightmapCheck(ServerLevel level, BlockPos pos, int range) {
        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                LevelChunk chunk = level.getChunk(SectionPos.blockToSectionCoord(pos.getX()) + x, SectionPos.blockToSectionCoord(pos.getZ()) + z);
                int heightmapChanges = ChunkDataCapability.getHeightmapChanges(chunk);
                if (heightmapChanges >= CommonConfig.MAXIMUM_HEIGHTMAP_CHANGES.get()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static ArrayList<BlockPos> nearbyBlockList(ServerLevel level, BlockPos centerPos) {
        int size = CommonConfig.STARFALL_SAFETY_CHECK_RANGE.get();
        ArrayList<BlockPos> result = new ArrayList<>();
        //TODO: fix this shit.
        //this is REALLY bad, preferably turn it into an iterable or stream.
        //I used the thing below earlier but that for some reason had the very first point stored in every single member of the stream? ? ? ??
        //return BlockPos.betweenClosedStream(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)).filter(p -> !level.getBlockState(p).isAir());

        for (int x = -size; x <= size; x++) {
            for (int y = (int) (-size / 4f); y <= size / 4f; y++) {
                for (int z = -size; z <= size; z++) {
                    BlockPos pos = new BlockPos(centerPos.offset(x, y, z));
                    if (isBlockImportant(level, pos)) {
                        result.add(pos);
                    }
                }
            }
        }
        return result;
    }

    public static boolean blockCheck(ServerLevel level, ArrayList<BlockPos> arrayList) {
        int failed = 0;
        int failToAbort = (int) (arrayList.size() * 0.2f);
        for (BlockPos pos : arrayList) {
            BlockState state = level.getBlockState(pos);
            if (level.isFluidAtPosition(pos, p -> !p.isEmpty())) {
                failed += 8;
            }
            if (state.is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                return false;
            }
            if (!blockEntityCheck(level, pos)) {
                return false;
            }
            if (!blockCheck(level, state)) {
                failed += 1;
            }
            if (failed >= failToAbort) {
                return false;
            }
        }
        return true;
    }

    public static boolean blockEntityCheck(ServerLevel level, BlockPos pos) {
        return level.getBlockEntity(pos) == null;
    }

    @SuppressWarnings("all")
    public static boolean blockCheck(ServerLevel level, BlockState state) {
        Tag.Named<Block>[] tags = new Tag.Named[]{BlockTagRegistry.STARFALL_ALLOWED, BlockTagRegistry.TERRACOTTA, BlockTags.LUSH_GROUND_REPLACEABLE, BlockTags.MUSHROOM_GROW_BLOCK, BlockTags.LOGS, BlockTags.LEAVES, BlockTags.SNOW, BlockTags.SAND, Tags.Blocks.SANDSTONE};
        for (Tag.Named<Block> tag : tags) {
            if (state.is(tag)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlockImportant(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (level.isFluidAtPosition(pos, p -> !p.isEmpty())) {
            return true;
        }
        return state.getMaterial().isSolid() && !state.isAir() && !state.getMaterial().isReplaceable() && state.getMaterial().blocksMotion();
    }
}