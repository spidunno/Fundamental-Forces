package team.lodestar.fufo;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.RenderType;
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
import team.lodestar.fufo.config.CommonConfig;
import team.lodestar.fufo.registry.client.FufoParticles;
import team.lodestar.fufo.registry.client.FufoTextureLoaders;
import team.lodestar.fufo.registry.common.*;

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
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);

        
        FufoBlocks.register();
        FufoItems.register();
        FufoBlockEntities.register();
        FufoEntities.ENTITY_TYPES.register(modBus);
        FufoMobEffects.EFFECTS.register(modBus);
        FufoSounds.SOUNDS.register(modBus);
        FufoWorldgenFeatures.FEATURE_TYPES.register(modBus);
        FufoRecipeTypes.RECIPE_TYPES.register(modBus);
        FufoParticles.PARTICLES.register(modBus);
        FufoCommandArguments.ARGUMENT_TYPES.register(modBus);
    }
    public static ResourceLocation fufoPath(String path) {
        return new ResourceLocation(FUFO, path);
    }
}
