package com.space_mod_group.space_mod;

import com.space_mod_group.space_mod.core.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.space_mod_group.space_mod.SpaceMod.MOD_ID;

public class SpaceHelper
{
    public static void updateState(World worldIn, BlockPos pos)
    {
        updateState(worldIn.getBlockState(pos), worldIn, pos);
    }

    public static void updateState(BlockState state, World worldIn, BlockPos pos)
    {
        worldIn.sendBlockUpdated(pos, state, state, 2);
    }

    public static boolean areWeOnClient(World world)
    {
        return world.isClientSide;
    }

    public static boolean areWeOnServer(World world)
    {
        return !areWeOnClient(world);
    }

    public static ResourceLocation prefix(String path)
    {
        return new ResourceLocation(MOD_ID, path);
    }

    public static <T> ArrayList<T> toArrayList(T... items)
    {
        return new ArrayList<>(Arrays.asList(items));
    }

    public static <T> ArrayList<T> toArrayList(Stream<T> items)
    {
        return items.collect(Collectors.toCollection(ArrayList::new));
    }

    public static String toTitleCase(String givenString, String regex)
    {
        String[] stringArray = givenString.split(regex);
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : stringArray)
        {
            stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(regex);
        }
        return stringBuilder.toString().trim().replaceAll(regex, " ").substring(0, stringBuilder.length() - 1);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Collection<T> takeAll(Collection<? extends T> src, T... items)
    {
        List<T> ret = Arrays.asList(items);
        for (T item : items)
        {
            if (!src.contains(item))
            {
                return Collections.emptyList();
            }
        }
        if (!src.removeAll(ret))
        {
            return Collections.emptyList();
        }
        return ret;
    }

    public static <T> Collection<T> takeAll(Collection<T> src, Predicate<T> pred)
    {
        List<T> ret = new ArrayList<>();

        Iterator<T> iter = src.iterator();
        while (iter.hasNext())
        {
            T item = iter.next();
            if (pred.test(item))
            {
                iter.remove();
                ret.add(item);
            }
        }

        if (ret.isEmpty())
        {
            return Collections.emptyList();
        }
        return ret;
    }

    public static Block[] getModBlocks(Class<?>... blockClasses)
    {
        return getModBlocks(b -> Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(b)));
    }

    @Nonnull
    public static Block[] getModBlocks(Predicate<Block> predicate)
    {
        Collection<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        ArrayList<Block> matchingBlocks = new ArrayList<>();
        for (RegistryObject<Block> registryObject : blocks)
        {
            if (predicate.test(registryObject.get()))
            {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }
}