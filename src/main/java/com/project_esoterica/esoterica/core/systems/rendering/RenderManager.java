package com.project_esoterica.esoterica.core.systems.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.common.capability.WorldDataCapability;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class RenderManager {
    @OnlyIn(Dist.CLIENT)
    public static MultiBufferSource.BufferSource DELAYED_RENDER = MultiBufferSource.immediate(new BufferBuilder(256));
    public static Frustum FRUSTUM;

    public static void onRenderLast(RenderWorldLastEvent event) {
        prepareFrustum(event.getMatrixStack(), Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition(), event.getProjectionMatrix());
        WorldDataCapability.getCapability(Minecraft.getInstance().level).ifPresent(capability -> {
            for (WorldEventInstance instance : capability.ACTIVE_WORLD_EVENTS) {
                if (instance.canRender()) {
                    instance.render(event.getMatrixStack(), RenderManager.DELAYED_RENDER, event.getPartialTicks());
                }
            }
        });
        event.getMatrixStack().pushPose();
        DELAYED_RENDER.endBatch();
        event.getMatrixStack().popPose();
    }

    public static void prepareFrustum(PoseStack p_172962_, Vec3 p_172963_, Matrix4f p_172964_) {
        Matrix4f matrix4f = p_172962_.last().pose();
        double d0 = p_172963_.x();
        double d1 = p_172963_.y();
        double d2 = p_172963_.z();
        FRUSTUM = new Frustum(matrix4f, p_172964_);
        FRUSTUM.prepare(d0, d1, d2);
    }

    public static void renderSphere(VertexConsumer vertexConsumer, PoseStack stack, float radius, int longs, int lats)
    {
        renderSphere(vertexConsumer, stack, radius, longs, lats,255,255,255,255,15728880);
    }
    public static void renderSphere(VertexConsumer vertexConsumer, PoseStack stack, float radius, int longs, int lats, int r, int g, int b, int a, int light) {
        Matrix4f last = stack.last().pose();
        float startU = 0;
        float startV = 0;
        float endU = Mth.PI * 2;
        float endV = Mth.PI;
        float stepU = (endU - startU) / longs;
        float stepV = (endV - startV) / lats;
        for (int i = 0; i < longs; ++i) {
            // U-points
            for (int j = 0; j < lats; ++j) {
                // V-points
                float u = i * stepU + startU;
                float v = j * stepV + startV;
                float un = (i + 1 == longs) ? endU : (i + 1) * stepU + startU;
                float vn = (j + 1 == lats) ? endV : (j + 1) * stepV + startV;
                Vector3f p0 = parametricSphere(u, v, radius);
                Vector3f p1 = parametricSphere(u, vn, radius);
                Vector3f p2 = parametricSphere(un, v, radius);
                Vector3f p3 = parametricSphere(un, vn, radius);

                float textureU = u/endU*radius;
                float textureV = v/endV*radius;
                float textureUN = un/endU*radius;
                float textureVN = vn/endV*radius;
                vertex(vertexConsumer,last, p0.x(), p0.y(), p0.z(), r,g,b,a,textureU,textureV,light);
                vertex(vertexConsumer,last, p2.x(), p2.y(), p2.z(), r,g,b,a,textureUN,textureV,light);
                vertex(vertexConsumer,last, p1.x(), p1.y(), p1.z(), r,g,b,a,textureU,textureVN,light);

                vertex(vertexConsumer,last, p3.x(), p3.y(), p3.z(), r,g,b,a,textureUN,textureVN,light);
                vertex(vertexConsumer,last, p1.x(), p1.y(), p1.z(), r,g,b,a,textureU,textureVN,light);
                vertex(vertexConsumer,last, p2.x(), p2.y(), p2.z(), r,g,b,a,textureUN,textureV,light);
            }
        }
    }

    public static void vertex(VertexConsumer vertexConsumer, Matrix4f last, float x, float y, float z) {
        vertexConsumer.vertex(last, x, y, z).endVertex();
    }

    public static void vertex(VertexConsumer vertexConsumer, Matrix4f last, float x, float y, float z, float u, float v) {
        vertexConsumer.vertex(last, x, y, z).uv(u, v).endVertex();
    }

    public static void vertex(VertexConsumer vertexConsumer, Matrix4f last, float x, float y, float z, float u, float v, int light) {
        vertexConsumer.vertex(last, x, y, z).uv(u, v).uv2(light).endVertex();
    }

    public static void vertex(VertexConsumer vertexConsumer, Matrix4f last, float x, float y, float z, int r, int g, int b, int a) {
        vertexConsumer.vertex(last, x, y, z).color(r, g, b, a).endVertex();
    }

    public static void vertex(VertexConsumer vertexConsumer, Matrix4f last, float x, float y, float z, int r, int g, int b, int a, float u, float v) {
        vertexConsumer.vertex(last, x, y, z).color(r, g, b, a).uv(u, v).endVertex();
    }

    public static void vertex(VertexConsumer vertexConsumer, Matrix4f last, float x, float y, float z, int r, int g, int b, int a, float u, float v, int light) {
        vertexConsumer.vertex(last, x, y, z).color(r, g, b, a).uv(u, v).uv2(light).endVertex();
    }

    public static Vector3f parametricSphere(float u, float v, float r) {
        return new Vector3f(Mth.cos(u) * Mth.sin(v) * r, Mth.cos(v) * r, Mth.sin(u) * Mth.sin(v) * r);
    }
}