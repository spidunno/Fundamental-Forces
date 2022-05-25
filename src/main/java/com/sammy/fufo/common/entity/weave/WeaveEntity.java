package com.sammy.fufo.common.entity.weave;

import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.Random;

import static net.minecraft.util.Mth.nextFloat;

public class WeaveEntity extends AbstractWeaveEntity{
    public WeaveEntity(EntityType<?> entity, Level level) {
        super(entity, level);
    }

    public WeaveEntity(Level level) {
        super(EntityRegistry.BASIC_WEAVE.get(),level);
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (weave != null) {
                weave.getBindables().forEach(b -> {
                    Vec3i offset = b.getLocation();
                    Random rand = level.getRandom();
                    ParticleBuilders.create(OrtusParticleRegistry.STAR_PARTICLE)
                            .setAlpha(0.05f, 0.15f, 0f)
                            .setAlphaEasing(Easing.QUINTIC_IN, Easing.QUINTIC_OUT)
                            .setLifetime(15 + rand.nextInt(4))
                            .setSpin(nextFloat(rand, 0.01f, 0.05f), 0.02f)
                            .setScale(0.05f, 0.35f + rand.nextFloat() * 0.15f, 0.2f)
                            .setScaleEasing(Easing.SINE_IN, Easing.SINE_OUT)
                            .setColor(new Color(250, 215, 100), new Color(169, 14, 92))
                            .setColorCoefficient(0.5f)
                            .enableNoClip()
                            .repeat(level, position().x() + offset.getX(), position().y() + offset.getY() + 0.45f, position().z() + offset.getZ(), 1);

                    ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
                            .setAlpha(0.01f, 0.08f, 0f)
                            .setAlphaEasing(Easing.QUINTIC_IN, Easing.QUINTIC_OUT)
                            .setLifetime(15 + rand.nextInt(4))
                            .setSpin(nextFloat(rand, 0.05f, 0.1f), 0.2f)
                            .setScale(0.05f, 0.15f + rand.nextFloat() * 0.1f, 0)
                            .setScaleEasing(Easing.SINE_IN, Easing.SINE_OUT)
                            .setColor(new Color(255, 187, 132), new Color(84, 40, 215))
                            .setColorCoefficient(0.5f)
                            .randomOffset(0.04f)
                            .enableNoClip()
                            .randomMotion(0.005f, 0.005f)
                            .repeat(level, position().x() + offset.getX(), position().y() + offset.getY() + 0.45f, position().z() + offset.getZ(), 1);
                });
            }
        }
        super.tick();
    }
}
