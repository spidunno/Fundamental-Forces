package com.sammy.fundamental_forces.core.setup.client;

import com.google.common.collect.Maps;
import com.sammy.fundamental_forces.client.particles.SimpleScreenParticleType;
import com.sammy.fundamental_forces.core.handlers.ScreenParticleHandler;
import com.sammy.fundamental_forces.core.helper.DataHelper;
import com.sammy.fundamental_forces.core.systems.rendering.particle.screen.ScreenParticleType;
import com.sammy.fundamental_forces.core.systems.rendering.particle.screen.ScreenParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;

import java.util.ArrayList;
import java.util.Comparator;

public class ScreenParticleRegistry {
    public static final ArrayList<ScreenParticleType<?>> PARTICLE_TYPES = new ArrayList<>();

    public static final ScreenParticleType<ScreenParticleOptions> WISPY_WISP = registerType(new SimpleScreenParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> WISPY_SMOKE = registerType(new SimpleScreenParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> WISPY_SPARKLE = registerType(new SimpleScreenParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> WISPY_TWINKLE = registerType(new SimpleScreenParticleType());

    public static final ScreenParticleType<ScreenParticleOptions> STAR = registerType(new SimpleScreenParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> SMOKE = registerType(new SimpleScreenParticleType());

    static {
        ScreenParticleHandler.PARTICLES = Maps.newTreeMap(Comparator.comparingInt(PARTICLE_TYPES::indexOf));
    }

    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        registerProvider(WISPY_WISP, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("wispy_wisp"))));
        registerProvider(WISPY_SMOKE, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("wispy_smoke"))));
        registerProvider(WISPY_SPARKLE, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("wispy_sparkle"))));
        registerProvider(WISPY_TWINKLE, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("wispy_twinkle"))));

        registerProvider(STAR, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("star"))));
        registerProvider(SMOKE, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("smoke"))));
    }

    public static <T extends ScreenParticleOptions> ScreenParticleType<T> registerType(ScreenParticleType<T> type) {
        PARTICLE_TYPES.add(type);
        return type;
    }

    public static <T extends ScreenParticleOptions> void registerProvider(ScreenParticleType<T> type, ScreenParticleType.ParticleProvider<T> provider) {
        type.provider = provider;
    }

    public static SpriteSet getSpriteSet(ResourceLocation resourceLocation) {
        return Minecraft.getInstance().particleEngine.spriteSets.get(resourceLocation);
    }
}