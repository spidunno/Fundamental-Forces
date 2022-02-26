package com.project_esoterica.esoterica.core.systems.rendering.particle.screen;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.project_esoterica.esoterica.core.systems.rendering.RenderUtilities;
import com.project_esoterica.esoterica.core.systems.rendering.particle.GenericAnimatedParticle;
import com.project_esoterica.esoterica.core.systems.rendering.particle.options.ParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;

public abstract class GenericAnimatedScreenParticle extends GenericAnimatedParticle {

    public GenericAnimatedScreenParticle(ClientLevel world, ParticleOptions data, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, data, x, y, z, vx, vy, vz, spriteSet);
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
        RenderUtilities.innermostBlit(pBuffer, (int) x - 2, (int) y - 2, 16 * size, 16 * size, rCol, gCol, bCol, alpha, getLightColor(pPartialTicks), u0, v0, u1, v1);
    }
}