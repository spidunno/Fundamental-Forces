package com.sammy.fundamental_forces.client.renderers.entity.falling;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.fundamental_forces.common.entity.falling.FallingEntity;
import com.sammy.fundamental_forces.core.helper.RenderHelper;
import com.sammy.fundamental_forces.core.helper.RenderHelper.VertexBuilder;
import com.sammy.fundamental_forces.core.systems.rendering.RenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.StandaloneModelConfiguration;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.client.model.renderable.MultipartTransforms;
import net.minecraftforge.client.model.renderable.SimpleRenderable;

import java.awt.*;

import static com.sammy.fundamental_forces.core.helper.DataHelper.prefix;
import static com.sammy.fundamental_forces.core.handlers.RenderHandler.DELAYED_RENDER;
import static com.sammy.fundamental_forces.core.helper.RenderHelper.FULL_BRIGHT;
import static com.sammy.fundamental_forces.core.systems.rendering.RenderTypes.bufferUniformChanges;
import static com.sammy.fundamental_forces.core.systems.rendering.RenderTypes.copy;

public class FallingStarRenderer extends EntityRenderer<FallingEntity> {

    private static final ResourceLocation COMA = prefix("textures/vfx/uv_test.png");
    private static final RenderType COMA_TYPE = RenderTypes.ADDITIVE_TEXTURE.apply(COMA);

    private static final ResourceLocation LIGHT_TRAIL = prefix("textures/vfx/light_trail.png");
    private static final RenderType LIGHT_TYPE = RenderTypes.SCROLLING_TEXTURE_TRIANGLE.apply(LIGHT_TRAIL);

    private static final ResourceLocation FIRE_TRAIL = prefix("textures/vfx/fire_trail.png");
    private static final RenderType FIRE_TYPE = RenderTypes.SCROLLING_TEXTURE_TRIANGLE.apply(FIRE_TRAIL);

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
        for (int i = 0; i < colors.length; i++) {
            int finalI = i;
            VertexConsumer fire = DELAYED_RENDER.getBuffer(bufferUniformChanges(copy(i, FIRE_TYPE),
                    (instance -> instance.safeGetUniform("Speed").set(300f + 100f * finalI))));

            float size = (colors.length - i);
            float beamLength = size * 2;
            Color color = colors[i];
            builder.setColor(color).setAlpha(0.1f + 0.15f * (colors.length - i))
                    .renderBeam(DELAYED_RENDER.getBuffer(LIGHT_TYPE), poseStack, entity.position(), entity.position().add(0, beamLength, 0), size)
                    .renderBeam(fire, poseStack, entity.position(), entity.position().add(0, beamLength, 0), size);
        }
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(FallingEntity p_114482_) {
        return LIGHT_TRAIL;
    }
}