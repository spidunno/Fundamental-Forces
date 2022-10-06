package team.lodestar.fufo.common.starfall.actors;

import net.minecraft.util.RandomSource;
import team.lodestar.fufo.common.capability.FufoChunkDataCapability;
import team.lodestar.fufo.common.starfall.StarfallSafetyHandler;
import team.lodestar.fufo.config.CommonConfig;
import team.lodestar.lodestone.helpers.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;

public abstract class AbstractStarfallActor {
    public final String id;
    public final int startingCountdown;

    public AbstractStarfallActor(String id, int startingCountdown) {
        this.id = id;
        this.startingCountdown = startingCountdown;
    }

    public static boolean isBlockImportant(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (level.isFluidAtPosition(pos, p -> !p.isEmpty())) {
            return true;
        }
        return state.getMaterial().isSolid() && !state.isAir() && !state.getMaterial().isReplaceable() && state.getMaterial().blocksMotion();
    }

    public int getStarfallCountdown(RandomSource random) {
        return startingCountdown;
    }

    public void act(ServerLevel level, BlockPos targetPos) {
        LevelChunk chunk = level.getChunkAt(targetPos);
        FufoChunkDataCapability.getCapabilityOptional(chunk).ifPresent(chunkDataCapability -> chunkDataCapability.chunkChanges = CommonConfig.MAXIMUM_CHUNK_CHANGES.getConfigValue());
    }

    public boolean canAct(ServerLevel level, BlockPos pos) {
        if (level.isFluidAtPosition(pos.below(), p -> !p.isEmpty())) {
            return false;
        }
        if (!level.isInWorldBounds(pos)) {
            return false;
        }
        boolean heightmap = StarfallSafetyHandler.chunkChangesCheck(level, pos, 2);
        boolean blocks = StarfallSafetyHandler.stateTagCheck(level, StarfallSafetyHandler.nearbyBlockList(level, pos));
        return heightmap && blocks;
    }

    public BlockPos getStarfallImpactPosition(ServerLevel level, BlockPos centerPos) {
        RandomSource random = level.random;
        int minOffset = CommonConfig.MINIMUM_STARFALL_DISTANCE.getConfigValue();
        int maxOffset = CommonConfig.MAXIMUM_STARFALL_DISTANCE.getConfigValue();
        int xOffset = Mth.nextInt(random, minOffset, maxOffset) * (random.nextBoolean() ? 1 : -1);
        int zOffset = Mth.nextInt(random, minOffset, maxOffset) * (random.nextBoolean() ? 1 : -1);
        BlockPos offsetPos = centerPos.offset(xOffset, 0, zOffset);
        return StarfallSafetyHandler.heightmapPosAt(MOTION_BLOCKING_NO_LEAVES, level, offsetPos);
    }

    public Vec3 getStarfallStartPosition(ServerLevel level, BlockPos targetPos, BlockPos centerPos) {
        Vec3 targetVec = new Vec3(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        Vec3 centerVec = new Vec3(centerPos.getX(), centerPos.getY(), centerPos.getZ());
        double distance = targetVec.distanceTo(centerVec) * (Mth.nextDouble(level.random, 0.5f, 5f));
        Vec3 direction = targetVec.vectorTo(centerVec).normalize().yRot(Mth.nextFloat(level.random, -0.26f, 0.26f)).multiply(distance, 1, distance);
        Vec3 spawnVec = centerVec.add(direction);
        return BlockHelper.fromBlockPos(StarfallSafetyHandler.heightmapPosAt(MOTION_BLOCKING_NO_LEAVES, level, new BlockPos(spawnVec))).add(0, CommonConfig.STARFALL_SPAWN_HEIGHT.getConfigValue(), 0);
    }
}