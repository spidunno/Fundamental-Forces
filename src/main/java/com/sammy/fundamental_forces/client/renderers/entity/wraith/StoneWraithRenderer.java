package com.sammy.fundamental_forces.client.renderers.entity.wraith;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sammy.fundamental_forces.common.entity.wraith.StoneWraith;
import com.sammy.fundamental_forces.core.helper.DataHelper;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class StoneWraithRenderer extends AnimatedEntityRenderer<StoneWraith> {
    private static final ResourceLocation[] DEFAULT_ANIMATIONS = new ResourceLocation[]{DataHelper.prefix("stone_wraith.twitchlayer"), DataHelper.prefix("stone_wraith.idle")};
    private static final ResourceLocation WRAITH_LOCATION = DataHelper.prefix("stone_wraith");

    public StoneWraithRenderer(EntityRendererProvider.Context context) {
        super(context, DataHelper.prefix("stone_wraith"), 0.5F);
    }

    @Override
    public ResourceLocation[] getAnimations(StoneWraith entity) {
        if (entity.isNoAnimationPlaying())
            return DEFAULT_ANIMATIONS;
        return super.getAnimations(entity);
    }

    @Override
    public ResourceLocation getTextureTableLocation(StoneWraith entity) {
        return WRAITH_LOCATION;
    }
}