package com.space_mod_group.space_mod.core.data;

import com.space_mod_group.space_mod.SpaceHelper;
import com.space_mod_group.space_mod.SpaceMod;
import com.space_mod_group.space_mod.core.registry.*;
import net.minecraft.block.Block;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.space_mod_group.space_mod.SpaceMod.MOD_ID;

public class SpaceModLang extends LanguageProvider
{
    public SpaceModLang(DataGenerator gen)
    {
        super(gen, MOD_ID, "en_us");
    }

    @Override
    public String getName()
    {
        return "Space Mod Lang Entries";
    }

    @Override
    protected void addTranslations()
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BlockRegistry.BLOCKS.getEntries());
        SpaceHelper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        SpaceHelper.takeAll(blocks, i -> i.get() instanceof WallSignBlock);
        Set<RegistryObject<Item>> items = new HashSet<>(ItemRegistry.ITEMS.getEntries());
        SpaceHelper.takeAll(items, i -> i.get() instanceof BlockItem);
        Set<RegistryObject<SoundEvent>> sounds = new HashSet<>(SoundRegistry.SOUNDS.getEntries());
        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(EnchantmentRegistry.ENCHANTMENTS.getEntries());
        Set<RegistryObject<Effect>> effects = new HashSet<>(PotionEffectRegistry.EFFECTS.getEntries());
        blocks.forEach(b ->
        {
            String name = b.get().getDescriptionId().replaceFirst("block." + MOD_ID + ".", "");
            name = SpaceHelper.toTitleCase(specialBlockNameChanges(name), "_");
            add(b.get().getDescriptionId(), name);
        });

        items.forEach(i ->
        {
            String name = i.get().getDescriptionId().replaceFirst("item." + MOD_ID + ".", "");
            name = SpaceHelper.toTitleCase(specialBlockNameChanges(name), "_");
            add(i.get().getDescriptionId(), name);
        });

        sounds.forEach(s -> {
            String name = SpaceHelper.toTitleCase(s.getId().getPath(), "_");
            add(MOD_ID + ".subtitle." + s.getId().getPath(), name);
        });
        enchantments.forEach(e -> {
            String name = SpaceHelper.toTitleCase(e.getId().getPath(), "_");
            add(e.get().getDescriptionId(), name);
        });

        effects.forEach(e -> {
            String name = SpaceHelper.toTitleCase(e.getId().getPath(), "_");
            add("effect." + MOD_ID + "." + e.get().getRegistryName().getPath(), name);
        });


        add("itemGroup." + MOD_ID, "Space Mod Group");

        add("enchantment.space_mod.enchantment_name.desc", "Enchantment Description Compat");
    }

    public String specialBlockNameChanges(String name)
    {
        if ((!name.endsWith("_bricks")))
        {
            if (name.contains("bricks"))
            {
                name = name.replaceFirst("bricks", "brick");
            }
        }
        if (name.contains("_fence") || name.contains("_button") || name.contains("pressure_plate"))
        {
            if (name.contains("planks"))
            {
                name = name.replaceFirst("_planks", "");
            }
        }
        return name;
    }
}