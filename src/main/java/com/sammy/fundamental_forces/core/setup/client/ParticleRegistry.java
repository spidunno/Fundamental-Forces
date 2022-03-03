package com.sammy.fundamental_forces.core.setup.client;

import com.sammy.fundamental_forces.FundamentalForcesMod;
import com.sammy.fundamental_forces.client.particles.textured.AnimatedParticleType;
import com.sammy.fundamental_forces.client.particles.wisp.WispParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegistry
{
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FundamentalForcesMod.MODID);

    public static RegistryObject<WispParticleType> WISP = PARTICLES.register("wisp", WispParticleType::new);
    public static RegistryObject<WispParticleType> SMOKE = PARTICLES.register("smoke", WispParticleType::new);
    public static RegistryObject<WispParticleType> SPARKLE = PARTICLES.register("sparkle", WispParticleType::new);
    public static RegistryObject<WispParticleType> TWINKLE = PARTICLES.register("twinkle", WispParticleType::new);

    public static RegistryObject<AnimatedParticleType> STAR = PARTICLES.register("star", AnimatedParticleType::new);

    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(WISP.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SMOKE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SPARKLE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(TWINKLE.get(), WispParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(STAR.get(), AnimatedParticleType.Factory::new);
    }
}