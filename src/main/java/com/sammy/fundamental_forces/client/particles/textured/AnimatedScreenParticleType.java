package com.sammy.fundamental_forces.client.particles.textured;

import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.AnimatedScreenParticle;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.base.ScreenParticle;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.ScreenParticleType;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.options.ScreenParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;

public class AnimatedScreenParticleType extends ScreenParticleType<ScreenParticleOptions> {

    public AnimatedScreenParticleType() {
        super();
    }

    public static class Factory implements ParticleProvider<ScreenParticleOptions> {
        public final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public ScreenParticle createParticle(ClientLevel pLevel, ScreenParticleOptions options, double pX, double pY, double pXSpeed, double pYSpeed) {
            return new AnimatedScreenParticle(pLevel, options, pX, pY, pXSpeed, pYSpeed, sprite);
        }
    }
}