package com.project_esoterica.esoterica.core.setup.item.tabs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static com.project_esoterica.esoterica.EsotericaMod.MODID;

public class ContentTab extends CreativeModeTab {
    public static final ContentTab INSTANCE = new ContentTab();

    public ContentTab() {
        super(MODID);
    }

    @Override
    public ItemStack makeIcon() {
        return Items.DIRT.getDefaultInstance();
    }
}
