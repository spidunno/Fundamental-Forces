package com.project_esoterica.esoterica.client.model.bibit;

import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.common.entity.Bibit;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BibitModel extends AnimatedGeoModel<Bibit> {

    @Override
    public ResourceLocation getModelLocation(Bibit object) {
        return EsotericaHelper.prefix("geo/bibit.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Bibit object) {
        return EsotericaHelper.prefix("textures/entity/bibit/metal.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Bibit animatable) {
        return EsotericaHelper.prefix("animations/bibit.animation.json");
    }

    public static ResourceLocation getOverlayTextureLocation(Bibit bibit) {
        return EsotericaHelper.prefix("textures/entity/bibit/bibit_non_binary.png");
    }
}