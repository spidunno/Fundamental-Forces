package team.lodestar.fufo.client.rendering.worldevent;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import team.lodestar.fufo.common.worldevents.starfall.FallingStarfallEvent;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.worldevent.WorldEventRenderer;
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

import static team.lodestar.fufo.FufoMod.fufoPath;
import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;

public class FallingStarfallEventRenderer extends WorldEventRenderer<FallingStarfallEvent> {

    private static final ResourceLocation LIGHT_TRAIL = fufoPath("textures/vfx/heavy_light_trail.png");
    public static final RenderType LIGHT_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.apply(LIGHT_TRAIL);

    private static final ResourceLocation STAR = fufoPath("textures/vfx/star.png");
    public static final RenderType STAR_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.apply(STAR);

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

//        float length = instance.startingHeight - instance.atmosphericEntryHeight;
//        double progress = instance.position.y - instance.atmosphericEntryHeight;
//        float atmosphericEntry = (float) Math.max(0, (progress / length));
//        float min = Math.min(1f, atmosphericEntry);
        List<Vector4f> mappedPastPositions = positions.stream().map(p -> p.position).map(p -> new Vector4f((float) p.x, (float) p.y, (float) p.z, 1)).collect(Collectors.toList());
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
        float flareSize = 8f;
        poseStack.pushPose();

        for (int i = 0; i < 5; i++) {
            float lerp = (float) (i / 4.0);
            Color color = ColorHelper.multicolorLerp(Easing.SINE_IN, lerp, Color.WHITE, Color.MAGENTA, Color.MAGENTA, Color.RED);
            float size = 1f + i * 2f;
            float alpha = (1f - i * 0.1f);
            builder.setColor(color).renderTrail(DELAYED_RENDER.getBuffer(LIGHT_TYPE), poseStack, mappedPastPositions, f -> size, f -> builder.setColor(ColorHelper.colorLerp(Easing.SINE_OUT, 1-f, color, Color.RED)).setAlpha(Math.max(0, Easing.SINE_IN.ease(f, 0, alpha, 1))));
        }
        poseStack.translate(lerpedPosition.x, lerpedPosition.y, lerpedPosition.z);
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
        for (int i = 0; i < 4; i++) {
            float lerp = (float) (i / 3.0);
            Color color = ColorHelper.multicolorLerp(Easing.SINE_IN, lerp, Color.WHITE, Color.MAGENTA, Color.RED);
            float size = flareSize + i*5f;
            float alpha = (1f - i * 0.2f);
            builder.setColor(color).setAlpha(alpha).renderQuad(DELAYED_RENDER.getBuffer(STAR_TYPE), poseStack, size);
        }
        poseStack.popPose();
    }
}