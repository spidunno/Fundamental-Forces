package com.sammy.fundamental_forces.core.systems.rendering.screenparticle;

import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.options.ScreenParticleOptions;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.rendertypes.TransparentScreenParticleRenderType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;

import java.util.ArrayList;

public class FrameSetScreenParticle extends GenericScreenParticle {
    public final ParticleEngine.MutableSpriteSet spriteSet;
    public ArrayList<Integer> frameSet = new ArrayList<>();

    public FrameSetScreenParticle(ClientLevel world, ScreenParticleOptions data, double x, double y, double vx, double vy, SpriteSet spriteSet) {
        super(world, data, x, y, vx, vy);
        this.spriteSet = (ParticleEngine.MutableSpriteSet) spriteSet;
        this.setSprite(0);
    }

    @Override
    public void tick() {
        setSprite(frameSet.get(age));
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return TransparentScreenParticleRenderType.INSTANCE;
    }

    public void setSprite(int spriteIndex) {
        if (spriteIndex < spriteSet.sprites.size() && spriteIndex >= 0) {
            setSprite(spriteSet.sprites.get(spriteIndex));
        }
    }

    protected void addLoop(int min, int max, int times) {
        for (int i = 0; i < times; i++) {
            addFrames(min, max);
        }
    }

    protected void addFrames(int min, int max) {
        for (int i = min; i <= max; i++) {
            frameSet.add(i);
        }
    }

    protected void insertFrames(int insertIndex, int min, int max) {
        for (int i = min; i <= max; i++) {
            frameSet.add(insertIndex, i);
        }
    }
}