package com.project_esoterica.esoterica.core.systems.rendering.particle.screen;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.project_esoterica.esoterica.core.systems.rendering.RenderUtilities;
import com.project_esoterica.esoterica.core.systems.rendering.particle.GenericParticle;
import com.project_esoterica.esoterica.core.systems.rendering.particle.options.ParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;

public abstract class GenericScreenParticle extends GenericParticle {
    public GenericScreenParticle(ClientLevel world, ParticleOptions data, double x, double y, double vx, double vy) {
        super(world, data, x, y, 0, vx, vy, 0);
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        float size = getQuadSize(pPartialTicks);
        float u0 = getU0();
        float u1 = getU1();
        float v0 = getV0();
        float v1 = getV1();

        RenderUtilities.innermostBlit(pBuffer, (int) x - 2, (int) y - 2, 24 * size, 42 * size, rCol, gCol, bCol, alpha, getLightColor(pPartialTicks), u0, v0, u1, v1);
    }
}
