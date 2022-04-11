package com.sammy.fundamental_forces.client.renderers.entity.falling;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.fundamental_forces.common.entity.falling.FallingEntity;
import com.sammy.fundamental_forces.core.helper.DataHelper;
import com.sammy.fundamental_forces.core.helper.RenderHelper;
import com.sammy.fundamental_forces.core.helper.RenderHelper.VertexBuilder;
import com.sammy.fundamental_forces.core.systems.rendering.RenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.StandaloneModelConfiguration;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.client.model.renderable.SimpleRenderable;

import java.awt.*;
import java.util.ArrayList;

import static com.sammy.fundamental_forces.core.helper.DataHelper.prefix;
import static com.sammy.fundamental_forces.core.handlers.RenderHandler.DELAYED_RENDER;
import static com.sammy.fundamental_forces.core.helper.RenderHelper.FULL_BRIGHT;
import static com.sammy.fundamental_forces.core.systems.rendering.RenderTypes.queueUniformChanges;
import static com.sammy.fundamental_forces.core.systems.rendering.RenderTypes.copy;

public class FallingStarRenderer extends EntityRenderer<FallingEntity> {

    private static final ResourceLocation COMA = prefix("textures/vfx/uv_test.png");
    private static final RenderType COMA_TYPE = RenderTypes.ADDITIVE_TEXTURE.apply(COMA);

    private static final ResourceLocation LIGHT_TRAIL = prefix("textures/vfx/light_trail.png");
    private static final RenderType LIGHT_TYPE = RenderTypes.ADDITIVE_TEXTURE.apply(LIGHT_TRAIL);

    private static final ResourceLocation FIRE_TRAIL = prefix("textures/vfx/fire_trail.png");
    private static final RenderType FIRE_TYPE = RenderTypes.ADDITIVE_TEXTURE.apply(FIRE_TRAIL);

    public static final OBJModel METEOR_TEARDROP = OBJLoader.INSTANCE.loadModel(new OBJModel.ModelSettings(prefix("models/obj/meteor.obj"), false, false, true, true, null));
    public static final SimpleRenderable RENDER = METEOR_TEARDROP.bakeRenderable(StandaloneModelConfiguration.INSTANCE);


    public FallingStarRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    //renders the coma obj model
    //        poseStack.scale(beamWidth + 1, beamWidth + 1, beamWidth + 1);
    //        RENDER.render(poseStack, DELAYED_RENDER, (r) -> COMA_TYPE, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, partialTicks, MultipartTransforms.EMPTY);
    @Override
    public void render(FallingEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        Color[] colors = new Color[]{
                Color.RED, Color.ORANGE, Color.RED, Color.YELLOW, Color.WHITE, Color.WHITE
        };
        VertexBuilder builder = RenderHelper.create().setLight(FULL_BRIGHT);
        ArrayList<Vec3> positions = DataHelper.rotatingCirclePositions(Vec3.ZERO, 4, 60, (long) (entity.level.getGameTime()+partialTicks), 360);
        for (int i = 0; i < colors.length; i++) {
            int finalI = i;
            VertexConsumer fire = DELAYED_RENDER.getBuffer(queueUniformChanges(copy(i, FIRE_TYPE),
                    (instance -> instance.safeGetUniform("Speed").set(100f + 300f * finalI))));

            float index = colors.length - i;
            float size = index * 2 + (float) Math.exp(i * 0.15f);
            float width = size * 0.75f;
            float alpha = 0.1f + 0.15f * (float) Math.exp(i * 0.5f);
            Color color = colors[i];
            builder.setColor(color)
                    .setAlpha(alpha)
                    .renderTrail(fire, poseStack, width, positions)
                    .setAlpha(alpha * 0.75f)
                    .renderTrail(DELAYED_RENDER.getBuffer(LIGHT_TYPE), poseStack, width * 0.7f, positions);
        }
//        for (Vec3 position : positions) {
//            builder.setColor(Color.RED)
//                    .setOffset((float)position.x, (float)position.y, (float)position.z)
//                    .renderQuad(DELAYED_RENDER.getBuffer(LIGHT_TYPE), poseStack, 2);
//        }
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(FallingEntity p_114482_) {
        return LIGHT_TRAIL;
    }
}