package com.project_esoterica.esoterica.core.setup.client;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.client.particles.wisp.WispParticleType;
import com.project_esoterica.esoterica.client.particles.wisp.WispScreenParticleType;
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

    public static RegistryObject<WispParticleType> WISP = PARTICLES.register("wisp", WispParticleType::new);
    public static RegistryObject<WispParticleType> SMOKE = PARTICLES.register("smoke", WispParticleType::new);
    public static RegistryObject<WispParticleType> SPARKLE = PARTICLES.register("sparkle", WispParticleType::new);
    public static RegistryObject<WispParticleType> TWINKLE = PARTICLES.register("twinkle", WispParticleType::new);

    public static RegistryObject<WispScreenParticleType> SCREEN_WISP = PARTICLES.register("screen_wisp", WispScreenParticleType::new);
    public static RegistryObject<WispScreenParticleType> SCREEN_SMOKE = PARTICLES.register("screen_smoke", WispScreenParticleType::new);
    public static RegistryObject<WispScreenParticleType> SCREEN_SPARKLE = PARTICLES.register("screen_sparkle", WispScreenParticleType::new);
    public static RegistryObject<WispScreenParticleType> SCREEN_TWINKLE = PARTICLES.register("screen_twinkle", WispScreenParticleType::new);

    public static RegistryObject<WispParticleType> SQUARE = PARTICLES.register("square", WispParticleType::new);
    public static RegistryObject<WispParticleType> DIAMOND = PARTICLES.register("diamond", WispParticleType::new);
    public static RegistryObject<WispParticleType> CIRCLE = PARTICLES.register("circle", WispParticleType::new);

    @SubscribeEvent
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(WISP.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SMOKE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SPARKLE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(TWINKLE.get(), WispParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(SCREEN_WISP.get(), WispScreenParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SCREEN_SMOKE.get(), WispScreenParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SCREEN_SPARKLE.get(), WispScreenParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SCREEN_TWINKLE.get(), WispScreenParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(SQUARE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(DIAMOND.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(CIRCLE.get(), WispParticleType.Factory::new);
    }
}