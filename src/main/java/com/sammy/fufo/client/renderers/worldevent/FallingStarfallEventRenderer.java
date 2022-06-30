package com.sammy.fufo.client.renderers.worldevent;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.sammy.fufo.common.worldevents.starfall.FallingStarfallEvent;
import com.sammy.ortus.helpers.ColorHelper;
import com.sammy.ortus.helpers.EntityHelper;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import com.sammy.ortus.systems.worldevent.WorldEventRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        float renderSize = 2;
        return Minecraft.getInstance().levelRenderer.cullingFrustum.isVisible(AABB.ofSize(instance.position, renderSize, renderSize, renderSize));
    }

    @Override
    public void render(FallingStarfallEvent instance, PoseStack poseStack, MultiBufferSource bufferSource, float partialTicks) {
        Vec3 starfallPosition = instance.position;
        Vec3 oldStarfallPosition = instance.positionOld;

        ArrayList<EntityHelper.PastPosition> positions = new ArrayList<>(instance.pastPositions);
        float xPos = (float) Mth.lerp(partialTicks, oldStarfallPosition.x, starfallPosition.x);
        float yPos = (float) Mth.lerp(partialTicks, oldStarfallPosition.y, starfallPosition.y);
        float zPos = (float) Mth.lerp(partialTicks, oldStarfallPosition.z, starfallPosition.z);
        Vec3 lerpedPosition = new Vec3(xPos, yPos, zPos);
        if (positions.size() > 1) {
            for (int i = 0; i < positions.size() - 2; i++) {
                EntityHelper.PastPosition position = positions.get(i);
                EntityHelper.PastPosition nextPosition = positions.get(i + 1);
                float x = (float) Mth.lerp(partialTicks, position.position.x, nextPosition.position.x);
                float y = (float) Mth.lerp(partialTicks, position.position.y, nextPosition.position.y);
                float z = (float) Mth.lerp(partialTicks, position.position.z, nextPosition.position.z);
                positions.set(i, new EntityHelper.PastPosition(new Vec3(x, y, z), position.time));
            }
        }
        if (positions.size() > 1) {
            positions.set(positions.size() - 1, new EntityHelper.PastPosition(lerpedPosition.add(instance.motion), 0));
        }

        float length = instance.startingHeight - instance.atmosphericEntryHeight;
        double progress = instance.position.y - instance.atmosphericEntryHeight;
        float atmosphericEntry = (float) Math.max(0, (progress / length));
        float min = 0.9f - Math.min(0.9f, atmosphericEntry * 0.9f);
        Color color = ColorHelper.colorLerp(Easing.SINE_IN, min, Color.WHITE, Color.YELLOW);
        List<Vector4f> mappedPastPositions = positions.stream().map(p -> p.position).map(p -> new Vector4f((float) p.x, (float) p.y, (float) p.z, 1)).collect(Collectors.toList());
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(color);
        float flareSize = 6f;
        float alphaMultiplier = 0.5f + atmosphericEntry * 0.5f;
        poseStack.pushPose();
        for (int i = 0; i < 10; i++) {
            float size = 3f + i*0.4f;
            float alpha = (0.5f - i * 0.05f) * alphaMultiplier;
            builder.renderTrail(DELAYED_RENDER.getBuffer(LIGHT_TYPE), poseStack, mappedPastPositions, f -> size, f -> builder.setAlpha(alpha * f));
        }
        poseStack.translate(lerpedPosition.x, lerpedPosition.y, lerpedPosition.z);
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
        for (int i = 0; i < 3; i++) {
            float size = flareSize + i*0.5f;
            float alpha = (0.8f - i * 0.2f) * alphaMultiplier;
            builder.setAlpha(alpha).renderQuad(DELAYED_RENDER.getBuffer(STAR_TYPE), poseStack, size);
        }
        poseStack.popPose();
    }
}