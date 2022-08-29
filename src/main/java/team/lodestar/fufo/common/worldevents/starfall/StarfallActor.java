package team.lodestar.fufo.common.worldevents.starfall;

import net.minecraft.util.RandomSource;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.capability.FufoChunkDataCapability;
import team.lodestar.fufo.config.CommonConfig;
import team.lodestar.fufo.registry.common.FufoTags;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.setup.LodestoneBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.world.ForgeChunkManager;

import java.util.ArrayList;
import java.util.Random;

import static net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;

public class StarfallActor {
    public final String id;
    public final int startingCountdown;

    public StarfallActor(String id, int startingCountdown) {
        this.id = id;
        this.startingCountdown = startingCountdown;
    }

    public static boolean chunkChangesCheck(ServerLevel level, BlockPos pos, int range) {
        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                LevelChunk chunk = level.getChunk(SectionPos.blockToSectionCoord(pos.getX()) + x, SectionPos.blockToSectionCoord(pos.getZ()) + z);
                int chunkChanges = FufoChunkDataCapability.getCapability(chunk).chunkChanges;
                if (chunkChanges >= CommonConfig.MAXIMUM_CHUNK_CHANGES.getConfigValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static ArrayList<BlockPos> nearbyBlockList(ServerLevel level, BlockPos centerPos) {
        int size = CommonConfig.STARFALL_SAFETY_RANGE.getConfigValue();
        ArrayList<BlockPos> result = new ArrayList<>();
        //TODO: make this better.
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
            if (state.is(net.minecraft.tags.BlockTags.FEATURES_CANNOT_REPLACE)) {
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
        TagKey<Block>[] tags = new TagKey[]{FufoTags.STARFALL_ALLOWED, LodestoneBlockTags.TERRACOTTA, net.minecraft.tags.BlockTags.LUSH_GROUND_REPLACEABLE, net.minecraft.tags.BlockTags.MUSHROOM_GROW_BLOCK, net.minecraft.tags.BlockTags.LOGS, net.minecraft.tags.BlockTags.LEAVES, net.minecraft.tags.BlockTags.SNOW, net.minecraft.tags.BlockTags.SAND, net.minecraftforge.common.Tags.Blocks.SANDSTONE};
        for (TagKey<Block> tag : tags) {
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

    public int randomizedCountdown(RandomSource random, int parentCountdown) {
        return parentCountdown;
    }

    public int randomizedCountdown(RandomSource random) {
        return randomizedCountdown(random, startingCountdown);
    }

    public void act(ServerLevel level, BlockPos targetPos) {
        LevelChunk chunk = level.getChunkAt(targetPos);
        FufoChunkDataCapability.getCapabilityOptional(chunk).ifPresent(chunkDataCapability -> chunkDataCapability.chunkChanges = CommonConfig.MAXIMUM_CHUNK_CHANGES.getConfigValue());
    }

    public final boolean canFall(ServerLevel level, BlockPos pos) {
        if (level.isFluidAtPosition(pos.below(), p -> !p.isEmpty())) {
            return false;
        }
        if (!level.isInWorldBounds(pos)) {
            return false;
        }
        boolean heightmap = chunkChangesCheck(level, pos, 2);
        boolean blocks = blockCheck(level, nearbyBlockList(level, pos));
        return heightmap && blocks;
    }

    public BlockPos randomizedStarfallTargetPosition(ServerLevel level, BlockPos centerPos) {
        RandomSource random = level.random;
        int minOffset = CommonConfig.MINIMUM_STARFALL_DISTANCE.getConfigValue();
        int maxOffset = CommonConfig.MAXIMUM_STARFALL_DISTANCE.getConfigValue();
        int xOffset = Mth.nextInt(random, minOffset, maxOffset) * (random.nextBoolean() ? 1 : -1);
        int zOffset = Mth.nextInt(random, minOffset, maxOffset) * (random.nextBoolean() ? 1 : -1);
        BlockPos offsetPos = centerPos.offset(xOffset, 0, zOffset);
        return heightmapPosAt(MOTION_BLOCKING_NO_LEAVES, level, offsetPos);
    }

    public Vec3 randomizedStarfallStartPosition(ServerLevel level, BlockPos targetPos, BlockPos centerPos) {
        Vec3 targetVec = new Vec3(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        Vec3 centerVec = new Vec3(centerPos.getX(), centerPos.getY(), centerPos.getZ());
        double distance = targetVec.distanceTo(centerVec) * (Mth.nextDouble(level.random, 0.5f, 5f)); //0.75-2.5x towards the center position
        Vec3 direction = targetVec.vectorTo(centerVec).normalize().yRot(Mth.nextFloat(level.random, -0.26f, 0.26f)).multiply(distance, 1, distance); //rotated direction towards centerPos
        Vec3 spawnVec = centerVec.add(direction);
        return BlockHelper.fromBlockPos(heightmapPosAt(MOTION_BLOCKING_NO_LEAVES, level, new BlockPos(spawnVec))).add(0, CommonConfig.STARFALL_SPAWN_HEIGHT.getConfigValue(), 0);//200 blocks above heightmap level at spawnVec
    }

    public static BlockPos heightmapPosAt(Heightmap.Types type, ServerLevel level, BlockPos pos) {

        ForgeChunkManager.forceChunk(level, FufoMod.FUFO, pos, SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()), true, false);
        BlockPos surfacePos = level.getHeightmapPos(type, pos);
        while (level.getBlockState(surfacePos.below()).is(net.minecraft.tags.BlockTags.LOGS)) {
            //TODO: it'd be best to replace this while statement with a custom Heightmap.Types' type.
            // However the Heightmap.Types enum isn't an IExtendibleEnum, we would need to make a dreaded forge PR for them to make it one
            surfacePos = surfacePos.below();
        }
        ForgeChunkManager.forceChunk(level, FufoMod.FUFO, pos, SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()), false, false);
        return surfacePos;
    }
}