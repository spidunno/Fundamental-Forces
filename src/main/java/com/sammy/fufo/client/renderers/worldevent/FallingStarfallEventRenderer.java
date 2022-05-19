package com.sammy.fufo.client.renderers.worldevent;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.fufo.common.worldevents.starfall.FallingStarfallEvent;
import com.sammy.ortus.handlers.RenderHandler;
import com.sammy.ortus.helpers.RenderHelper;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import com.sammy.ortus.systems.worldevent.WorldEventRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import static com.sammy.fufo.FufoMod.fufoPath;
import static com.sammy.ortus.handlers.RenderHandler.DELAYED_RENDER;

public class FallingStarfallEventRenderer extends WorldEventRenderer<FallingStarfallEvent> {

    private static final ResourceLocation LIGHT_TRAIL = fufoPath("textures/vfx/light_trail.png");
    public static final RenderType LIGHT_TYPE = OrtusRenderTypeRegistry.TEXTURE_TRIANGLE.apply(LIGHT_TRAIL);

    private static final ResourceLocation STAR = fufoPath("textures/vfx/star.png");
    public static final RenderType STAR_TYPE = OrtusRenderTypeRegistry.ADDITIVE_TEXTURE.apply(STAR);

    public FallingStarfallEventRenderer() {
    }

    @Override
    public boolean canRender(FallingStarfallEvent instance) {
        float renderSize = 25;
        return RenderHandler.FRUSTUM.isVisible(new AABB(instance.position.subtract(renderSize, renderSize, renderSize), instance.position.add(renderSize, renderSize, renderSize)));
    }

    @Override
    public void render(FallingStarfallEvent instance, PoseStack poseStack, MultiBufferSource bufferSource, float partialTicks) {
        LocalPlayer player = Minecraft.getInstance().player;
        float beamLength = 20f;
        float beamWidth = 4f;
        float flareSize = 3f;
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld();
        VertexConsumer lightTrailConsumer = DELAYED_RENDER.getBuffer(LIGHT_TYPE);
        VertexConsumer starConsumer = DELAYED_RENDER.getBuffer(STAR_TYPE);
        Vec3 motion = instance.motion.add(instance.motion.multiply(instance.speed * partialTicks, instance.speed * partialTicks, instance.speed * partialTicks));
        Vec3 position = instance.position.add(motion);

        poseStack.pushPose();
        poseStack.translate(position.x - player.getX(), position.y - player.getY(), position.z - player.getZ());
        builder.renderBeam(lightTrailConsumer, poseStack, position, position.subtract(instance.motion.multiply(beamLength, beamLength, beamLength)), beamWidth);
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
        builder.renderQuad(starConsumer, poseStack, flareSize);
        poseStack.popPose();
    }
}