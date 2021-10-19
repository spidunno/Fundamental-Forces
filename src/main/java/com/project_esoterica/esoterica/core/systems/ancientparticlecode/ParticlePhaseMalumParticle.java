package com.project_esoterica.esoterica.core.systems.ancientparticlecode;

import com.project_esoterica.esoterica.core.systems.ancientparticlecode.data.ParticleOptions;
import com.project_esoterica.esoterica.core.systems.ancientparticlecode.phases.ParticlePhase;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ParticlePhaseMalumParticle extends TextureSheetParticle
{
    public final SpriteSet spriteSet;
    public ArrayList<ParticlePhase> phases;
    public ParticleOptions data;
    float[] hsv1 = new float[3], hsv2 = new float[3];
    
    public ParticlePhaseMalumParticle(ClientLevel world, ParticleOptions data, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet, ParticlePhase... phases)
    {
        super(world, x, y, z, vx, vy, vz);
        this.phases = new ArrayList<>(Arrays.stream(phases).toList());
        this.spriteSet = spriteSet;
        this.setSprite(phases[0].currentFrame);
        this.setPos(x, y, z);
        this.data = data;
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
        this.gravity = data.gravity ? 1 : 0;
        Color.RGBtoHSB((int) (255 * Math.min(1.0f, data.r1)), (int) (255 * Math.min(1.0f, data.g1)), (int) (255 * Math.min(1.0f, data.b1)), hsv1);
        Color.RGBtoHSB((int) (255 * Math.min(1.0f, data.r2)), (int) (255 * Math.min(1.0f, data.g2)), (int) (255 * Math.min(1.0f, data.b2)), hsv2);
        updateTraits();
    }
    public void setSprite(int spriteIndex)
    {
        if (spriteSet instanceof ParticleEngine.MutableSpriteSet)
        {
            ParticleEngine.MutableSpriteSet animatedSprite = (ParticleEngine.MutableSpriteSet) spriteSet;
            if (spriteIndex < animatedSprite.sprites.size() && spriteIndex >= 0) //idiot-proof if statement. The idiot is sammy
            {
                setSprite(animatedSprite.sprites.get(spriteIndex));
            }
        }
    }

    protected float getCoeff() {
        return (float) this.age / (float) this.lifetime;
    }

    protected float getColorCoeff() {
        float increasedAge = Math.min(this.age * data.colorCurveMultiplier, this.lifetime);
        return increasedAge / (float) this.lifetime;
    }

    protected void updateTraits() {
        float coeff = getCoeff();
        quadSize = Mth.lerp(coeff, data.scale1, data.scale2);
        coeff = getColorCoeff();
        float h = Mth.rotLerp(coeff, 360 * hsv1[0], 360 * hsv2[0]) / 360;
        float s = Mth.lerp(coeff, hsv1[1], hsv2[1]);
        float v = Mth.lerp(coeff, hsv1[2], hsv2[2]);
        int packed = Color.HSBtoRGB(h, s, v);
        float r = FastColor.ARGB32.red(packed) / 255.0f;
        float g = FastColor.ARGB32.green(packed) / 255.0f;
        float b = FastColor.ARGB32.blue(packed) / 255.0f;
        setColor(r, g, b);
        setAlpha(Mth.lerp(coeff, data.a1, data.a2));
        oRoll = roll;
        roll += data.spin;
    }
    
    @Override
    public void tick()
    {
        age++;
        xo = x;
        yo = y;
        zo = z;
        move(xd, yd, zd);
    
        if (phases.isEmpty())
        {
            remove();
            return;
        }
        if (!phases.get(0).isComplete)
        {
            phases.get(0).tick(this);
        }
        else
        {
            phases.remove(0);
        }
        updateTraits();
    }

    @Override
    public ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
    
}

