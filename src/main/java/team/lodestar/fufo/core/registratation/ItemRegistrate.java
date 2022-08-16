package team.lodestar.fufo.core.registratation;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.item.DebugTool;
import team.lodestar.fufo.common.item.DevTool;
import team.lodestar.fufo.core.setup.content.item.tabs.ContentTab;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;

import net.minecraft.world.item.Item;

public class ItemRegistrate {
    public static final ItemEntry<Item> CRACK = itemRegister("crack");
    public static final ItemEntry<Item> DEPLETED_ORTUSITE_CHUNK = itemRegister("depleted_ortusite_chunk");
    public static final ItemEntry<Item> ORTUSITE_CHUNK = itemRegister("ortusite_chunk");
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