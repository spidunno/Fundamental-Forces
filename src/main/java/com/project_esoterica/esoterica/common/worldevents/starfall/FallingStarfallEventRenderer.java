package com.project_esoterica.esoterica.common.worldevents.starfall;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.core.systems.rendering.RenderManager;
import com.project_esoterica.esoterica.core.systems.rendering.RenderTypes;
import com.project_esoterica.esoterica.core.systems.rendering.RenderUtilities;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

import static com.project_esoterica.esoterica.core.systems.rendering.RenderManager.DELAYED_RENDER;

public class FallingStarfallEventRenderer extends WorldEventRenderer<FallingStarfallEvent> {

    private static final ResourceLocation STAR_LOCATION = new ResourceLocation(EsotericaMod.MOD_ID, "textures/star.png");
    public static final RenderType RENDER_TYPE = RenderTypes.createGlowingTextureRenderType(STAR_LOCATION);

    public FallingStarfallEventRenderer() {
    }

    @Override
    public boolean canRender(FallingStarfallEvent instance) {
        float renderSize = 25;
        return RenderManager.FRUSTUM.isVisible(new AABB(instance.position.subtract(renderSize,renderSize,renderSize), instance.position.add(renderSize,renderSize,renderSize)));
    }

    @Override
    public void render(FallingStarfallEvent instance, PoseStack poseStack, MultiBufferSource bufferSource, float partialTicks) {
        poseStack.pushPose();
        LocalPlayer player = Minecraft.getInstance().player;
        float minScale = 9;
        float time = (player.tickCount + partialTicks) / 4f;
        float distanceMultiplier = Math.max(1, 20 - (float) Math.max(0, instance.position.distanceTo(player.position()) / 20f));
        double flicker = (3 % Math.sin(time) - Math.cos(-time)) / 3f;
        float maxScale = (float) (Math.max(minScale, minScale + distanceMultiplier) + flicker * (distanceMultiplier / 2f));
        poseStack.translate(instance.position.x - player.getX(), instance.position.y - player.getY(), instance.position.z - player.getZ()); // move to position
        poseStack.scale(maxScale, maxScale, maxScale);
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
        MultiBufferSource delayedBuffer = DELAYED_RENDER;
        VertexConsumer vertexConsumer = delayedBuffer.getBuffer(RENDER_TYPE);
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();

        RenderUtilities.vertex(vertexConsumer, matrix, -0.5f, -0.5f, 0, 255, 255, 255, 255, 0, 1, 15728880);
        RenderUtilities.vertex(vertexConsumer, matrix, 0.5f, -0.5f, 0, 255, 255, 255, 255, 1, 1, 15728880);
        RenderUtilities.vertex(vertexConsumer, matrix, 0.5f, 0.5f, 0, 255, 255, 255, 255, 1, 0, 15728880);
        RenderUtilities.vertex(vertexConsumer, matrix, -0.5f, 0.5f, 0, 255, 255, 255, 255, 0, 0, 15728880);

        poseStack.popPose();
    }
}
