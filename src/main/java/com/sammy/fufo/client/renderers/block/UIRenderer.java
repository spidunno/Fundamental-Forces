package com.sammy.fufo.client.renderers.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3d;
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
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

import static com.sammy.ortus.handlers.RenderHandler.DELAYED_RENDER;

public class UIRenderer implements BlockEntityRenderer<UITestBlockEntity> {

    public UIRenderer(BlockEntityRendererProvider.Context context) {
    }

    private static final ResourceLocation TEST_BEAM = FufoMod.fufoPath("textures/vfx/uv_test.png");
    private static final RenderType TEST_BEAM_TYPE = OrtusRenderTypeRegistry.ADDITIVE_TEXTURE.apply(TEST_BEAM);
    @Override
    public void render(UITestBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        if(pBlockEntity.toggle){
            VertexConsumer consumer = DELAYED_RENDER.getBuffer(TEST_BEAM_TYPE);
            Vec3 player = Minecraft.getInstance().player.position();
            Vec3 pos = new Vec3(pBlockEntity.getBlockPos().getX() + 0.5, pBlockEntity.getBlockPos().getY() + 0.5, pBlockEntity.getBlockPos().getZ() + 0.5);
            Vector3f cross = new Vector3f(pos.cross(player));
            Quaternion Q = new Quaternion(cross.x(), cross.y(), cross.z(), 1.0f+(float)pos.dot(player));
            Q.normalize();
            pPoseStack.mulPose(Q);
            VFXBuilders.createWorld().setOffset(0.5f,1.5f,0.5f).renderQuad(consumer, pPoseStack, 0.75f, 0.4f).setColor(Color.WHITE).setAlpha(1);
        }
        pPoseStack.popPose();
    }
}
