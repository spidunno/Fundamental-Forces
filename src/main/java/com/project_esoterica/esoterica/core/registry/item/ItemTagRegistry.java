package com.project_esoterica.esoterica.core.registry.item;

import com.project_esoterica.esoterica.EsotericHelper;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class ItemTagRegistry {
//    public static Tag.Named<Item> RUNEWOOD_LOGS = makeWrapperTag("runewood_logs");

    public static Tag.Named<Item> makeWrapperTag(String id) {
        return ItemTags.createOptional(EsotericHelper.prefix(id));
    }
}