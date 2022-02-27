package com.project_esoterica.esoterica.client.renderers.entity.falling;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.project_esoterica.esoterica.common.entity.falling.FallingEntity;
import com.project_esoterica.esoterica.core.setup.client.ShaderRegistry;
import com.project_esoterica.esoterica.core.helper.RenderHelper;
import com.project_esoterica.esoterica.core.systems.rendering.RenderTypes;
import com.project_esoterica.esoterica.core.systems.rendering.StateShards;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.StandaloneModelConfiguration;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.client.model.renderable.MultipartTransforms;
import net.minecraftforge.client.model.renderable.SimpleRenderable;

import static com.project_esoterica.esoterica.core.helper.DataHelper.prefix;
import static com.project_esoterica.esoterica.core.handlers.RenderHandler.DELAYED_RENDER;
import static com.project_esoterica.esoterica.core.systems.rendering.RenderTypes.createMovingBootlegTriangleRenderType;
import static com.project_esoterica.esoterica.core.systems.rendering.RenderTypes.withShaderHandler;

public class FallingStarRenderer extends EntityRenderer<FallingEntity> {

    private static final ResourceLocation STAR = prefix("textures/vfx/noise/orb_noise_trinary.png");
    public static final RenderType STAR_TYPE = withShaderHandler(RenderTypes.createRadialScatterNoiseQuadRenderType(STAR), ()->{
        ShaderInstance instance = ShaderRegistry.radialScatterNoise.getInstance().get();
        instance.safeGetUniform("Speed").set(-1500f);
        instance.safeGetUniform("Intensity").set(25f);
        instance.safeGetUniform("Falloff").set(1.4f);
    });

    private static final ResourceLocation LIGHT_TRAIL = prefix("textures/vfx/energy_trail.png");
    public static final RenderType LIGHT_TYPE = withShaderHandler(createMovingBootlegTriangleRenderType(StateShards.ADDITIVE_TRANSPARENCY, LIGHT_TRAIL), ()->{
        ShaderInstance instance = ShaderRegistry.movingBootlegTriangle.getInstance().get();
        instance.safeGetUniform("Speed").set(100f);
    });

    private static final ResourceLocation DARKNESS_TRAIL = prefix("textures/vfx/shadow_trail.png");
    public static final RenderType DARKNESS_TYPE = withShaderHandler(createMovingBootlegTriangleRenderType(StateShards.ADDITIVE_TRANSPARENCY, DARKNESS_TRAIL), ()->{
        ShaderInstance instance = ShaderRegistry.movingBootlegTriangle.getInstance().get();
        instance.safeGetUniform("Speed").set(200f);
    });

    private static final ResourceLocation FIRE_TRAIL = prefix("textures/vfx/fire_trail.png");
    public static final RenderType FIRE_TYPE = withShaderHandler(createMovingBootlegTriangleRenderType(StateShards.ADDITIVE_TRANSPARENCY, FIRE_TRAIL), ()->{
        ShaderInstance instance = ShaderRegistry.movingBootlegTriangle.getInstance().get();
        instance.safeGetUniform("Speed").set(400f);
    });

    public static final RenderHelper.VertexBuilder builder = RenderHelper.create();
    public static final OBJModel METEOR_TEARDROP = OBJLoader.INSTANCE.loadModel(new OBJModel.ModelSettings(prefix("models/obj/meteor.obj"), false, false, true, true, null));
    public static final SimpleRenderable RENDER = METEOR_TEARDROP.bakeRenderable(StandaloneModelConfiguration.INSTANCE);
    public FallingStarRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(FallingEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        float beamLength = 6f;
        float beamWidth = 3f;
        RenderSystem.setShaderColor(255/255f, 152/255f, 55/255f, 1f);
        poseStack.pushPose();
        poseStack.scale(beamWidth+1, beamWidth+1,beamWidth+1);
        RENDER.render(poseStack, DELAYED_RENDER, (r)->STAR_TYPE, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, partialTicks, MultipartTransforms.EMPTY);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
        poseStack.scale(1.02f, 1.04f,1.02f);
        RENDER.render(poseStack, DELAYED_RENDER, (r)->STAR_TYPE, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, partialTicks, MultipartTransforms.EMPTY);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
        poseStack.scale(1.02f, 1.04f,1.02f);
        RENDER.render(poseStack, DELAYED_RENDER, (r)->STAR_TYPE, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, partialTicks, MultipartTransforms.EMPTY);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
        poseStack.scale(1.02f, 1.04f,1.02f);
        RENDER.render(poseStack, DELAYED_RENDER, (r)->STAR_TYPE, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, partialTicks, MultipartTransforms.EMPTY);
        poseStack.popPose();
//        RenderUtilities.create().setUV(0, 0, 1, 0.5f).setOffset(0, 4, 0).renderQuad(DELAYED_RENDER.getBuffer(STAR_TYPE), poseStack, 4, 2);
//        RenderUtilities.create().setUV(0, 0.5f, 1, 1).renderQuad(DELAYED_RENDER.getBuffer(STAR_TYPE), poseStack, 4, 2);
//        builder.setColor(255, 241, 167, 255).renderBeam(DELAYED_RENDER.getBuffer(LIGHT_TYPE), poseStack, entity.position(), entity.position().add(new Vec3(0, beamLength, 0)),beamWidth);
//        builder.setColor(255, 230, 93, 205).renderBeam(DELAYED_RENDER.getBuffer(DARKNESS_TYPE), poseStack, entity.position(), entity.position().add(new Vec3(0, beamLength+1, 0)),beamWidth+2);
//        builder.setColor(255, 180, 55, 155).renderBeam(DELAYED_RENDER.getBuffer(FIRE_TYPE), poseStack, entity.position(), entity.position().add(new Vec3(0, beamLength+2, 0)),beamWidth+4);
//        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
//        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
//        RenderUtilities.create().setColor(255, 124, 112).setUV(0, 0, 1, 1f).renderQuad(DELAYED_RENDER.getBuffer(STAR_TYPE), poseStack, 2, 2);
        poseStack.popPose();
    }
    @Override
    public ResourceLocation getTextureLocation(FallingEntity p_114482_) {
        return LIGHT_TRAIL;
    }
}
