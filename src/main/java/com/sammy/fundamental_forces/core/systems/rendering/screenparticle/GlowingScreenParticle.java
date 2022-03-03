package com.sammy.fundamental_forces.core.systems.rendering.screenparticle;


import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.GenericScreenParticle;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.options.ScreenParticleOptions;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.rendertypes.AdditiveScreenParticleRenderType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;


public class GlowingScreenParticle extends GenericScreenParticle {
    public GlowingScreenParticle(ClientLevel world, ScreenParticleOptions data, double x, double y, double vx, double vy) {
        super(world, data, x, y, vx, vy);
    }
    @Override
    public ParticleRenderType getRenderType() {
        return AdditiveScreenParticleRenderType.INSTANCE;
    }
}