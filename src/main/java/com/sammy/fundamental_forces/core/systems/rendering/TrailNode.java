package com.sammy.fundamental_forces.core.systems.rendering;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Vector4f;
import com.sammy.fundamental_forces.core.helper.MathHelper;
import com.sammy.fundamental_forces.core.helper.RenderHelper;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

import java.awt.*;

public class TrailNode {

    public final float xp, xn;
    public final float yp, yn;
    public final float z;
    public final float width;

    public TrailNode(float xp, float xn, float yp, float yn, float z, float width) {

        this.xp = xp;
        this.xn = xn;
        this.yp = yp;
        this.yn = yn;
        this.z = z;
        this.width = width;
    }

    public TrailNode(Vector4f pos, Vec2 perp, float width) {

        this(pos.x() + perp.x, pos.x() - perp.x, pos.y() + perp.y, pos.y() - perp.y, pos.z(), width);
    }

    public TrailNode(float xp, float xn, float yp, float yn, float z) {

        this(xp, xn, yp, yn, z, MathHelper.dist(xp - xn, yp - yn));
    }

    public float xMid() {

        return (xp + xn) * 0.5F;
    }

    public float yMid() {

        return (yp + yn) * 0.5F;
    }

    public TrailNode renderStart(Matrix3f normal, VertexConsumer builder, int packedLight, float r, float g, float b, float a, float uFrom, float vFrom, float uTo, float vTo) {

        builder.vertex(xp, yp, z).color(r, g, b, a).uv(uFrom, vFrom).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
        builder.vertex(xn, yn, z).color(r, g, b, a).uv(uTo, vFrom).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
        return this;
    }

    public TrailNode renderEnd(Matrix3f normal, VertexConsumer builder, int packedLight, float r, float g, float b, float a, float uFrom, float vFrom, float uTo, float vTo) {

        builder.vertex(xn, yn, z).color(r, g, b, a).uv(uTo, vTo).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
        builder.vertex(xp, yp, z).color(r, g, b, a).uv(uFrom, vTo).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
        return this;
    }

    public TrailNode renderMid(Matrix3f normal, VertexConsumer builder, int packedLight, float r, float g, float b, float a, float uFrom, float vFrom, float uTo, float vTo) {

        renderEnd(normal, builder, packedLight, r, g, b, a, uFrom, vFrom, uTo, vTo);
        renderStart(normal, builder, packedLight, r, g, b, a, uFrom, vFrom, uTo, vTo);
        return this;
    }

    public TrailNode renderStart(Matrix3f normal, VertexConsumer builder, int packedLight, float r, float g, float b, float a) {

        return renderStart(normal, builder, packedLight, r, g, b, a, 0, 0, 1, 1);
    }

    public TrailNode renderEnd(Matrix3f normal, VertexConsumer builder, int packedLight, float r, float g, float b, float a) {

        return renderEnd(normal, builder, packedLight, r, g, b, a, 0, 0, 1, 1);
    }

    public TrailNode renderMid(Matrix3f normal, VertexConsumer builder, int packedLight, float r, float g, float b, float a) {

        return renderMid(normal, builder, packedLight, r, g, b, a, 0, 0, 1, 1);
    }

    public static void renderTrailNodes(Matrix3f normal, VertexConsumer builder, int packedLight, TrailNode[] nodes, Color color, float a, float u0, float v0, float u1, float v1) {
        renderTrailNodes(normal, builder, packedLight, nodes, color.getRed(), color.getGreen(), color.getBlue(), a, u0, v0, u1, v1);
    }

//    public static void renderTrailNodes(Matrix3f normal, VertexConsumer builder, int packedLight, TrailNode[] nodes, float r, float g, float b, float alpha, float u0, float v0, float u1, float v1) {
//        nodes[0].renderStart(normal, builder, packedLight, r, g, b, alpha, u0, v0, u1, v1);
//        int count = nodes.length - 1;
//        for (int i = 1; i < count; ++i) {
//            nodes[i].renderMid(normal, builder, packedLight, r, g, b, alpha, u0, v0, u1, v1);
//        }
//        nodes[count].renderEnd(normal, builder, packedLight, r, g, b, alpha, u0, v0, u1, v1);
//    }

    public static void renderTrailNodes(Matrix3f normal, VertexConsumer builder, int packedLight, TrailNode[] nodes, float r, float g, float b, float a, float u0, float v0, float u1, float v1) {
        int count = nodes.length - 1;
        float increment = 1.0F / count;
        float start = Mth.lerp(increment, v0, v1);
        float nearingEnd = Mth.lerp((count - 1) * increment, v0, v1);

        nodes[0].renderStart(normal, builder, packedLight, r, g, b, a, u0, v0, u1, start);
        for (int i = 1; i < count; ++i) {
            float previous = Mth.lerp((i - 1) * increment, v0, v1);
            float current = Mth.lerp(i * increment, v0, v1);
            float next = Mth.lerp((i + 1) * increment, v0, v1);
            nodes[i].renderEnd(normal, builder, packedLight, r, g, b, a, u0, previous, u1, current);
            nodes[i].renderStart(normal, builder, packedLight, r, g, b, a, u0, current, u1, next);
        }
        nodes[count].renderEnd(normal, builder, packedLight, r, g, b, a, u0, nearingEnd, u1, v1);
    }
}