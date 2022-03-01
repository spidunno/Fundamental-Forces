package com.sammy.fundamental_forces.core.systems.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SimpleFeature extends Feature<NoneFeatureConfiguration> {
    public SimpleFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        return false;
    }
}
