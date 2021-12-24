package com.project_esoterica.esoterica;

import com.project_esoterica.esoterica.core.registry.block.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.project_esoterica.esoterica.EsotericaMod.MOD_ID;
import static net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;

public class EsotericaHelper {
    public static final Random RANDOM = new Random();

    public static void updateState(Level level, BlockPos pos) {
        updateState(level.getBlockState(pos), level, pos);
    }

    public static void updateState(BlockState state, Level level, BlockPos pos) {
        level.sendBlockUpdated(pos, state, state, 3);
    }

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static <T> ArrayList<T> toArrayList(T... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    public static <T> ArrayList<T> toArrayList(Stream<T> items) {
        return items.collect(Collectors.toCollection(ArrayList::new));
    }

    public static String toTitleCase(String givenString, String regex) {
        String[] stringArray = givenString.split(regex);
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : stringArray) {
            stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(regex);
        }
        return stringBuilder.toString().trim().replaceAll(regex, " ").substring(0, stringBuilder.length() - 1);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Collection<T> takeAll(Collection<? extends T> src, T... items) {
        List<T> ret = Arrays.asList(items);
        for (T item : items) {
            if (!src.contains(item)) {
                return Collections.emptyList();
            }
        }
        if (!src.removeAll(ret)) {
            return Collections.emptyList();
        }
        return ret;
    }

    public static <T> Collection<T> takeAll(Collection<T> src, Predicate<T> pred) {
        List<T> ret = new ArrayList<>();

        Iterator<T> iter = src.iterator();
        while (iter.hasNext()) {
            T item = iter.next();
            if (pred.test(item)) {
                iter.remove();
                ret.add(item);
            }
        }

        if (ret.isEmpty()) {
            return Collections.emptyList();
        }
        return ret;
    }

    public static Block[] getModBlocks(Class<?>... blockClasses) {
        return getModBlocks(p -> Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(b)));
    }

    @Nonnull
    public static Block[] getModBlocks(Predicate<Block> predicate) {
        Collection<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        ArrayList<Block> matchingBlocks = new ArrayList<>();
        for (RegistryObject<Block> registryObject : blocks) {
            if (predicate.test(registryObject.get())) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    public static Vec3 randomUnitVec3() {
        return new Vec3(1, 1, 1)
                .xRot(RANDOM.nextFloat() * 6.28f)
                .yRot(RANDOM.nextFloat() * 6.28f)
                .zRot(RANDOM.nextFloat() * 6.28f);
    }
    public static Vec3 vec3FromPos(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }
    public static BlockPos posFromVec3(Vec3 vec3) {
        return new BlockPos(vec3.x,vec3.y,vec3.z);
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