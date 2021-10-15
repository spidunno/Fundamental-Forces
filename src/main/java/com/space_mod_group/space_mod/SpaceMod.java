package com.space_mod_group.space_mod;

import com.space_mod_group.space_mod.core.config.ClientConfig;
import com.space_mod_group.space_mod.core.config.CommonConfig;
import com.space_mod_group.space_mod.core.data.*;
import com.space_mod_group.space_mod.core.registry.*;
import com.space_mod_group.space_mod.core.registry.block.BlockRegistry;
import com.space_mod_group.space_mod.core.registry.block.BlockEntityRegistry;
import com.space_mod_group.space_mod.core.registry.item.EnchantmentRegistry;
import com.space_mod_group.space_mod.core.registry.item.ItemRegistry;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.space_mod_group.space_mod.SpaceMod.MOD_ID;

@Mod(MOD_ID)
public class SpaceMod
{
    public static final String MOD_ID = "space_mod";
    public static final Logger LOGGER = LogManager.getLogger();

    public SpaceMod()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        EnchantmentRegistry.ENCHANTMENTS.register(modBus);
        BlockRegistry.BLOCKS.register(modBus);
        ItemRegistry.ITEMS.register(modBus);
        BlockEntityRegistry.BLOCK_ENTITY_TYPES.register(modBus);
        EntityRegistry.ENTITY_TYPES.register(modBus);
        PotionEffectRegistry.EFFECTS.register(modBus);
        SoundRegistry.SOUNDS.register(modBus);
        modBus.addListener(this::gatherData);
    }
    public void gatherData(GatherDataEvent event)
    {
        BlockTagsProvider provider = new SpaceModBlockTags(event.getGenerator(), event.getExistingFileHelper());
        event.getGenerator().addProvider(new SpaceModBlockStates(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new SpaceModItemModels(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new SpaceModLang(event.getGenerator()));
        event.getGenerator().addProvider(provider);
        event.getGenerator().addProvider(new SpaceModBlockLootTables(event.getGenerator()));
        event.getGenerator().addProvider(new SpaceModItemTags(event.getGenerator(), provider, event.getExistingFileHelper()));
        event.getGenerator().addProvider(new SpaceModRecipes(event.getGenerator()));
    }
}
