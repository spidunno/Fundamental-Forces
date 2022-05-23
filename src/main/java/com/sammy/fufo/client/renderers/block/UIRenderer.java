package com.sammy.fufo.client.renderers.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.blockentity.UITestBlockEntity;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

import static com.sammy.ortus.handlers.RenderHandler.DELAYED_RENDER;

public class UIRenderer implements BlockEntityRenderer<UITestBlockEntity> {

    private static final ResourceLocation TEST_BEAM = FufoMod.fufoPath("textures/vfx/uv_test.png");
    private static final RenderType TEST_BEAM_TYPE = OrtusRenderTypeRegistry.ADDITIVE_TEXTURE.apply(TEST_BEAM);
    public UIRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(UITestBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0,1,0);
        VertexConsumer consumer = DELAYED_RENDER.getBuffer(TEST_BEAM_TYPE);
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(pBlockEntity.getLevel().getGameTime() * 10));
        VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setOffset(0.5f, 1.5f, 0.5f).renderSphere(consumer, pPoseStack, 1, 6, 6);
        pPoseStack.popPose();
    }
}
