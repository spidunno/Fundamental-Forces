package com.sammy.fundamental_forces.client.renderers.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.fundamental_forces.common.blockentity.OrbBlockEntity;
import com.sammy.fundamental_forces.core.setup.client.ShaderRegistry;
import com.sammy.fundamental_forces.core.helper.RenderHelper;
import com.sammy.fundamental_forces.core.systems.rendering.RenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

import static com.sammy.fundamental_forces.core.helper.DataHelper.prefix;
import static com.sammy.fundamental_forces.core.handlers.RenderHandler.DELAYED_RENDER;


public class OrbRenderer implements BlockEntityRenderer<OrbBlockEntity> {
    public OrbRenderer(BlockEntityRendererProvider.Context context) {
    }

    private static final RenderHelper.VertexBuilder BUILDER = RenderHelper.create();
    private static final ResourceLocation ORB_NOISE = prefix("textures/vfx/noise/orb_noise.png");
    public static final RenderType ORB_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialNoiseQuadRenderType(ORB_NOISE), () -> {
        ShaderInstance instance = ShaderRegistry.radialNoise.getInstance().get();
        instance.safeGetUniform("Speed").set(2500f);
        instance.safeGetUniform("Intensity").set(35f);
        instance.safeGetUniform("Falloff").set(2.5f);
    });
    private static final ResourceLocation SECONDARY_ORB_NOISE = prefix("textures/vfx/noise/orb_noise_secondary.png");
    public static final RenderType SECONDARY_ORB_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialNoiseQuadRenderType(SECONDARY_ORB_NOISE), () -> {

        ShaderInstance instance = ShaderRegistry.radialNoise.getInstance().get();
        instance.safeGetUniform("Speed").set(-1500f);
        instance.safeGetUniform("Intensity").set(45f);
        instance.safeGetUniform("Falloff").set(2.5f);
    });
    private static final ResourceLocation TRINARY_ORB_NOISE = prefix("textures/vfx/noise/orb_noise_trinary.png");
    public static final RenderType TRINARY_ORB_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialNoiseQuadRenderType(TRINARY_ORB_NOISE), () -> {

        ShaderInstance instance = ShaderRegistry.radialNoise.getInstance().get();
        instance.safeGetUniform("Speed").set(2000f);
        instance.safeGetUniform("Intensity").set(35f);
        instance.safeGetUniform("Falloff").set(2.5f);
    });

    @Override
    public void render(OrbBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        renderOrb(poseStack, Color.YELLOW, Color.CYAN, Color.YELLOW);
        poseStack.popPose();
    }

    public static void renderOrb(PoseStack poseStack, Color primaryColor, Color secondaryColor, Color trinaryColor) {
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));

        float scale = 0.4f;
        VertexConsumer primary = DELAYED_RENDER.getBuffer(ORB_NOISE_TYPE);
        BUILDER.setColor(primaryColor.getRed(), primaryColor.getGreen(), primaryColor.getBlue(), 200).renderQuad(primary, poseStack, scale, scale);
        scale = 0.5f;
        VertexConsumer secondary = DELAYED_RENDER.getBuffer(SECONDARY_ORB_NOISE_TYPE);
        BUILDER.setColor(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue(), 140).renderQuad(secondary, poseStack, scale, scale);
        scale = 0.6f;
        VertexConsumer trinary = DELAYED_RENDER.getBuffer(TRINARY_ORB_NOISE_TYPE);
        BUILDER.setColor(trinaryColor.getRed(), trinaryColor.getGreen(), trinaryColor.getBlue(), 80).renderQuad(trinary, poseStack, scale, scale);
    }
}