package com.sammy.fundamental_forces.core.setup.client;

import com.sammy.fundamental_forces.FundamentalForcesMod;
import com.sammy.fundamental_forces.client.particles.SimpleParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegistry
{
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FundamentalForcesMod.MODID);

    public static RegistryObject<SimpleParticleType> WISPY_WISP = PARTICLES.register("wispy_wisp", SimpleParticleType::new);
    public static RegistryObject<SimpleParticleType> WISPY_SMOKE = PARTICLES.register("wispy_smoke", SimpleParticleType::new);
    public static RegistryObject<SimpleParticleType> WISPY_SPARKLE = PARTICLES.register("wispy_sparkle", SimpleParticleType::new);
    public static RegistryObject<SimpleParticleType> WISPY_TWINKLE = PARTICLES.register("wispy_twinkle", SimpleParticleType::new);

    public static RegistryObject<SimpleParticleType> STAR = PARTICLES.register("star", SimpleParticleType::new);
    public static RegistryObject<SimpleParticleType> SMOKE = PARTICLES.register("smoke", SimpleParticleType::new);

    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(WISPY_WISP.get(), SimpleParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(WISPY_SMOKE.get(), SimpleParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(WISPY_SPARKLE.get(), SimpleParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(WISPY_TWINKLE.get(), SimpleParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(STAR.get(), SimpleParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SMOKE.get(), SimpleParticleType.Factory::new);
    }
}