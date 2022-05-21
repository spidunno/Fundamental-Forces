package com.sammy.fufo.core.registratation;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.core.setup.content.item.tabs.ContentTab;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ItemRegistrate{
    public static final ItemEntry<Item> CRACK = itemRegister("crack");
    public static final ItemEntry<Item> BOTTLE_OF_CRACK = itemRegister("bottle_of_crack");
    public static final ItemEntry<Item> WISP_BOTTLE = itemRegister("wisp_bottle");
    public static final ItemEntry<Item> ORTUSITE_CHUNK = itemRegister("ortusite_chunk");

    public static ItemEntry<Item> itemRegister(String name){
        return FufoMod.registrate().item(name,Item::new).tab(ContentTab::get).register();
    }
}
