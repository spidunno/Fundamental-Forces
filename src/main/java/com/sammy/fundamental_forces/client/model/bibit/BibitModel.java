package com.sammy.fundamental_forces.client.model.bibit;

import com.sammy.fundamental_forces.common.entity.robot.BibitEntity;
import com.sammy.fundamental_forces.core.helper.DataHelper;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.sammy.fundamental_forces.core.helper.DataHelper.prefix;

public class BibitModel extends AnimatedGeoModel<BibitEntity> {

    @Override
    public ResourceLocation getModelLocation(BibitEntity object) {
        return prefix("geo/bibit.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BibitEntity object) {
        return prefix("textures/entity/bibit/metal.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BibitEntity animatable) {
        return prefix("animations/bibit.animation.json");
    }

    public static ResourceLocation getOverlayTextureLocation(BibitEntity bibit) {
        String identifier = bibit.visualState != BibitEntity.BibitState.IDLE ? bibit.visualState.stateIdentifier : bibit.state.stateIdentifier;
        return DataHelper.prefix("textures/entity/bibit/bibit_" + identifier + ".png");
    }
}