package com.project_esoterica.esoterica.client.renderers.entity.falling;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.project_esoterica.esoterica.common.entity.falling.FallingEntity;
import com.project_esoterica.esoterica.core.systems.rendering.Shaders;
import com.project_esoterica.esoterica.core.systems.rendering.RenderTypes;
import com.project_esoterica.esoterica.core.systems.rendering.RenderUtilities;
import com.project_esoterica.esoterica.core.systems.rendering.StateShards;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;


import static com.project_esoterica.esoterica.core.helper.DataHelper.prefix;
import static com.project_esoterica.esoterica.core.systems.rendering.RenderManager.DELAYED_RENDER;
import static com.project_esoterica.esoterica.core.systems.rendering.RenderTypes.createMovingBootlegTriangleRenderType;
import static com.project_esoterica.esoterica.core.systems.rendering.RenderTypes.withShaderHandler;
import static com.project_esoterica.esoterica.core.systems.rendering.RenderUtilities.*;

public class FallingStarRenderer extends EntityRenderer<FallingEntity> {

    private static final ResourceLocation STAR = prefix("textures/vfx/energy_trail.png");
    public static final RenderType STAR_TYPE = withShaderHandler(RenderTypes.createColorGradientQuadRenderType(StateShards.ADDITIVE_TRANSPARENCY, STAR), ()->{
        ShaderInstance instance = Shaders.colorGradientTexture.getInstance().get();
        instance.safeGetUniform("DarkColor").set(152f/255f, 1.0f, 241f/255f,1f);
    });

    private static final ResourceLocation LIGHT_TRAIL = prefix("textures/vfx/energy_trail.png");
    public static final RenderType LIGHT_TYPE = withShaderHandler(createMovingBootlegTriangleRenderType(StateShards.ADDITIVE_TRANSPARENCY, LIGHT_TRAIL), ()->{
        ShaderInstance instance = Shaders.movingBootlegTriangle.getInstance().get();
        instance.safeGetUniform("Speed").set(100f);
    });

    private static final ResourceLocation DARKNESS_TRAIL = prefix("textures/vfx/shadow_trail.png");
    public static final RenderType DARKNESS_TYPE = withShaderHandler(createMovingBootlegTriangleRenderType(StateShards.ADDITIVE_TRANSPARENCY, DARKNESS_TRAIL), ()->{
        ShaderInstance instance = Shaders.movingBootlegTriangle.getInstance().get();
        instance.safeGetUniform("Speed").set(200f);
    });

    private static final ResourceLocation FIRE_TRAIL = prefix("textures/vfx/fire_trail.png");
    public static final RenderType FIRE_TYPE = withShaderHandler(createMovingBootlegTriangleRenderType(StateShards.ADDITIVE_TRANSPARENCY, FIRE_TRAIL), ()->{
        ShaderInstance instance = Shaders.movingBootlegTriangle.getInstance().get();
        instance.safeGetUniform("Speed").set(400f);
    });


    public FallingStarRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(FallingEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        float beamLength = 6f;
        float beamWidth = 5f;
        float starSize = 3f;
//        RenderUtilities.create().setUV(0, 0, 1, 0.5f).setOffset(0, 4, 0).renderQuad(DELAYED_RENDER.getBuffer(STAR_TYPE), poseStack, 4, 2);
//        RenderUtilities.create().setUV(0, 0.5f, 1, 1).renderQuad(DELAYED_RENDER.getBuffer(STAR_TYPE), poseStack, 4, 2);
//        VertexBuilder builder = RenderUtilities.create();
//        builder.setColor(255, 226, 139, 255).renderBeam(DELAYED_RENDER.getBuffer(LIGHT_TYPE), poseStack, entity.position(), entity.position().add(new Vec3(beamLength, beamLength, 0)),beamWidth);
//        builder.setColor(169, 83, 255, 255).renderBeam(DELAYED_RENDER.getBuffer(DARKNESS_TYPE), poseStack, entity.position(), entity.position().add(new Vec3(beamLength+1, beamLength+1, 0)),beamWidth+1);
//        builder.setColor(54, 153, 217, 255).renderBeam(DELAYED_RENDER.getBuffer(FIRE_TYPE), poseStack, entity.position(), entity.position().add(new Vec3(beamLength+2, beamLength+2, 0)),beamWidth+2);
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
        RenderUtilities.create().setColor(255, 124, 112).setUV(0, 0, 1, 1f).renderQuad(DELAYED_RENDER.getBuffer(STAR_TYPE), poseStack, 2, 2);
        poseStack.popPose();
    }
    @Override
    public ResourceLocation getTextureLocation(FallingEntity p_114482_) {
        return LIGHT_TRAIL;
    }
}
