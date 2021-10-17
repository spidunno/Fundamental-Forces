package com.project_esoterica.empirical_esoterica.common.worldevent.starfall;

import com.project_esoterica.empirical_esoterica.EmpiricalEsoterica;
import com.project_esoterica.empirical_esoterica.common.capability.ChunkDataCapability;
import com.project_esoterica.empirical_esoterica.core.config.CommonConfig;
import com.project_esoterica.empirical_esoterica.core.registry.worldevent.StarfallResults;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Random;

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


    public static boolean heightmapCheck(ServerLevel level, BlockPos pos) {
        LevelChunk chunk = level.getChunkAt(pos);
        int heightmapChanges = ChunkDataCapability.getHeightmapChanges(chunk);
        if (heightmapChanges > CommonConfig.MAXIMUM_HEIGHTMAP_CHANGES.get())
        {
            EmpiricalEsoterica.LOGGER.info(heightmapChanges);
            return false;
        }
        return true;
    }

    public static boolean blockEntityCheck(ServerLevel level, BlockPos pos) {
        return true;
    }

    public static boolean blockTagCheck(ServerLevel level, BlockPos pos) {
        return true;
    }
}
