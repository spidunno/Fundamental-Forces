package com.sammy.fundamental_forces.core.handlers;

import com.sammy.fundamental_forces.common.capability.ChunkDataCapability;
import com.sammy.fundamental_forces.common.capability.PlayerDataCapability;
import com.sammy.fundamental_forces.common.capability.WorldDataCapability;
import com.sammy.fundamental_forces.common.worldevents.starfall.ScheduledStarfallEvent;
import com.sammy.fundamental_forces.config.CommonConfig;
import com.sammy.fundamental_forces.core.setup.content.block.BlockTagRegistry;
import com.sammy.fundamental_forces.core.setup.content.worldevent.StarfallActors;
import com.sammy.fundamental_forces.core.setup.content.worldevent.WorldEventRenderers;
import com.sammy.fundamental_forces.core.setup.content.worldevent.WorldEventTypes;
import com.sammy.fundamental_forces.core.systems.worldevent.WorldEventInstance;
import com.sammy.fundamental_forces.core.systems.worldevent.WorldEventRenderer;
import com.sammy.fundamental_forces.core.systems.worldevent.WorldEventType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class WorldEventHandler {

    public static class ClientOnly {
        public static void renderWorldEvents(RenderLevelLastEvent event) {
            WorldDataCapability.getCapability(Minecraft.getInstance().level).ifPresent(capability -> {
                for (WorldEventInstance instance : capability.activeWorldEvents) {
                    WorldEventRenderer<WorldEventInstance> renderer = WorldEventRenderers.RENDERERS.get(instance.type);
                    if (renderer != null) {
                        if (renderer.canRender(instance)) {
                            renderer.render(instance, event.getPoseStack(), RenderHandler.DELAYED_RENDER, event.getPartialTick());
                        }
                    }
                }
            });
        }
    }

    public static <T extends WorldEventInstance> T addWorldEvent(Level level, T instance) {
        WorldDataCapability.getCapability(level).ifPresent(capability -> {
            capability.inboundWorldEvents.add(instance);
            instance.start(level);
            instance.sync(level);
        });
        return instance;
    }

    public static void breakBlock(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            ServerLevel level = player.getLevel();
            LevelChunk chunk = level.getChunkAt(player.blockPosition());
            ChunkDataCapability.getCapability(chunk).ifPresent(chunkDataCapability -> {
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
            ChunkDataCapability.getCapability(chunk).ifPresent(chunkDataCapability -> {
                if (level.canSeeSky(player.blockPosition())) {
                    chunkDataCapability.chunkChanges++;
                }
            });
        }
    }

    public static void playerJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.level instanceof ServerLevel level) {
                PlayerDataCapability.getCapability(player).ifPresent(capability -> {
                    WorldDataCapability.getCapability(level).ifPresent(worldCapability -> {
                        if (player instanceof ServerPlayer serverPlayer) {
                            for (WorldEventInstance instance : worldCapability.activeWorldEvents) {
                                if (instance.isClientSynced()) {
                                    WorldEventInstance.sync(instance, serverPlayer);
                                }
                            }
                        }
                    });
                    if (ScheduledStarfallEvent.areStarfallsAllowed(level)) {
                        if (!capability.hasJoinedBefore) {
                            addWorldEvent(level, new ScheduledStarfallEvent(StarfallActors.INITIAL_SPACE_DEBRIS).targetEntity(player).randomizedStartingCountdown(level).looping().determined());
                        } else {
                            ScheduledStarfallEvent.addMissingStarfall(level, player);
                        }
                    }
                });
            }
        }
    }

    public static void worldTick(TickEvent.WorldTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) {
            if (!event.world.isClientSide) {
                tick(event.world);
            }
        }
    }

    public static void tick(Level level) {
        WorldDataCapability.getCapability(level).ifPresent(capability -> {
            capability.activeWorldEvents.addAll(capability.inboundWorldEvents);
            capability.inboundWorldEvents.clear();
            Iterator<WorldEventInstance> iterator = capability.activeWorldEvents.iterator();
            while (iterator.hasNext()) {
                WorldEventInstance instance = iterator.next();
                if (instance.discarded) {
                    iterator.remove();
                } else {
                    instance.tick(level);
                }
            }
        });
    }

    public static void serializeNBT(WorldDataCapability capability, CompoundTag tag) {
        tag.putInt("worldEventCount", capability.activeWorldEvents.size());
        for (int i = 0; i < capability.activeWorldEvents.size(); i++) {
            WorldEventInstance instance = capability.activeWorldEvents.get(i);
            CompoundTag instanceTag = new CompoundTag();
            instance.serializeNBT(instanceTag);
            tag.put("worldEvent_" + i, instanceTag);
        }
    }

    public static void deserializeNBT(WorldDataCapability capability, CompoundTag tag) {
        capability.activeWorldEvents.clear();
        int starfallCount = tag.getInt("worldEventCount");
        for (int i = 0; i < starfallCount; i++) {
            CompoundTag instanceTag = tag.getCompound("worldEvent_" + i);
            WorldEventType reader = WorldEventTypes.EVENT_TYPES.get(instanceTag.getString("type"));
            WorldEventInstance eventInstance = reader.createInstance(instanceTag);

            capability.activeWorldEvents.add(eventInstance);
        }
    }

    public static boolean heightmapCheck(ServerLevel level, BlockPos pos, int range) {
        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                LevelChunk chunk = level.getChunk(SectionPos.blockToSectionCoord(pos.getX()) + x, SectionPos.blockToSectionCoord(pos.getZ()) + z);
                int chunkChanges = ChunkDataCapability.getChunkChanges(chunk);
                if (chunkChanges >= CommonConfig.MAXIMUM_CHUNK_CHANGES.get()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static ArrayList<BlockPos> nearbyBlockList(ServerLevel level, BlockPos centerPos) {
        int size = CommonConfig.STARFALL_SAFETY_RANGE.get();
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