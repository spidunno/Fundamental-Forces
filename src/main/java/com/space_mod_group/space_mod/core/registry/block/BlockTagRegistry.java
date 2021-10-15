package com.space_mod_group.space_mod.core.registry.block;

import com.space_mod_group.space_mod.SpaceHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;

public class BlockTagRegistry
{
//    public static ITag.INamedTag<Block> RUNEWOOD_LOGS = makeWrapperTag("runewood_logs");

    public static Tag.Named<Block> makeWrapperTag(String id)
    {
        return BlockTags.createOptional(SpaceHelper.prefix(id));
    }
}