package com.sammy.fundamental_forces.core.systems.rendering.particle.world;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.fundamental_forces.config.ClientConfig;
import com.sammy.fundamental_forces.core.handlers.RenderHandler;
import com.sammy.fundamental_forces.core.systems.rendering.RenderTypes;
import com.sammy.fundamental_forces.core.systems.rendering.particle.SimpleParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

import java.awt.*;

public class GenericParticle extends TextureSheetParticle {
    protected WorldParticleOptions data;
    private final ParticleRenderType renderType;
    protected final ParticleEngine.MutableSpriteSet spriteSet;
    float[] hsv1 = new float[3], hsv2 = new float[3];

    public GenericParticle(ClientLevel world, WorldParticleOptions data, ParticleEngine.MutableSpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd) {
        super(world, x, y, z);
        this.data = data;
        this.renderType = data.renderType;
        this.spriteSet = spriteSet;
        this.roll = data.spinOffset + data.spin1;
        if (!data.forcedMotion) {
            this.xd = xd;
            this.yd = yd;
            this.zd = zd;
        }
        this.setLifetime(data.lifetime);
        this.gravity = data.gravity ? 1 : 0;
        this.hasPhysics = !data.noClip;
        this.friction = 1;
        Color.RGBtoHSB((int) (255 * Math.min(1.0f, data.r1)), (int) (255 * Math.min(1.0f, data.g1)), (int) (255 * Math.min(1.0f, data.b1)), hsv1);
        Color.RGBtoHSB((int) (255 * Math.min(1.0f, data.r2)), (int) (255 * Math.min(1.0f, data.g2)), (int) (255 * Math.min(1.0f, data.b2)), hsv2);
        updateTraits();
        if (getAnimator().equals(SimpleParticleOptions.Animator.RANDOM_SPRITE)) {
            pickSprite(spriteSet);
        }
        if (getAnimator().equals(SimpleParticleOptions.Animator.NONE_OR_CUSTOM) || getAnimator().equals(SimpleParticleOptions.Animator.WITH_AGE)) {
            pickSprite(0);
        }
        updateTraits();
    }

    public SimpleParticleOptions.Animator getAnimator() {
        return data.animator;
    }

    public void pickSprite(int spriteIndex) {
        if (spriteIndex < spriteSet.sprites.size() && spriteIndex >= 0) {
            setSprite(spriteSet.sprites.get(spriteIndex));
        }
    }

    public void pickColor(float colorCoeff) {
        float h = Mth.rotLerp(colorCoeff, 360f * hsv1[0], 360f * hsv2[0]) / 360f;
        float s = Mth.lerp(colorCoeff, hsv1[1], hsv2[1]);
        float v = Mth.lerp(colorCoeff, hsv1[2], hsv2[2]);
        int packed = Color.HSBtoRGB(h, s, v);
        float r = FastColor.ARGB32.red(packed) / 255.0f;
        float g = FastColor.ARGB32.green(packed) / 255.0f;
        float b = FastColor.ARGB32.blue(packed) / 255.0f;
        setColor(r, g, b);
    }

    protected void updateTraits() {
        pickColor(data.colorCurveEasing.ease(age * data.colorCurveMultiplier, 0, 1, lifetime));
        if (data.isTrinaryScale()) {
            float trinaryAge = Math.min(1, age * data.scaleCurveMultiplier / lifetime);
            if (trinaryAge >= 0.5f) {
                quadSize = Mth.lerp(data.scaleCurveEndEasing.ease((trinaryAge - 0.5f), 1, 0, 0.5f), data.scale2, data.scale3);
            } else {
                quadSize = Mth.lerp(data.scaleCurveStartEasing.ease(trinaryAge, 0, 1, 0.5f), data.scale1, data.scale2);
            }
        } else {
            quadSize = Mth.lerp(data.scaleCurveStartEasing.ease(Math.min(lifetime, age * data.scaleCurveMultiplier), 0, 1, data.lifetime), data.scale1, data.scale2);
        }
        if (data.isTrinaryAlpha()) {
            float trinaryAge = Math.min(1, age * data.alphaCurveMultiplier / lifetime);
            if (trinaryAge >= 0.5f) {
                alpha = Mth.lerp(data.alphaCurveEndEasing.ease((trinaryAge - 0.5f), 1, 0, 0.5f), data.alpha2, data.alpha3);
            } else {
                alpha = Mth.lerp(data.alphaCurveStartEasing.ease(trinaryAge, 0, 1, 0.5f), data.alpha1, data.alpha2);
            }
        } else {
            alpha = Mth.lerp(data.alphaCurveStartEasing.ease(Math.min(lifetime, age * data.alphaCurveMultiplier), 0, 1, data.lifetime), data.alpha1, data.alpha2);
        }
        oRoll = roll;
        roll += data.spinOffset + Mth.lerp(data.spinEasing.ease(Math.min(lifetime, age * data.spinCurveMultiplier), 0, 1, data.lifetime), data.spin1, data.spin2);
        if (data.forcedMotion) {
            float motionAge = Math.min(lifetime, age * data.motionCurveMultiplier);
            xd = Mth.lerp(data.motionEasing.ease(motionAge, 0, 1, data.lifetime), data.startingMotion.x(), data.endingMotion.x());
            yd = Mth.lerp(data.motionEasing.ease(motionAge, 0, 1, data.lifetime), data.startingMotion.y(), data.endingMotion.y());
            zd = Mth.lerp(data.motionEasing.ease(motionAge, 0, 1, data.lifetime), data.startingMotion.z(), data.endingMotion.z());
        } else {
            xd *= data.motionCurveMultiplier;
            yd *= data.motionCurveMultiplier;
            zd *= data.motionCurveMultiplier;
        }
    }

    @Override
    public void tick() {
        updateTraits();
        if (data.animator.equals(SimpleParticleOptions.Animator.WITH_AGE)) {
            setSpriteFromAge(spriteSet);
        }
        super.tick();
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        super.render(ClientConfig.DELAYED_PARTICLE_RENDERING.get() ? RenderHandler.DELAYED_RENDER.getBuffer(RenderTypes.ADDITIVE_PARTICLE) : consumer, camera, partialTicks);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return renderType;
    }
}