package com.space_mod_group.space_mod.core.registry.item;

import com.space_mod_group.space_mod.SpaceHelper;
import net.minecraft.world.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;

public class ItemTagRegistry
{
//    public static ITag.INamedTag<Item> RUNEWOOD_LOGS = makeWrapperTag("runewood_logs");

    public static Tag.Named<Item> makeWrapperTag(String id)
    {
        return ItemTags.createOptional(SpaceHelper.prefix(id));
    }
}