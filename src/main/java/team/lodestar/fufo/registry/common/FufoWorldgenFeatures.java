package team.lodestar.fufo.registry.common;

import team.lodestar.fufo.common.world.gen.MeteoriteFeature;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.fufo.FufoMod;

import static net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration.INSTANCE;

@Mod.EventBusSubscriber(modid= FufoMod.FUFO, bus= Mod.EventBusSubscriber.Bus.MOD)
public class FufoWorldgenFeatures {

    public static final DeferredRegister<Feature<?>> FEATURE_TYPES = DeferredRegister.create(ForgeRegistries.FEATURES, FufoMod.FUFO);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> METEORITE = FEATURE_TYPES.register("meteorite", MeteoriteFeature::new);
}