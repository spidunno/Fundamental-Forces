package com.sammy.fufo.core.setup.content.block;

import com.sammy.fufo.FufoMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BlockTagRegistry {
    public static TagKey<Block> STARFALL_ALLOWED = fufoTag("starfall_avoided");

    public static TagKey<Block> fufoTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, FufoMod.fufoPath(path));
    }
    public static TagKey<Block> modTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(path));
    }

    public static TagKey<Block> forgeTag(String name) {
        return BlockTags.create(new ResourceLocation("forge", name));
    }
}