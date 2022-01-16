package com.project_esoterica.esoterica.core.setup.block;

import com.project_esoterica.esoterica.core.helper.DataHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class BlockTagRegistry {
    public static Tag.Named<Block> TERRACOTTA = makeWrapperTag("terracotta");
    public static Tag.Named<Block> STARFALL_ALLOWED = makeWrapperTag("starfall_avoided");

    public static Tag.Named<Block> makeWrapperTag(String id) {
        return BlockTags.createOptional(DataHelper.prefix(id));
    }
}