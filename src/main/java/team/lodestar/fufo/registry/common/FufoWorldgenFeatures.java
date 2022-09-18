package team.lodestar.fufo.registry.common;

import team.lodestar.fufo.common.starfall.features.MeteoriteFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.fufo.FufoMod;

@Mod.EventBusSubscriber(modid= FufoMod.FUFO, bus= Mod.EventBusSubscriber.Bus.MOD)
public class FufoWorldgenFeatures {

    public static final DeferredRegister<Feature<?>> FEATURE_TYPES = DeferredRegister.create(ForgeRegistries.FEATURES, FufoMod.FUFO);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> METEORITE = FEATURE_TYPES.register("meteorite", MeteoriteFeature::new);
}