package com.project_esoterica.esoterica.core.setup.item;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.common.item.DevTool;
import com.project_esoterica.esoterica.core.setup.block.BlockRegistry;
import com.project_esoterica.esoterica.core.setup.item.tabs.ContentTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EsotericaMod.MODID);

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

    public static final RegistryObject<Item> CHARRED_ROCK = ITEMS.register("charred_rock", () -> new BlockItem(BlockRegistry.CHARRED_ROCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_CHARRED_ROCK = ITEMS.register("polished_charred_rock", () -> new BlockItem(BlockRegistry.POLISHED_CHARRED_ROCK.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> CHARRED_ROCK_SLAB = ITEMS.register("charred_rock_slab", () -> new BlockItem(BlockRegistry.CHARRED_ROCK_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_CHARRED_ROCK_SLAB = ITEMS.register("polished_charred_rock_slab", () -> new BlockItem(BlockRegistry.POLISHED_CHARRED_ROCK_SLAB.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> CHARRED_ROCK_STAIRS = ITEMS.register("charred_rock_stairs", () -> new BlockItem(BlockRegistry.CHARRED_ROCK_STAIRS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_CHARRED_ROCK_STAIRS = ITEMS.register("polished_charred_rock_stairs", () -> new BlockItem(BlockRegistry.POLISHED_CHARRED_ROCK_STAIRS.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> VOLCANIC_GLASS = ITEMS.register("volcanic_glass", () -> new BlockItem(BlockRegistry.VOLCANIC_GLASS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SCORCHED_EARTH = ITEMS.register("scorched_earth", () -> new BlockItem(BlockRegistry.SCORCHED_EARTH.get(), DEFAULT_PROPERTIES()));


}