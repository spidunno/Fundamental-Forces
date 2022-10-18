package team.lodestar.fufo.common.starfall;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.world.ForgeChunkManager;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.capability.FufoChunkDataCapability;
import team.lodestar.fufo.common.starfall.actors.AbstractStarfallActor;
import team.lodestar.fufo.config.CommonConfig;
import team.lodestar.fufo.registry.common.FufoTags;
import team.lodestar.lodestone.setup.LodestoneBlockTags;

import java.util.ArrayList;

public class StarfallSafetyHandler {
    //TODO: rewrite all of this
    // a 5x5 area of chunks is scanned for invalid chunks, which have had more than 50 surface changes done by a player. If any invalid chunks are found we look somewhere else
    // we scan a pretty big area around the impact site, think like a 21x21x21 zone, cache all the non-empty blocks and do some checks on them. We also cache the amount of fluid states
    // if the amount of fluid states makes up more than 10% of both blocks and fluids combined, we look somewhere else
    // if the amount of 'starfall invalid' blocks (blocks not tagged with starfall_permitted) makes up more than 20% of all blocks, we look somewhere else
    // if we find more than let's say, ten 'illegal' blocks (think bedrock, obsidian, crafting table, chest, etc) we look somewhere else
    // We also do some heightmap stuff, the mean of heightmap values in the region has to be under some arbitrary threshold

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

    public static boolean stateTagCheck(ServerLevel level, ArrayList<BlockPos> arrayList) {
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
            if (!stateTagCheck(level, state)) {
                failed += 1;
            }
            if (failed >= failToAbort) {
                return false;
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
                    if (AbstractStarfallActor.isBlockImportant(level, pos)) {
                        result.add(pos);
                    }
                }
            }
        }
        return result;
    }

    public static boolean blockEntityCheck(ServerLevel level, BlockPos pos) {
        return level.getBlockEntity(pos) == null;
    }

    @SuppressWarnings("all")
    public static boolean stateTagCheck(ServerLevel level, BlockState state) {
        TagKey<Block>[] tags = new TagKey[]{FufoTags.STARFALL_ALLOWED, LodestoneBlockTags.TERRACOTTA, net.minecraft.tags.BlockTags.LUSH_GROUND_REPLACEABLE, net.minecraft.tags.BlockTags.MUSHROOM_GROW_BLOCK, net.minecraft.tags.BlockTags.LOGS, net.minecraft.tags.BlockTags.LEAVES, net.minecraft.tags.BlockTags.SNOW, net.minecraft.tags.BlockTags.SAND, net.minecraftforge.common.Tags.Blocks.SANDSTONE};
        for (TagKey<Block> tag : tags) {
            if (state.is(tag)) {
                return true;
            }
        }
        return false;
    }

    //TODO: move this to some sorta helper method
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
