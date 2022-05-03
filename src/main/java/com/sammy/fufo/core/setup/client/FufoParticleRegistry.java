package com.sammy.fufo.core.setup.client;

import com.sammy.fufo.FufoMod;
import com.sammy.ortus.OrtusLib;
import com.sammy.ortus.systems.rendering.particle.type.OrtusParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class FufoParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FufoMod.FUFO);

    public static RegistryObject<OrtusParticleType> COLORED_SMOKE = PARTICLES.register("smoke", OrtusParticleType::new);

    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(COLORED_SMOKE.get(), OrtusParticleType.Factory::new);
    }
}