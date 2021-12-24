package com.project_esoterica.esoterica.client.renderers.entity.falling;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.common.entity.falling.FallingEntity;
import com.project_esoterica.esoterica.core.systems.rendering.RenderTypes;
import com.project_esoterica.esoterica.core.systems.rendering.StateShards;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;


import static com.project_esoterica.esoterica.core.helper.DataHelper.prefix;
import static com.project_esoterica.esoterica.core.systems.rendering.RenderManager.DELAYED_RENDER;
import static com.project_esoterica.esoterica.core.systems.rendering.RenderUtilities.*;

public class FallingStarRenderer extends EntityRenderer<FallingEntity> {

    private static final ResourceLocation STAR = prefix("textures/vfx/star.png");
    public static final RenderType STAR_TYPE = RenderTypes.createAdditiveQuadRenderType(STAR);

    private static final ResourceLocation LIGHT_TRAIL = prefix("textures/vfx/light_trail.png");
    public static final RenderType LIGHT_TYPE = RenderTypes.createBootlegTriangleRenderType(StateShards.ADDITIVE_TRANSPARENCY, LIGHT_TRAIL);


    public FallingStarRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(FallingEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        float beamLength = 6f;
        float beamWidth = 2f;
        float starSize = 3f;
        renderBeam(DELAYED_RENDER.getBuffer(LIGHT_TYPE), poseStack, entity.position(), entity.position().add(new Vec3(beamLength, beamLength, 0)),beamWidth);
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
        renderQuad(DELAYED_RENDER.getBuffer(STAR_TYPE), poseStack, starSize, starSize);
        poseStack.popPose();
    }
    @Override
    public ResourceLocation getTextureLocation(FallingEntity p_114482_) {
        return LIGHT_TRAIL;
    }
}
