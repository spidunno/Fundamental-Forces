package com.project_esoterica.esoterica.client.particles.wisp;


import com.project_esoterica.esoterica.core.systems.rendering.screenparticle.GenericScreenParticle;
import com.project_esoterica.esoterica.core.systems.rendering.screenparticle.options.ScreenParticleOptions;
import com.project_esoterica.esoterica.core.systems.rendering.screenparticle.rendertypes.AdditiveScreenParticleRenderType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;


public class WispScreenParticle extends GenericScreenParticle {
    public WispScreenParticle(ClientLevel world, ScreenParticleOptions data, double x, double y, double vx, double vy) {
        super(world, data, x, y, vx, vy);
    }
    @Override
    public ParticleRenderType getRenderType() {
        return AdditiveScreenParticleRenderType.INSTANCE;
    }
}