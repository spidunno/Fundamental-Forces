package com.sammy.fufo.core.setup.content.item;

import com.sammy.fufo.FufoMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemTagRegistry {
    public static TagKey<Item> METEOR_FLAME_CATALYST = fufoTag("meteor_flame_catalyst");

    public static TagKey<Item> fufoTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, FufoMod.fufoPath(path));
    }
    public static TagKey<Item> modTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(path));
    }

    public static TagKey<Item> forgeTag(String name) {
        return ItemTags.create(new ResourceLocation("forge", name));
    }
}