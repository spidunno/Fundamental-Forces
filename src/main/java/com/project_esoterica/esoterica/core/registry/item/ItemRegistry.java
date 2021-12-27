package com.project_esoterica.esoterica.core.registry.item;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.common.item.DevTool;
import com.project_esoterica.esoterica.core.registry.block.BlockRegistry;
import com.project_esoterica.esoterica.core.registry.item.tabs.ContentTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EsotericaMod.MOD_ID);

    public static Item.Properties DEFAULT_PROPERTIES() {
        return new Item.Properties().tab(ContentTab.INSTANCE);
    }
    public static Item.Properties GEAR_PROPERTIES() {
        return new Item.Properties().tab(ContentTab.INSTANCE).stacksTo(1);
    }

    public static final RegistryObject<Item> ASTEROID_ROCK = ITEMS.register("asteroid_rock", () -> new BlockItem(BlockRegistry.ASTEROID_ROCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ASTEROID_CHUNK = ITEMS.register("asteroid_chunk", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ASTRAL_SHARD = ITEMS.register("astral_shard", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> DEV_TOOL = ITEMS.register("dev_tool", () -> new DevTool(GEAR_PROPERTIES()));
}