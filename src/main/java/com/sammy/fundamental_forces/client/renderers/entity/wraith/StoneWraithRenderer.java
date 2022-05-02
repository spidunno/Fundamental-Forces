package com.sammy.fundamental_forces.client.renderers.entity.wraith;

import com.sammy.fundamental_forces.FufoMod;
import com.sammy.fundamental_forces.common.entity.wraith.StoneWraith;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class StoneWraithRenderer extends AnimatedEntityRenderer<StoneWraith> {
    private static final ResourceLocation WRAITH_LOCATION = FufoMod.prefix("stone_wraith");

    public StoneWraithRenderer(EntityRendererProvider.Context context) {
        super(context, FufoMod.prefix("stone_wraith"), 0.5F);
    }

    @Override
    public ResourceLocation getTextureTableLocation(StoneWraith entity) {
        return WRAITH_LOCATION;
    }
}