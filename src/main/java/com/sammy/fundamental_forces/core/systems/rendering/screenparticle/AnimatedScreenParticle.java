package com.sammy.fundamental_forces.core.systems.rendering.screenparticle;

import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.options.ScreenParticleOptions;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.rendertypes.TransparentScreenParticleRenderType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;

import java.util.ArrayList;

public class AnimatedScreenParticle extends GenericScreenParticle {
    public final ParticleEngine.MutableSpriteSet spriteSet;

    public AnimatedScreenParticle(ClientLevel world, ScreenParticleOptions data, double x, double y, double vx, double vy, SpriteSet spriteSet) {
        super(world, data, x, y, vx, vy);
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
        return TransparentScreenParticleRenderType.INSTANCE;
    }
}