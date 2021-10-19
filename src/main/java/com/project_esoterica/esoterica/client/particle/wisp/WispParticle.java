package com.project_esoterica.esoterica.client.particle.wisp;


import com.project_esoterica.esoterica.core.systems.rendering.particle.GenericMalumParticle;
import com.project_esoterica.esoterica.core.systems.rendering.particle.data.ParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;

public class WispParticle extends GenericMalumParticle {
    public WispParticle(ClientLevel world, ParticleOptions data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
    }

    @Override
    protected int getLightColor(float p_107249_) {
        return 0xF000F0;
    }
}