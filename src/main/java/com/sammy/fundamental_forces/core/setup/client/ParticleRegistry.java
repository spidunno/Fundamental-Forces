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

    public static RegistryObject<SimpleParticleType> WISP = PARTICLES.register("wisp", SimpleParticleType::new);
    public static RegistryObject<SimpleParticleType> SMOKE = PARTICLES.register("smoke", SimpleParticleType::new);
    public static RegistryObject<SimpleParticleType> SPARKLE = PARTICLES.register("sparkle", SimpleParticleType::new);
    public static RegistryObject<SimpleParticleType> TWINKLE = PARTICLES.register("twinkle", SimpleParticleType::new);
    public static RegistryObject<SimpleParticleType> STAR = PARTICLES.register("star", SimpleParticleType::new);

    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(WISP.get(), SimpleParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SMOKE.get(), SimpleParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SPARKLE.get(), SimpleParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(TWINKLE.get(), SimpleParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(STAR.get(), SimpleParticleType.Factory::new);
    }
}