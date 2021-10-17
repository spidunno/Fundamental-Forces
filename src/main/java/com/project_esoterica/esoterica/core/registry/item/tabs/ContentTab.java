package com.project_esoterica.esoterica.core.registry.item.tabs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static com.project_esoterica.esoterica.EsotericaMod.MOD_ID;

public class ContentTab extends CreativeModeTab {
    public static final ContentTab INSTANCE = new ContentTab();
    public ContentTab() {
        super(MOD_ID);
    }

    @Override
    public ItemStack makeIcon() {
        return Items.DIRT.getDefaultInstance();
    }
}
