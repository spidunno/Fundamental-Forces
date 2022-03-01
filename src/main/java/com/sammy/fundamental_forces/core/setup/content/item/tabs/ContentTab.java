package com.sammy.fundamental_forces.core.setup.content.item.tabs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static com.sammy.fundamental_forces.FundamentalForcesMod.MODID;

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
