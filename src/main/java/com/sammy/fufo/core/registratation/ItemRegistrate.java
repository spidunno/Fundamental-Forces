package com.sammy.fufo.core.registratation;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.item.DebugTool;
import com.sammy.fufo.common.item.DevTool;
import com.sammy.fufo.core.setup.content.item.tabs.ContentTab;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;

import net.minecraft.world.item.Item;

public class ItemRegistrate {
    public static final ItemEntry<Item> CRACK = itemRegister("crack");
    public static final ItemEntry<Item> BOTTLE_OF_CRACK = itemRegister("bottle_of_crack");
    public static final ItemEntry<Item> WISP_BOTTLE = itemRegister("wisp_bottle");
    public static final ItemEntry<Item> DEPLETED_ORTUSITE_CHUNK = itemRegister("depleted_ortusite_chunk");
    public static final ItemEntry<Item> ORTUSITE_CHUNK = itemRegister("ortusite_chunk");
    public static final ItemEntry<Item> ASTRAL_SHARD = itemRegister("astral_shard");
    public static final ItemEntry<DevTool> DEV_TOOL = itemRegister("dev_tool", DevTool::new);
    public static final ItemEntry<DebugTool> DEBUG_TOOL = itemRegister("debug_tool", DebugTool::new);

    public static ItemEntry<Item> itemRegister(String name) {
        return itemRegister(name, Item::new);
    }

    public static <T extends Item> ItemEntry<T> itemRegister(String name, NonNullFunction<Item.Properties, T> factory) {
        return FufoMod.registrate().item(name, factory).tab(ContentTab::get).register();
    }

    public static void register() {
    }
}