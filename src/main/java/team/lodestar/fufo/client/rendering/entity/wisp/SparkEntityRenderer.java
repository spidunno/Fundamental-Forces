package team.lodestar.fufo.client.rendering.entity.wisp;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector4f;
import team.lodestar.fufo.common.entity.wisp.SparkEntity;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static team.lodestar.fufo.FufoMod.fufoPath;
import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;

public class SparkEntityRenderer extends EntityRenderer<SparkEntity> {

    private static final ResourceLocation WISP_TEXTURE = fufoPath("textures/particle/square.png");
    private static final RenderType WISP_TYPE = LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.apply(WISP_TEXTURE);
    private static final ResourceLocation SPARK_TRAIL_TEXTURE = fufoPath("textures/entity/wisp/spark_trail.png");
    private static final RenderType SPARK_TRAIL_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.apply(SPARK_TRAIL_TEXTURE);

    public SparkEntityRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(SparkEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        ArrayList<EntityHelper.PastPosition> positions = new ArrayList<>(entity.pastPositions);
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

        List<Vector4f> mappedPastPositions = positions.stream().map(p -> p.position).map(p -> new Vector4f((float) p.x, (float) p.y, (float) p.z, 1)).collect(Collectors.toList());

        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(entity.color.brighter());

//        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
//        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
//        builder.renderQuad(DELAYED_RENDER.getBuffer(WISP_TYPE), poseStack, 0.3f);

        poseStack.pushPose();
        RenderSystem.enableBlend();
        builder.setOffset(-x, -y, -z)
                .setUV(0, 0, 1, 1)
                .renderTrail(DELAYED_RENDER.getBuffer(SPARK_TRAIL_TYPE), poseStack, mappedPastPositions, f -> 0.6f);
        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(SparkEntity p_114482_) {
        return WISP_TEXTURE;
    }
}