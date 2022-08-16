package team.lodestar.fufo.registry.common;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import team.lodestar.fufo.FufoMod;

public class FufoTags {
    public static TagKey<Item> METEOR_FLAME_CATALYST = fufoItemTag("meteor_flame_catalyst");

    public static TagKey<Block> STARFALL_ALLOWED = fufoBlockTag("starfall_avoided");


    public static TagKey<Item> fufoItemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, FufoMod.fufoPath(path));
    }

    public static TagKey<Item> modItemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(path));
    }

    public static TagKey<Item> forgeItemTag(String name) {
        return ItemTags.create(new ResourceLocation("forge", name));
    }

    public static TagKey<Block> fufoBlockTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, FufoMod.fufoPath(path));
    }

    public static TagKey<Block> modBlockTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(path));
    }

    public static TagKey<Block> forgeBlockTag(String name) {
        return net.minecraft.tags.BlockTags.create(new ResourceLocation("forge", name));
    }
}