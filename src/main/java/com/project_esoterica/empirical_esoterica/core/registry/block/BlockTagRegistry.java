package com.project_esoterica.empirical_esoterica.core.registry.block;

import com.project_esoterica.empirical_esoterica.EsotericHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class BlockTagRegistry {
//    public static ITag.INamedTag<Block> RUNEWOOD_LOGS = makeWrapperTag("runewood_logs");

    public static Tag.Named<Block> makeWrapperTag(String id) {
        return BlockTags.createOptional(EsotericHelper.prefix(id));
    }
}