package com.sammy.fundamental_forces;

import com.sammy.fundamental_forces.config.ClientConfig;
import com.sammy.fundamental_forces.config.CommonConfig;
import com.sammy.fundamental_forces.core.data.*;
import com.sammy.fundamental_forces.core.setup.content.SoundRegistry;
import com.sammy.fundamental_forces.core.setup.content.block.BlockEntityRegistry;
import com.sammy.fundamental_forces.core.setup.content.block.BlockRegistry;
import com.sammy.fundamental_forces.core.setup.client.ParticleRegistry;
import com.sammy.fundamental_forces.core.setup.client.TextureGrabber;
import com.sammy.fundamental_forces.core.setup.content.RecipeTypeRegistry;
import com.sammy.fundamental_forces.core.setup.content.entity.EntityRegistry;
import com.sammy.fundamental_forces.core.setup.content.item.EnchantmentRegistry;
import com.sammy.fundamental_forces.core.setup.content.item.ItemRegistry;
import com.sammy.fundamental_forces.core.setup.content.potion.PotionEffectRegistry;
import com.sammy.fundamental_forces.core.setup.content.worldgen.FeatureRegistry;
import com.sammy.fundamental_forces.core.setup.server.CommandRegistry;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static com.sammy.fundamental_forces.FundamentalForcesMod.MODID;

@Mod(MODID)
public class FundamentalForcesMod {
    public static final String MODID = "fundamental_forces";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Random RANDOM = new Random();

    public FundamentalForcesMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        CommandRegistry.registerArgumentTypes();
        EnchantmentRegistry.ENCHANTMENTS.register(modBus);
        BlockRegistry.BLOCKS.register(modBus);
        ItemRegistry.ITEMS.register(modBus);
        BlockEntityRegistry.BLOCK_ENTITY_TYPES.register(modBus);
        EntityRegistry.ENTITY_TYPES.register(modBus);
        PotionEffectRegistry.EFFECTS.register(modBus);
        SoundRegistry.SOUNDS.register(modBus);
        ParticleRegistry.PARTICLES.register(modBus);
        FeatureRegistry.FEATURE_TYPES.register(modBus);
        RecipeTypeRegistry.RECIPE_TYPES.register(modBus);
        modBus.addListener(this::gatherData);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            TextureGrabber.setup();
        }
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
