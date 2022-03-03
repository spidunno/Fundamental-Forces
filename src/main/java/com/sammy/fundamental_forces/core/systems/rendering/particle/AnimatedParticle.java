package com.sammy.fundamental_forces.core.systems.rendering.particle;

import com.sammy.fundamental_forces.core.systems.rendering.particle.options.ParticleOptions;
import com.sammy.fundamental_forces.core.systems.rendering.particle.rendertypes.TransparentParticleRenderType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;

import java.util.ArrayList;

public class AnimatedParticle extends GenericParticle {
    public final ParticleEngine.MutableSpriteSet spriteSet;

    public AnimatedParticle(ClientLevel world, ParticleOptions data, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, data, x, y, z, vx, vy, vz);
        this.spriteSet = (ParticleEngine.MutableSpriteSet) spriteSet;
        setSprite(this.spriteSet.sprites.get(0));
    }

    @Override
    public void tick() {
        setSpriteFromAge(spriteSet);
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return TransparentParticleRenderType.INSTANCE;
    }
}