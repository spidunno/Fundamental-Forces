package team.lodestar.fufo.client.rendering.entity.wisp;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import team.lodestar.fufo.common.entity.wisp.WispEntity;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.fufo.FufoMod;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static team.lodestar.fufo.client.rendering.worldevent.FallingStarfallEventRenderer.*;
import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;

public class WispEntityRenderer extends EntityRenderer<WispEntity> {

    private static final ResourceLocation WISP = FufoMod.fufoPath("textures/entity/wisp/wisp_glimmer.png");
    private static final RenderType WISP_TYPE = LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.apply(WISP);

    private static final ResourceLocation WISP_TRAIL = FufoMod.fufoPath("textures/entity/wisp/wisp_trail.png");
    private static final RenderType WISP_TRAIL_TYPE = LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE_TRIANGLE.apply(WISP_TRAIL);

    public WispEntityRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(WispEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        java.util.List<EntityHelper.PastPosition> positions = new ArrayList<>(entity.pastPositions);
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
        float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
        if (positions.size() > 1) {
            positions.set(positions.size() - 1, new EntityHelper.PastPosition(new Vec3(x, y, z).add(entity.getDeltaMovement().multiply(partialTicks, partialTicks, partialTicks)), 0));
        }
        List<Vector4f> mappedPastPositions = positions.stream().map(p -> p.position).map(p -> new Vector4f((float) p.x, (float) p.y, (float) p.z, 1)).collect(Collectors.toList());

        Color color = new Color(219, 88, 239);
        VFXBuilders.WorldVFXBuilder trailBuilder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(color).setOffset(-x, -y, -z);
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(color);
        poseStack.pushPose();
        RenderSystem.enableBlend();
        trailBuilder.renderTrail(DELAYED_RENDER.getBuffer(STAR_TRAIL_TYPE), poseStack, mappedPastPositions, f -> 0.25f, f -> trailBuilder.setAlpha(Math.max(0, Easing.SINE_IN.ease(f, 0, 0.5f, 1))));
        trailBuilder.renderTrail(DELAYED_RENDER.getBuffer(STAR_TRAIL_TYPE), poseStack, mappedPastPositions, f -> 0.1f, f -> trailBuilder.setAlpha(Math.max(0, Easing.SINE_IN.ease(f, 0, 0.75f, 1))));

        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));

        builder.setAlpha(0.5f).renderQuad(DELAYED_RENDER.getBuffer(STAR_TYPE), poseStack, 0.8f);
        builder.setAlpha(0.75f).renderQuad(DELAYED_RENDER.getBuffer(STAR_TYPE), poseStack, 0.4f);
        builder.setColor(color.brighter()).setAlpha(1).renderQuad(DELAYED_RENDER.getBuffer(STAR_TYPE), poseStack, 0.25f);

        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(WispEntity p_114482_) {
        return WISP;
    }
}