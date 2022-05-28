package com.sammy.fufo.client.renderers.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.sammy.fufo.FufoMod;
import com.sammy.fufo.client.ui.*;
import com.sammy.fufo.client.ui.constraint.PercentageConstraint;
import com.sammy.fufo.client.ui.constraint.PixelConstraint;
import com.sammy.fufo.common.blockentity.ArrayBlockEntity;
import com.sammy.fufo.common.blockentity.UITestBlockEntity;
import com.sammy.ortus.helpers.util.AnimationTickHolder;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;


import static com.sammy.ortus.handlers.RenderHandler.DELAYED_RENDER;

public class ArrayRenderer implements BlockEntityRenderer<ArrayBlockEntity> {

    BlockEntityRendererProvider.Context context;
    public ArrayRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }


    @Override
    public void render(ArrayBlockEntity pBlockEntity, float partialTicks, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        if(true) return;
        int uiWidth = pBlockEntity.uiWidth;
        int uiHeight = pBlockEntity.uiHeight;

        double uiWidthBlocks = uiWidth / 16.0;
        double uiHeightBlocks = uiHeight / 16.0;

        Vec3 uiPosition = new Vec3(0.5, 2.0f, 0.5f);

        pPoseStack.pushPose();
        //VertexConsumer consumer = DELAYED_RENDER.getBuffer(TEST_BEAM_TYPE);
        Vec3 player = Minecraft.getInstance().player.getEyePosition();
        Vec3 center = new Vec3(pBlockEntity.getBlockPos().getX() + uiPosition.x, pBlockEntity.getBlockPos().getY() + uiPosition.y, pBlockEntity.getBlockPos().getZ() + uiPosition.z);

        Vec3 startYaw = new Vec3(0.0, 0.0, 1.0);
        Vec3 endYaw = new Vec3(player.x, 0.0, player.z).subtract(new Vec3(center.x, 0.0, center.z)).normalize();
        Vec3 d = player.subtract(center);

        // Find angle between start & end in yaw
        float yaw = (float) Math.toDegrees(Math.atan2(endYaw.x - startYaw.x, endYaw.z - startYaw.z)) + 90;

        // Find angle between start & end in pitch
        float pitch = (float) Math.toDegrees(Math.atan2(Math.sqrt(d.z * d.z + d.x * d.x), d.y) + Math.PI);

        Quaternion Q = Quaternion.ONE.copy();

        // doubling to account for how quats work
        Q.mul(new Quaternion(new Vector3f(0.0f, 1.0f, 0.0f), yaw * 2, true));
        Q.mul(new Quaternion(new Vector3f(1.0f, 0.0f, 0.0f), pitch + 90, true));

        pPoseStack.translate(uiPosition.x, uiPosition.y, uiPosition.z);
        pPoseStack.mulPose(Q);
        pPoseStack.translate(-uiWidthBlocks / 2, uiHeightBlocks / 2, 0.0);
        pPoseStack.scale(1 / 16f, 1 / 16f, 1 / 16f);

        pBlockEntity.box.xConstraint = new PixelConstraint(uiWidth);
        pBlockEntity.box.yConstraint = new PixelConstraint(uiHeight);

        long currentTime = System.currentTimeMillis();
        if(pBlockEntity.previousTime == -1)
            pBlockEntity.previousTime = currentTime;
        long deltaTime = currentTime - pBlockEntity.previousTime;
        double delta = deltaTime / 1000.0;
//        if(pBlockEntity.box != null) pBlockEntity.box.render(context, consumer, pPoseStack, delta);
        pBlockEntity.previousTime = currentTime;
        pPoseStack.popPose();
    }
}
