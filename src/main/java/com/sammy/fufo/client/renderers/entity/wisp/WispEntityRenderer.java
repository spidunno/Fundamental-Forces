package com.sammy.fufo.client.renderers.entity.wisp;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sammy.fufo.common.entity.wisp.WispEntity;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static com.sammy.fufo.FufoMod.fufoPath;

public class WispEntityRenderer extends EntityRenderer<WispEntity> {

    private static final ResourceLocation WISP_TEXTURE = fufoPath("textures/particle/square.png");
    private static final RenderType WISP_TYPE = OrtusRenderTypeRegistry.TRANSPARENT_TEXTURE.apply(WISP_TEXTURE);
    private static final ResourceLocation SPARK_TRAIL_TEXTURE = fufoPath("textures/entity/wisp/spark_trail.png");
    private static final RenderType SPARK_TRAIL_TYPE = OrtusRenderTypeRegistry.TRANSPARENT_TEXTURE.apply(SPARK_TRAIL_TEXTURE);

    public WispEntityRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(WispEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(entity.color.brighter()).setAlpha(1 - entity.fadeOut / 10f);

        RenderSystem.enableBlend();
        poseStack.pushPose();
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
//        builder.renderQuad(DELAYED_RENDER.getBuffer(WISP_TYPE), poseStack, 0.45f);
        poseStack.popPose();

        RenderSystem.disableBlend();
    }

    @Override
    public ResourceLocation getTextureLocation(WispEntity p_114482_) {
        return WISP_TEXTURE;
    }
}