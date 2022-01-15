package com.project_esoterica.esoterica.client.renderers.worldevent;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.project_esoterica.esoterica.common.worldevents.starfall.FallingStarfallEvent;
import com.project_esoterica.esoterica.core.systems.rendering.RenderManager;
import com.project_esoterica.esoterica.core.systems.rendering.RenderTypes;
import com.project_esoterica.esoterica.core.systems.rendering.RenderUtilities;
import com.project_esoterica.esoterica.core.systems.rendering.StateShards;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import static com.project_esoterica.esoterica.core.helper.DataHelper.prefix;
import static com.project_esoterica.esoterica.core.systems.rendering.RenderManager.DELAYED_RENDER;

public class FallingStarfallEventRenderer extends WorldEventRenderer<FallingStarfallEvent> {

    private static final ResourceLocation LIGHT_TRAIL = prefix("textures/vfx/light_trail.png");
    public static final RenderType LIGHT_TYPE = RenderTypes.createBootlegTriangleRenderType(StateShards.ADDITIVE_TRANSPARENCY, LIGHT_TRAIL);

    private static final ResourceLocation STAR = prefix("textures/vfx/star.png");
    public static final RenderType STAR_TYPE = RenderTypes.createQuadRenderType(StateShards.ADDITIVE_TRANSPARENCY, STAR);

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
        float beamLength = 20f;
        float beamWidth = 4f;
        VertexConsumer lightTrailConsumer = DELAYED_RENDER.getBuffer(LIGHT_TYPE);
        float starSize = 5f;
        RenderUtilities.VertexBuilder builder = RenderUtilities.create();
        Vec3 motion = instance.motion.add(instance.motion.multiply(instance.speed*partialTicks,instance.speed*partialTicks,instance.speed*partialTicks));
        Vec3 position = instance.position.add(motion);
        poseStack.translate(position.x - player.getX(), position.y - player.getY(), position.z - player.getZ());
        builder.renderBeam(lightTrailConsumer, poseStack, position, position.subtract(instance.motion.multiply(beamLength, beamLength, beamLength)), beamWidth);
        VertexConsumer starConsumer = DELAYED_RENDER.getBuffer(STAR_TYPE);
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
        builder.renderQuad(starConsumer, poseStack, starSize, starSize);
        poseStack.popPose();
    }
}
