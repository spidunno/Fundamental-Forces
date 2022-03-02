package com.sammy.fundamental_forces.core.setup.content.item;

import com.sammy.fundamental_forces.core.helper.DataHelper;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class ItemTagRegistry {
    public static Tag.Named<Item> METEOR_FLAME_CATALYST = makeWrapperTag("meteor_flame_catalyst");

    public static Tag.Named<Item> makeWrapperTag(String id) {
        return ItemTags.createOptional(DataHelper.prefix(id));
    }
}