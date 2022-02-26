package com.project_esoterica.esoterica.client.particles.wisp;


import com.mojang.blaze3d.vertex.VertexConsumer;
import com.project_esoterica.esoterica.core.systems.rendering.particle.options.ParticleOptions;
import com.project_esoterica.esoterica.core.systems.rendering.particle.rendertypes.AdditiveScreenParticleRenderType;
import com.project_esoterica.esoterica.core.systems.rendering.particle.screen.GenericScreenParticle;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;


public class WispScreenParticle extends GenericScreenParticle {

    public WispScreenParticle(ClientLevel world, ParticleOptions data, double x, double y, double vx, double vy) {
        super(world, data, x, y, vx, vy);
    }

    @Override
    protected int getLightColor(float p_107249_) {
        return 0xF000F0;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return AdditiveScreenParticleRenderType.INSTANCE;
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        super.render(consumer, camera, partialTicks);
    }
}