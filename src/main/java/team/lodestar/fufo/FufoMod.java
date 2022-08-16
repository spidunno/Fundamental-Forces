package team.lodestar.fufo;

import team.lodestar.fufo.config.ClientConfig;
import team.lodestar.fufo.config.CommonConfig;
import team.lodestar.fufo.core.data.*;
import team.lodestar.fufo.core.registratation.BlockEntityRegistrate;
import team.lodestar.fufo.core.registratation.BlockRegistrate;
import team.lodestar.fufo.core.registratation.ItemRegistrate;
import team.lodestar.fufo.core.setup.client.FufoParticleRegistry;
import team.lodestar.fufo.core.setup.content.SoundRegistry;
import team.lodestar.fufo.core.setup.client.FufoTextureGrabber;
import team.lodestar.fufo.core.setup.content.RecipeTypeRegistry;
import team.lodestar.fufo.core.setup.content.entity.EntityRegistry;
import team.lodestar.fufo.core.setup.content.item.EnchantmentRegistry;
import team.lodestar.fufo.core.setup.content.item.ItemRegistry;
import team.lodestar.fufo.core.setup.content.potion.PotionEffectRegistry;
import team.lodestar.fufo.core.setup.content.worldgen.FeatureRegistry;
import team.lodestar.fufo.core.setup.server.CommandRegistry;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static team.lodestar.fufo.FufoMod.FUFO;

@Mod(FUFO)
public class FufoMod {
    public static final String FUFO = "fufo";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Random RANDOM = new Random();
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(FUFO));

    public static Registrate registrate(){
        return REGISTRATE.get();
    }


    public FufoMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);

        BlockRegistrate.register();
        ItemRegistrate.register();
        BlockEntityRegistrate.register();
        CommandRegistry.registerArgumentTypes();
        EnchantmentRegistry.ENCHANTMENTS.register(modBus);
        ItemRegistry.ITEMS.register(modBus);
        EntityRegistry.ENTITY_TYPES.register(modBus);
        PotionEffectRegistry.EFFECTS.register(modBus);
        SoundRegistry.SOUNDS.register(modBus);
        FeatureRegistry.FEATURE_TYPES.register(modBus);
        RecipeTypeRegistry.RECIPE_TYPES.register(modBus);
        FufoParticleRegistry.PARTICLES.register(modBus);
        //modBus.addListener(this::gatherData);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            FufoTextureGrabber.setup();
        }
    }
    public static ResourceLocation fufoPath(String path) {
        return new ResourceLocation(FUFO, path);
    }

    /*public void gatherData(GatherDataEvent event) {
        BlockTagsProvider provider = new SpaceModBlockTags(event.getGenerator(), event.getExistingFileHelper());
        event.getGenerator().addProvider(new SpaceModBlockStates(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new SpaceModItemModels(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new SpaceModLang(event.getGenerator()));
        event.getGenerator().addProvider(provider);
        event.getGenerator().addProvider(new SpaceModBlockLootTables(event.getGenerator()));
        event.getGenerator().addProvider(new SpaceModItemTags(event.getGenerator(), provider, event.getExistingFileHelper()));
        event.getGenerator().addProvider(new SpaceModRecipes(event.getGenerator()));
    }*/
}
