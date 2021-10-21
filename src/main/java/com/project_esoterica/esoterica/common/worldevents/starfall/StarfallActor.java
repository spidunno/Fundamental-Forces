package com.project_esoterica.esoterica.common.worldevents.starfall;

import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.core.config.CommonConfig;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
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
        return EsotericaHelper.heightmapPosAt(MOTION_BLOCKING_NO_LEAVES, level, offsetPos);
    }

    public Vec3 randomizedStarfallStartPosition(ServerLevel level, BlockPos targetPos, BlockPos centerPos) {
        Vec3 targetVec = new Vec3(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        Vec3 centerVec = new Vec3(centerPos.getX(), centerPos.getY(), centerPos.getZ());
        double distance = targetVec.distanceTo(centerVec)*(Mth.nextDouble(level.random, 0.5f, 5f)); //0.75-2.5x towards the center position
        Vec3 direction = targetVec.vectorTo(centerVec).normalize().yRot(Mth.nextFloat(level.random, -0.26f, 0.26f)).multiply(distance,1, distance); //rotated direction towards centerPos
        Vec3 spawnVec = centerVec.add(direction);
        return EsotericaHelper.vec3FromPos(EsotericaHelper.heightmapPosAt(MOTION_BLOCKING_NO_LEAVES, level, EsotericaHelper.posFromVec3(spawnVec))).add(0, 200, 0);//200 blocks above heightmap level at spawnVec
    }
}