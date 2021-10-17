package com.project_esoterica.empirical_esoterica.common.worldevent.starfall;

import com.project_esoterica.empirical_esoterica.common.capability.ChunkDataCapability;
import com.project_esoterica.empirical_esoterica.core.config.CommonConfig;
import com.project_esoterica.empirical_esoterica.core.registry.block.BlockTagRegistry;
import com.project_esoterica.empirical_esoterica.core.registry.worldevent.StarfallResults;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import org.openjdk.nashorn.internal.ir.annotations.Ignore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class StarfallResult {
    public final String id;
    public final int startingCountdown;

    public StarfallResult(String id, int startingCountdown) {
        this.startingCountdown = startingCountdown;
        this.id = id;
        StarfallResults.STARFALL_RESULTS.put(id, this);
    }

    public int randomizedCountdown(Random random, int parentCountdown) {
        return parentCountdown;
    }

    public int randomizedCountdown(Random random) {
        return randomizedCountdown(random, startingCountdown);
    }

    public void fall(ServerLevel level, BlockPos pos) {

    }

    public final boolean canFall(ServerLevel level, BlockPos pos) {
        return true;
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
        //this is REALLY bad, preferably turn it into an iterable or stream.
        //I used the thing below earlier but that for some reason had the very first point stored in every single member of the stream? ? ? ??
        //return BlockPos.betweenClosedStream(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)).filter(p -> !level.getBlockState(p).isAir());

        for (int x = -size; x <= size;x++)
        {
            for (int y = (int) (-size/4f); y <= size/4f; y++)
            {
                for (int z = -size; z <= size;z++)
                {
                    BlockPos pos = new BlockPos(centerPos.offset(x,y,z));
                    if (!level.getBlockState(pos).isAir())
                    {
                        result.add(pos);
                    }
                }
            }
        }
        return result;
    }

    public static boolean blockCheck(ServerLevel level, ArrayList<BlockPos> arrayList) {
        int failed = 0;
        int failToAbort = (int) (arrayList.size()*0.2f);
        for (BlockPos pos : arrayList) {
            BlockState state = level.getBlockState(pos);
            if (state.is(BlockTags.FEATURES_CANNOT_REPLACE))
            {
                return false;
            }
            if (!blockEntityCheck(level, pos))
            {
                return false;
            }
            if (!blockCheck(level, state))
            {
                failed++;
                if (failed >= failToAbort)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean blockEntityCheck(ServerLevel level, BlockPos pos) {
        return level.getBlockEntity(pos) == null;
    }

    @SuppressWarnings("all")
    public static boolean blockCheck(ServerLevel level, BlockState state) {
        if (!state.getMaterial().isSolid() || state.getMaterial().isReplaceable() || !state.getMaterial().blocksMotion())
        {
            return true;
        }
        Tag.Named<Block>[] tags = new Tag.Named[]{BlockTagRegistry.STARFALL_ALLOWED, BlockTags.LOGS, BlockTags.LEAVES, BlockTags.LUSH_GROUND_REPLACEABLE, BlockTags.SNOW, BlockTags.MUSHROOM_GROW_BLOCK};
        for (Tag.Named<Block> tag : tags)
        {
            if (state.is(tag))
            {
                return true;
            }
        }
        return false;
    }
}