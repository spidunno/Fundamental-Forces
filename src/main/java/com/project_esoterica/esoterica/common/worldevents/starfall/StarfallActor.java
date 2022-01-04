package com.project_esoterica.esoterica.common.worldevents.starfall;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.common.capability.ChunkDataCapability;
import com.project_esoterica.esoterica.core.config.CommonConfig;
import com.project_esoterica.esoterica.core.helper.DataHelper;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
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

    public void act(ServerLevel level, BlockPos targetPos) {
        LevelChunk chunk = level.getChunkAt(targetPos);
        ChunkDataCapability.getCapability(chunk).ifPresent(chunkDataCapability -> chunkDataCapability.chunkChanges = CommonConfig.MAXIMUM_CHUNK_CHANGES.get());
    }

    public final boolean canFall(ServerLevel level, BlockPos pos) {
        if (level.isFluidAtPosition(pos.below(), p -> !p.isEmpty())) {
            return false;
        }
        boolean heightmap = WorldEventManager.heightmapCheck(level, pos, 2);
        boolean blocks = WorldEventManager.blockCheck(level, WorldEventManager.nearbyBlockList(level, pos));
        return heightmap && blocks;
    }

    public BlockPos randomizedStarfallTargetPosition(ServerLevel level, BlockPos centerPos) {
        Random random = level.random;
        int minOffset = CommonConfig.MINIMUM_STARFALL_DISTANCE.get();
        int maxOffset = CommonConfig.MAXIMUM_STARFALL_DISTANCE.get();
        int xOffset = Mth.nextInt(random, minOffset, maxOffset) * (random.nextBoolean() ? 1 : -1);
        int zOffset = Mth.nextInt(random, minOffset, maxOffset) * (random.nextBoolean() ? 1 : -1);
        BlockPos offsetPos = centerPos.offset(xOffset, 0, zOffset);
        return heightmapPosAt(MOTION_BLOCKING_NO_LEAVES, level, offsetPos);
    }

    public Vec3 randomizedStarfallStartPosition(ServerLevel level, BlockPos targetPos, BlockPos centerPos) {
        Vec3 targetVec = new Vec3(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        Vec3 centerVec = new Vec3(centerPos.getX(), centerPos.getY(), centerPos.getZ());
        double distance = targetVec.distanceTo(centerVec)*(Mth.nextDouble(level.random, 0.5f, 5f)); //0.75-2.5x towards the center position
        Vec3 direction = targetVec.vectorTo(centerVec).normalize().yRot(Mth.nextFloat(level.random, -0.26f, 0.26f)).multiply(distance,1, distance); //rotated direction towards centerPos
        Vec3 spawnVec = centerVec.add(direction);
        return DataHelper.fromBlockPos(heightmapPosAt(MOTION_BLOCKING_NO_LEAVES, level, new BlockPos(spawnVec))).add(0, CommonConfig.STARFALL_SPAWN_LEVEL.get(), 0);//200 blocks above heightmap level at spawnVec
    }
    public static BlockPos heightmapPosAt(Heightmap.Types type, ServerLevel level, BlockPos pos)
    {
        ForgeChunkManager.forceChunk(level, EsotericaMod.MOD_ID, pos, SectionPos.blockToSectionCoord(pos.getX()),SectionPos.blockToSectionCoord(pos.getZ()),true,false);
        BlockPos surfacePos = level.getHeightmapPos(type, pos);
        while (level.getBlockState(surfacePos.below()).is(BlockTags.LOGS))
        {
            //TODO: it'd be best to replace this while statement with a custom Heightmap.Types' type.
            // However the Heightmap.Types enum isn't an IExtendibleEnum, we would need to make a dreaded forge PR for them to make it one
            surfacePos = surfacePos.below();
        }
        ForgeChunkManager.forceChunk(level, EsotericaMod.MOD_ID, pos,SectionPos.blockToSectionCoord(pos.getX()),SectionPos.blockToSectionCoord(pos.getZ()),false,false);
        return surfacePos;
    }
}