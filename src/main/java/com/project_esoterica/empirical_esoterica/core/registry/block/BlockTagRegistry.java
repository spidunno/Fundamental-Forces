package com.project_esoterica.empirical_esoterica.core.registry.block;

import com.project_esoterica.empirical_esoterica.EsotericHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class BlockTagRegistry {
    public static Tag.Named<Block> STARFALL_ALLOWED = makeWrapperTag("starfall_avoided");

    public static Tag.Named<Block> makeWrapperTag(String id) {
        return BlockTags.createOptional(EsotericHelper.prefix(id));
    }
}