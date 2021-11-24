package com.project_esoterica.esoterica.client.renderers.falling;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.common.entity.falling.FallingEntity;
import com.project_esoterica.esoterica.core.systems.rendering.RenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;


import static com.project_esoterica.esoterica.core.systems.rendering.RenderManager.DELAYED_RENDER;
import static com.project_esoterica.esoterica.core.systems.rendering.RenderUtilities.*;

public class FallingStarRenderer extends EntityRenderer<FallingEntity> {

    private static final ResourceLocation TEXTURE = EsotericaHelper.prefix("textures/vfx/uv_test.png");
    public static final RenderType RENDER_TYPE = RenderTypes.createGlowingTextureRenderType(TEXTURE);

    public FallingStarRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(FallingEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        VertexConsumer vertexConsumer = DELAYED_RENDER.getBuffer(RENDER_TYPE);
        Vec3 playerPos = entity.position();
        poseStack.translate(-playerPos.x, -playerPos.y, -playerPos.z); // move to position
        renderBeam(vertexConsumer, poseStack, entity.position(), entity.position().add(2, 0,0), 2);
        poseStack.popPose();
    }
    @Override
    public ResourceLocation getTextureLocation(FallingEntity p_114482_) {
        return TEXTURE;
    }
}
