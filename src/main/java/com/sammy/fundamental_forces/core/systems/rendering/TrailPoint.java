package com.sammy.fundamental_forces.core.systems.rendering;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import net.minecraft.client.renderer.texture.OverlayTexture;

import static com.sammy.fundamental_forces.core.helper.RenderHelper.vertexPosColorUVLight;

public record TrailPoint(float xp, float xn, float yp, float yn, float z) {

    public void renderStart(Matrix3f normal, VertexConsumer builder, int packedLight, float r, float g, float b, float a, float u0, float v0, float u1, float v1) {
        builder.vertex(xp, yp, z).color(r, g, b, a).uv(u0, v0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
        builder.vertex(xn, yn, z).color(r, g, b, a).uv(u1, v0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
    }

    public void renderEnd(Matrix3f normal, VertexConsumer builder, int packedLight, float r, float g, float b, float a, float u0, float v0, float u1, float v1) {
        builder.vertex(xn, yn, z).color(r, g, b, a).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
        builder.vertex(xp, yp, z).color(r, g, b, a).uv(u0, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
    }
}