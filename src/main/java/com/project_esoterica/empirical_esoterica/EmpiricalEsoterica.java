package com.project_esoterica.empirical_esoterica;

import com.project_esoterica.empirical_esoterica.core.config.ClientConfig;
import com.project_esoterica.empirical_esoterica.core.config.CommonConfig;
import com.project_esoterica.empirical_esoterica.core.data.*;
import com.project_esoterica.empirical_esoterica.core.registry.EntityRegistry;
import com.project_esoterica.empirical_esoterica.core.registry.PotionEffectRegistry;
import com.project_esoterica.empirical_esoterica.core.registry.SoundRegistry;
import com.project_esoterica.empirical_esoterica.core.registry.block.BlockEntityRegistry;
import com.project_esoterica.empirical_esoterica.core.registry.block.BlockRegistry;
import com.project_esoterica.empirical_esoterica.core.registry.item.EnchantmentRegistry;
import com.project_esoterica.empirical_esoterica.core.registry.item.ItemRegistry;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static com.project_esoterica.empirical_esoterica.EmpiricalEsoterica.MOD_ID;

@Mod(MOD_ID)
public class EmpiricalEsoterica {
    public static final String MOD_ID = "empirical_esoterica";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Random RANDOM = new Random();
    public EmpiricalEsoterica() {
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

    public void gatherData(GatherDataEvent event) {
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
