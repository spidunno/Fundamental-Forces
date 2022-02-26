package com.project_esoterica.esoterica.client.particles.wisp;

import com.mojang.serialization.Codec;
import com.project_esoterica.esoterica.core.systems.rendering.particle.options.ParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;

import javax.annotation.Nullable;

public class WispScreenParticleType extends ParticleType<ParticleOptions> {
    public WispScreenParticleType() {
        super(false, ParticleOptions.DESERIALIZER);
    }


    @Override
    public Codec<ParticleOptions> codec() {
        return ParticleOptions.codecFor(this);
    }

    public static class Factory implements ParticleProvider<ParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }


        @Nullable
        @Override
        public Particle createParticle(ParticleOptions data, ClientLevel world, double x, double y, double z, double mx, double my, double mz) {
            WispScreenParticle ret = new WispScreenParticle(world, data, x, y, mx, my);
            ret.pickSprite(sprite);
            return ret;
        }
    }
}
