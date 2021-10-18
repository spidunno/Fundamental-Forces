package com.project_esoterica.esoterica.client.model.bibit;

import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.common.entity.BibitEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BibitModel extends AnimatedGeoModel<BibitEntity> {

    @Override
    public ResourceLocation getModelLocation(BibitEntity object) {
        return EsotericaHelper.prefix("geo/bibit.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BibitEntity object) {
        return EsotericaHelper.prefix("textures/entity/bibit/metal.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BibitEntity animatable) {
        return EsotericaHelper.prefix("animations/bibit.animation.json");
    }

    public static ResourceLocation getOverlayTextureLocation(BibitEntity bibit) {
        String identifier = bibit.visualState != BibitEntity.bibitStateEnum.IDLE ? bibit.visualState.stateIdentifier : bibit.state.stateIdentifier;
        return EsotericaHelper.prefix("textures/entity/bibit/bibit_" + identifier + ".png");
    }
}