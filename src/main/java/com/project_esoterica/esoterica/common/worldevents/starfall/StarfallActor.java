package com.project_esoterica.esoterica.common.worldevents.starfall;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.core.config.CommonConfig;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraftforge.common.world.ForgeChunkManager;

import java.util.Random;

import static net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;

public class StarfallActor {
    public final String id;
    public final int startingCountdown;

    public StarfallActor(String id, int startingCountdown) {
        this.id = id;
        this.startingCountdown = startingCountdown;
    }

    public int randomizedCountdown(Random random, int parentCountdown) {
        return parentCountdown;
    }

    public int randomizedCountdown(Random random) {
        return randomizedCountdown(random, startingCountdown);
    }

    public void fall(ServerLevel level, BlockPos targetPos) {

    }

    public void act(ServerLevel level, BlockPos targetPos) {

    }

    public final boolean canFall(ServerLevel level, BlockPos pos) {
        if (level.isFluidAtPosition(pos.below(), p -> !p.isEmpty())) {
            return false;
        }
        boolean heightmap = WorldEventManager.heightmapCheck(level, pos, 2);
        boolean blocks = WorldEventManager.blockCheck(level, WorldEventManager.nearbyBlockList(level, pos));
        return heightmap && blocks;
    }

    public BlockPos randomizedStarfallPosition(ServerLevel level, BlockPos centerPos) {
        Random random = level.random;
        int minOffset = CommonConfig.MINIMUM_STARFALL_DISTANCE.get();
        int maxOffset = CommonConfig.MAXIMUM_STARFALL_DISTANCE.get();
        int xOffset = Mth.nextInt(random, minOffset, maxOffset) * (random.nextBoolean() ? 1 : -1);
        int zOffset = Mth.nextInt(random, minOffset, maxOffset) * (random.nextBoolean() ? 1 : -1);
        BlockPos offsetPos = centerPos.offset(xOffset, 0, zOffset);
        ForgeChunkManager.forceChunk(level, EsotericaMod.MOD_ID, offsetPos,SectionPos.blockToSectionCoord(offsetPos.getX()),SectionPos.blockToSectionCoord(offsetPos.getZ()),true,false);
        BlockPos surfacePos = level.getHeightmapPos(MOTION_BLOCKING_NO_LEAVES, offsetPos);
        ForgeChunkManager.forceChunk(level, EsotericaMod.MOD_ID, offsetPos,SectionPos.blockToSectionCoord(offsetPos.getX()),SectionPos.blockToSectionCoord(offsetPos.getZ()),false,false);
        return surfacePos;
    }
}