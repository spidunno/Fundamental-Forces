package com.project_esoterica.esoterica.core.systems.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.project_esoterica.esoterica.common.capability.WorldDataCapability;
import com.project_esoterica.esoterica.config.ClientConfig;
import com.project_esoterica.esoterica.core.setup.worldevent.WorldEventRenderers;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventInstance;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class RenderManager {
    public static HashMap<RenderType, BufferBuilder> BUFFERS = new HashMap<>();
    public static HashMap<RenderType, RenderTypeShaderHandler> HANDLERS = new HashMap<>();
    public static MultiBufferSource.BufferSource DELAYED_RENDER = null;
    public static Matrix4f PARTICLE_MATRIX = null;
    public static Matrix4f SCREEN_PARTICLE_MATRIX = null;
    public static Frustum FRUSTUM;

    public static void setupDelayedRenderer(FMLClientSetupEvent event) {
        DELAYED_RENDER = MultiBufferSource.immediateWithBuffers(BUFFERS, new BufferBuilder(256));
    }
    public static void onRenderLast(RenderLevelLastEvent event) {
        prepareFrustum(event.getPoseStack(), Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition(), event.getProjectionMatrix());
        WorldDataCapability.getCapability(Minecraft.getInstance().level).ifPresent(capability -> {
            for (WorldEventInstance instance : capability.ACTIVE_WORLD_EVENTS) {
                WorldEventRenderer<WorldEventInstance> renderer = WorldEventRenderers.RENDERERS.get(instance.type);
                if (renderer != null) {
                    if (renderer.canRender(instance)) {
                        renderer.render(instance, event.getPoseStack(), RenderManager.DELAYED_RENDER, event.getPartialTick());
                    }
                }
            }
        });
        if (ClientConfig.DELAYED_PARTICLE_RENDERING.get()) {
            RenderSystem.getModelViewStack().pushPose();
            RenderSystem.getModelViewStack().setIdentity();
            if (PARTICLE_MATRIX != null) RenderSystem.getModelViewStack().mulPoseMatrix(PARTICLE_MATRIX);
            RenderSystem.applyModelViewMatrix();
            DELAYED_RENDER.endBatch(RenderTypes.ADDITIVE_PARTICLE);
            DELAYED_RENDER.endBatch(RenderTypes.ADDITIVE_BLOCK_PARTICLE);
            RenderSystem.getModelViewStack().popPose();
            RenderSystem.applyModelViewMatrix();
        }
        event.getPoseStack().pushPose();
        for (RenderType type : BUFFERS.keySet()) {
            if (HANDLERS.containsKey(type)) {
                RenderTypeShaderHandler handler = HANDLERS.get(type);
                handler.updateShaderData();
            }
            DELAYED_RENDER.endBatch(type);
        }
        event.getPoseStack().popPose();
    }

    public static void prepareFrustum(PoseStack poseStack, Vec3 position, Matrix4f stack) {
        Matrix4f matrix4f = poseStack.last().pose();
        double d0 = position.x();
        double d1 = position.y();
        double d2 = position.z();
        FRUSTUM = new Frustum(matrix4f, stack);
        FRUSTUM.prepare(d0, d1, d2);
    }

}