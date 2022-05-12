package com.sammy.fufo.client.renderers.entity.wisp;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.sammy.fufo.common.entity.wisp.WispEntity;
import com.sammy.ortus.helpers.DataHelper;
import com.sammy.ortus.helpers.RenderHelper;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sammy.fufo.FufoMod.prefix;
import static com.sammy.ortus.handlers.RenderHandler.DELAYED_RENDER;

public class WispEntityRenderer extends EntityRenderer<WispEntity> {

    private static final ResourceLocation WISP_TEXTURE = prefix("textures/particle/square.png");
    private static final RenderType WISP_TYPE = OrtusRenderTypeRegistry.ADDITIVE_TEXTURE.apply(WISP_TEXTURE);

    public WispEntityRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(WispEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
//        poseStack.pushPose();
        if (true) {
            return;
        }
        RenderHelper.VertexBuilder builder = RenderHelper.create();
        DataHelper.trackPastPositions(entity.pastPositions, entity.position(), 0f);
        ArrayList<Vec3> positions = Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16).map(i -> entity.position().add(0, i, 0)).collect(Collectors.toCollection(ArrayList::new));

        Color color = entity.getColor();
//        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
//        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
//        builder.setColor(color).renderQuad(DELAYED_RENDER.getBuffer(WISP_TYPE), poseStack, 0.5f);

        builder.setColor(color).setOffset((float) -entity.getX(), (float) -entity.getY(), (float) -entity.getZ())
                .renderTrail(DELAYED_RENDER.getBuffer(WISP_TYPE), poseStack, positions.stream().map(p -> new Vector4f((float) p.x, (float) p.y, (float) p.z, 1)).collect(Collectors.toList()), f -> f);

//        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(WispEntity p_114482_) {
        return WISP_TEXTURE;
    }
}