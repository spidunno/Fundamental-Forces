package com.sammy.fundamental_forces.core.setup.client;

import com.google.common.collect.Maps;
import com.sammy.fundamental_forces.client.particles.wisp.WispScreenParticleType;
import com.sammy.fundamental_forces.core.handlers.ScreenParticleHandler;
import com.sammy.fundamental_forces.core.helper.DataHelper;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.ScreenParticleType;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.options.ScreenParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;

import java.util.ArrayList;
import java.util.Comparator;

public class ScreenParticleRegistry {
    public static final ArrayList<ScreenParticleType<?>> PARTICLE_TYPES = new ArrayList<>();

    public static final ScreenParticleType<ScreenParticleOptions> WISP = registerType(new WispScreenParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> SMOKE = registerType(new WispScreenParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> SPARKLE = registerType(new WispScreenParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> TWINKLE = registerType(new WispScreenParticleType());

    static {
        ScreenParticleHandler.PARTICLES = Maps.newTreeMap(Comparator.comparingInt(PARTICLE_TYPES::indexOf));
    }

    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        registerProvider(WISP, new WispScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("wisp"))));
        registerProvider(SMOKE, new WispScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("smoke"))));
        registerProvider(SPARKLE, new WispScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("sparkle"))));
        registerProvider(TWINKLE, new WispScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("twinkle"))));
    }

    public static <T extends ScreenParticleOptions> ScreenParticleType<T> registerType(ScreenParticleType<T> type) {
        PARTICLE_TYPES.add(type);
        return type;
    }

    public static <T extends ScreenParticleOptions> void registerProvider(ScreenParticleType<T> type, ScreenParticleType.ParticleProvider<T> provider) {
        type.provider = provider;
    }
    public static SpriteSet getSpriteSet(ResourceLocation resourceLocation)
    {
        return Minecraft.getInstance().particleEngine.spriteSets.get(resourceLocation);
    }
}