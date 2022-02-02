package com.project_esoterica.esoterica.core.setup.client;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.client.particles.wisp.WispParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleRegistry
{
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EsotericaMod.MODID);

    public static RegistryObject<WispParticleType> WISP_PARTICLE = PARTICLES.register("wisp_particle", WispParticleType::new);
    public static RegistryObject<WispParticleType> SMOKE_PARTICLE = PARTICLES.register("smoke_particle", WispParticleType::new);
    public static RegistryObject<WispParticleType> SPARKLE_PARTICLE = PARTICLES.register("sparkle_particle", WispParticleType::new);
    public static RegistryObject<WispParticleType> TWINKLE_PARTICLE = PARTICLES.register("twinkle_particle", WispParticleType::new);

    @SubscribeEvent
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(WISP_PARTICLE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SMOKE_PARTICLE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SPARKLE_PARTICLE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(TWINKLE_PARTICLE.get(), WispParticleType.Factory::new);
    }
}