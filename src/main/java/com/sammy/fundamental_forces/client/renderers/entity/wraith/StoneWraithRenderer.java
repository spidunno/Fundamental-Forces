package com.sammy.fundamental_forces.client.renderers.entity.wraith;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sammy.fundamental_forces.common.entity.wraith.StoneWraith;
import com.sammy.fundamental_forces.core.helper.DataHelper;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/**
 * @author Ocelot
 */
public class StoneWraithRenderer extends AnimatedEntityRenderer<StoneWraith> {
    private static final ResourceLocation[] DEFAULT_ANIMATIONS = new ResourceLocation[]{DataHelper.prefix("wraith.idle")};
    private static final ResourceLocation WRAITH_LOCATION = DataHelper.prefix("stone_wraith");

    public StoneWraithRenderer(EntityRendererProvider.Context context) {
        super(context, DataHelper.prefix("stone_wraith"), 0.5F);
    }

    @Override
    protected void setupRotations(StoneWraith entity, PoseStack matrixStack, float ticksExisted, float rotY, float partialTicks) {
        super.setupRotations(entity, matrixStack, ticksExisted, rotY, partialTicks);
//        if (!((double) entity.animationSpeed < 0.01)) {
//            float j = entity.animationPosition - entity.animationSpeed * (1.0F - partialTicks) + 6.0F;
//            float k = (Math.abs(j % 13.0F - 6.5F) - 3.25F) / 3.25F;
//            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(6.5F * k));
//        }
    }

    @Override
    public ResourceLocation[] getAnimations(StoneWraith entity) {
        if (entity.isNoAnimationPlaying())
            return DEFAULT_ANIMATIONS;
        return super.getAnimations(entity);
    }

    @Override
    public ResourceLocation getTextureTableLocation(StoneWraith yeti) {
        return WRAITH_LOCATION;
    }
}