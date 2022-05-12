package com.sammy.fufo.client.renderers.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.fufo.common.blockentity.AnchorBlockEntity;
import com.sammy.ortus.helpers.DataHelper;
import com.sammy.ortus.helpers.RenderHelper;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static com.sammy.fufo.FufoMod.prefix;
import static com.sammy.ortus.handlers.RenderHandler.DELAYED_RENDER;

public class AnchorRenderer implements BlockEntityRenderer<AnchorBlockEntity> {

    private static final ResourceLocation TEST_BEAM  = prefix("textures/vfx/uv_test.png");
    private static final RenderType TEST_BEAM_TYPE = OrtusRenderTypeRegistry.ADDITIVE_TEXTURE.apply(TEST_BEAM);

    public AnchorRenderer(BlockEntityRendererProvider.Context context) {
    }
    @Override
    public void render(AnchorBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        VertexConsumer beamConsumer = DELAYED_RENDER.getBuffer(TEST_BEAM_TYPE);
        blockEntityIn.NEARBY_ANCHORS.forEach(anchor -> {
            poseStack.pushPose();
            RenderHelper.create().renderBeam(beamConsumer, poseStack, DataHelper.fromBlockPos(blockEntityIn.getBlockPos()), DataHelper.fromBlockPos(anchor.getBlockPos()), 0.1f).setOffset(0.5f, 0.5f, 0.5f);
            poseStack.popPose();
        });
    }
}
