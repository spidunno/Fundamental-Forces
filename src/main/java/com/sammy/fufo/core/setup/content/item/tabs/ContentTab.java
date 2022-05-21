package com.sammy.fufo.core.setup.content.item.tabs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import static com.sammy.fufo.FufoMod.FUFO;

public class ContentTab extends CreativeModeTab {
    public static final ContentTab INSTANCE = new ContentTab();
    public static @NotNull ContentTab get(){return INSTANCE;}

    public ContentTab() {
        super(FUFO);
    }

    @Override
    public ItemStack makeIcon() {
        return Items.DIRT.getDefaultInstance();
    }
}
