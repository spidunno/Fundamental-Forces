package com.project_esoterica.esoterica.core.registry.item.tabs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ContentTab extends CreativeModeTab {
    public static final ContentTab INSTANCE = new ContentTab();
    public ContentTab() {
        super("esoterica_content");
    }

    @Override
    public ItemStack makeIcon() {
        return Items.DIRT.getDefaultInstance();
    }
}
