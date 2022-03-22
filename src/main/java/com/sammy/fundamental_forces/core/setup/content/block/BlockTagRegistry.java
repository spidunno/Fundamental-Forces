package com.sammy.fundamental_forces.core.setup.content.block;

import com.sammy.fundamental_forces.core.helper.DataHelper;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BlockTagRegistry {
    public static TagKey<Block> TERRACOTTA = makeWrapperTag("terracotta");
    public static TagKey<Block> STARFALL_ALLOWED = makeWrapperTag("starfall_avoided");

    public static TagKey<Block> makeWrapperTag(String id) {
        return TagKey.m_203882_(Registry.BLOCK_REGISTRY, DataHelper.prefix(id));
    }
}