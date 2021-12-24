package com.project_esoterica.esoterica.core.data;

import com.project_esoterica.esoterica.core.helper.DataHelper;
import com.project_esoterica.esoterica.core.registry.PotionEffectRegistry;
import com.project_esoterica.esoterica.core.registry.SoundRegistry;
import com.project_esoterica.esoterica.core.registry.block.BlockRegistry;
import com.project_esoterica.esoterica.core.registry.item.EnchantmentRegistry;
import com.project_esoterica.esoterica.core.registry.item.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

import static com.project_esoterica.esoterica.EsotericaMod.MOD_ID;

public class SpaceModLang extends LanguageProvider {
    public SpaceModLang(DataGenerator gen) {
        super(gen, MOD_ID, "en_us");
    }

    @Override
    public String getName() {
        return "Space Mod Lang Entries";
    }

    @Override
    protected void addTranslations() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BlockRegistry.BLOCKS.getEntries());
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallSignBlock);
        Set<RegistryObject<Item>> items = new HashSet<>(ItemRegistry.ITEMS.getEntries());
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem);
        Set<RegistryObject<SoundEvent>> sounds = new HashSet<>(SoundRegistry.SOUNDS.getEntries());
        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(EnchantmentRegistry.ENCHANTMENTS.getEntries());
        Set<RegistryObject<MobEffect>> effects = new HashSet<>(PotionEffectRegistry.EFFECTS.getEntries());
        blocks.forEach(b ->
        {
            String name = b.get().getDescriptionId().replaceFirst("block." + MOD_ID + ".", "");
            name = DataHelper.toTitleCase(specialBlockNameChanges(name), "_");
            add(b.get().getDescriptionId(), name);
        });

        items.forEach(i ->
        {
            String name = i.get().getDescriptionId().replaceFirst("item." + MOD_ID + ".", "");
            name = DataHelper.toTitleCase(specialBlockNameChanges(name), "_");
            add(i.get().getDescriptionId(), name);
        });

        sounds.forEach(s -> {
            String name = DataHelper.toTitleCase(s.getId().getPath(), "_");
            add(MOD_ID + ".subtitle." + s.getId().getPath(), name);
        });
        enchantments.forEach(e -> {
            String name = DataHelper.toTitleCase(e.getId().getPath(), "_");
            add(e.get().getDescriptionId(), name);
        });

        effects.forEach(e -> {
            String name = DataHelper.toTitleCase(e.getId().getPath(), "_");
            add("effect." + MOD_ID + "." + e.get().getRegistryName().getPath(), name);
        });


        add("itemGroup." + MOD_ID, "Empirical Esoterica");

        addCommandKey("devsetup", "Command Successful you fuckhead.");

        addCommandKey("fallstar_natural_position", "Natural starfall scheduled to fall at the given position.");
        addCommandKey("fallstar_natural_target", "Natural starfall scheduled for the given target.");
        addCommandKey("fallstar_artificial_position", "Artificial starfall scheduled to fall at the given position.");
        addCommandKey("fallstar_artificial_target", "Artificial starfall scheduled for the given target.");

        addCommandOutput("error.starfall.result", "No such starfall result exists.");

        addCommandOutput("checkarea.report.success", "Success: Area viable for starfalls!");
        addCommandOutput("checkarea.report.failure", "Failure: Printing feedback report:");

        addCommandOutput("checkarea.heightmap.success", "Success: Heightmap levels normal.");
        addCommandOutput("checkarea.heightmap.failure", "Failure: Heightmap levels show signs of player intervention.");

        addCommandOutput("checkarea.blocktag.success", "Success: Terrain type is normal.");
        addCommandOutput("checkarea.blocktag.failure", "Failure: Terrain type contains abnormal blocks.");

        addCommandKey("screenshake", "Command Successful, enjoy your screenshake.");
    }

    public void addEnchantmentDescription(String enchantmentName, String description) {
        add("enchantment." + MOD_ID + "." + enchantmentName + ".desc", description);
    }

    public void addCommandKey(String command) {
        add("command." + MOD_ID + "." + command, "Command Successful!");
    }

    public void addCommandKey(String command, String feedback) {
        add("command." + MOD_ID + "." + command, feedback);
    }

    public static TranslatableComponent getCommandKey(String command) {
        return new TranslatableComponent("command." + MOD_ID + "." + command);
    }

    public void addCommandOutput(String output, String feedback) {
        add("command." + MOD_ID + "." + output, feedback);
    }

    public static TranslatableComponent getCommandOutput(String output) {
        return new TranslatableComponent("command." + MOD_ID + "." + output);
    }

    public String specialBlockNameChanges(String name) {
        if ((!name.endsWith("_bricks"))) {
            if (name.contains("bricks")) {
                name = name.replaceFirst("bricks", "brick");
            }
        }
        if (name.contains("_fence") || name.contains("_button") || name.contains("pressure_plate")) {
            if (name.contains("planks")) {
                name = name.replaceFirst("_planks", "");
            }
        }
        return name;
    }
}