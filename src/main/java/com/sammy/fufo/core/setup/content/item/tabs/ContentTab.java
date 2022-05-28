package com.sammy.fufo.core.setup.content.item.tabs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static com.sammy.fufo.FufoMod.FUFO;
import static com.sammy.fufo.FufoMod.REGISTRATE;

public class ContentTab extends CreativeModeTab {
    public static final ContentTab INSTANCE = new ContentTab();
    public static @NotNull ContentTab get(){return INSTANCE;}

    public ContentTab() {
        super(FUFO);
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return REGISTRATE.get().get("crack", Item.class).get().getDefaultInstance();
    }
}
