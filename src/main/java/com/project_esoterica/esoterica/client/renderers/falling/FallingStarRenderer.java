package com.project_esoterica.esoterica.client.renderers.falling;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.project_esoterica.esoterica.common.entity.falling.FallingEntity;
import com.project_esoterica.esoterica.core.systems.rendering.RenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;


import static com.project_esoterica.esoterica.core.systems.rendering.RenderManager.DELAYED_RENDER;
import static com.project_esoterica.esoterica.core.systems.rendering.RenderUtilities.renderTriangle;

public class FallingStarRenderer extends EntityRenderer<FallingEntity> {

    private static final ResourceLocation STAR_LOCATION = new ResourceLocation("textures/block/stone.png");
    public static final RenderType RENDER_TYPE = RenderTypes.createGlowingTextureTrianglesRenderType(STAR_LOCATION);

    public FallingStarRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(FallingEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        Minecraft minecraft = Minecraft.getInstance();
        float cameraX = minecraft.gameRenderer.getMainCamera().getXRot();
        float cameraY = minecraft.gameRenderer.getMainCamera().getYRot()%360;

        VertexConsumer vertexConsumer = DELAYED_RENDER.getBuffer(RENDER_TYPE);

        //TODO: redo this shit
        poseStack.mulPose(entity.getDirection().getOpposite().getRotation());
        float rotation = (cameraY-180) < 0 ? -cameraX : cameraX;
        poseStack.mulPose(new Quaternion(0, rotation, 0, true));


        poseStack.mulPose(Vector3f.YN.rotationDegrees(-90f));
        renderTriangle(vertexConsumer, poseStack, 1, 10);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
        renderTriangle(vertexConsumer, poseStack, 1, 10);

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(FallingEntity p_114482_) {
        return STAR_LOCATION;
    }
}
