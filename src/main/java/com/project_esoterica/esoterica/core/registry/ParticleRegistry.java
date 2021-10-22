package com.project_esoterica.esoterica.core.registry;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.client.particles.wisp.WispParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleRegistry
{
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EsotericaMod.MOD_ID);

    public static RegistryObject<WispParticleType> WISP_PARTICLE = PARTICLES.register("wisp_particle", WispParticleType::new);
    
    @SubscribeEvent
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event)
    {
        Minecraft.getInstance().particleEngine.register(WISP_PARTICLE.get(), WispParticleType.Factory::new);
    }
}