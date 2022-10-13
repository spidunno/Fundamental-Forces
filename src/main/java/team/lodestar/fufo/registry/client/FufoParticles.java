package team.lodestar.fufo.registry.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.fufo.FufoMod;
import team.lodestar.lodestone.systems.rendering.particle.type.LodestoneParticleType;

@SuppressWarnings("unused")
public class FufoParticles {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FufoMod.FUFO);

    public static RegistryObject<LodestoneParticleType> COLORED_SMOKE = PARTICLES.register("smoke", LodestoneParticleType::new);

    public static RegistryObject<LodestoneParticleType> CIRCLE = PARTICLES.register("circle", LodestoneParticleType::new);
    public static RegistryObject<LodestoneParticleType> DIAMOND = PARTICLES.register("diamond", LodestoneParticleType::new);
    public static RegistryObject<LodestoneParticleType> SQUARE = PARTICLES.register("square", LodestoneParticleType::new);

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        event.register(COLORED_SMOKE.get(), LodestoneParticleType.Factory::new);
        event.register(CIRCLE.get(), LodestoneParticleType.Factory::new);
        event.register(DIAMOND.get(), LodestoneParticleType.Factory::new);
        event.register(SQUARE.get(), LodestoneParticleType.Factory::new);
    }
}