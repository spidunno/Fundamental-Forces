package com.sammy.fundamental_forces.core.setup.content.worldgen;

import com.sammy.fundamental_forces.common.worldgen.MeteoriteFeature;
import com.sammy.fundamental_forces.core.helper.DataHelper;
import com.sammy.fundamental_forces.core.systems.worldgen.SimpleFeature;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.sammy.fundamental_forces.FundamentalForcesMod.MODID;

@Mod.EventBusSubscriber(modid= MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class FeatureRegistry {

    public static final DeferredRegister<Feature<?>> FEATURE_TYPES = DeferredRegister.create(ForgeRegistries.FEATURES, MODID);

    //public static final RegistryObject<SimpleFeature> METEORITE = FEATURE_TYPES.register("meteorite", MeteoriteFeature::new);



    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }

//    static <C extends FeatureConfiguration, F extends Feature<C>> PlacedFeature registerPlacedFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
//        return PlacementUtils.register(registryName, registerConfiguredFeature(registryName, feature).placed(placementModifiers));
//    }
//
//    static <C extends FeatureConfiguration, F extends Feature<C>> ConfiguredFeature<C, F> registerConfiguredFeature(String registryName, ConfiguredFeature<C, F> feature) {
//        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, DataHelper.prefix(registryName), feature);
//    }
}