package com.sammy.fundamental_forces.core.setup.content.item;

import com.sammy.fundamental_forces.core.helper.DataHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemTagRegistry {
    public static TagKey<Item> METEOR_FLAME_CATALYST = makeWrapperTag("meteor_flame_catalyst");

    public static TagKey<Item> makeWrapperTag(String id) {
        return TagKey.m_203882_(Registry.ITEM_REGISTRY, DataHelper.prefix(id));
    }
}