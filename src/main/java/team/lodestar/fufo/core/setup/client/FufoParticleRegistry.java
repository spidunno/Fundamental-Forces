package team.lodestar.fufo.core.setup.client;

import team.lodestar.fufo.FufoMod;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.rendering.particle.type.LodestoneParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class FufoParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FufoMod.FUFO);

    public static RegistryObject<LodestoneParticleType> COLORED_SMOKE = PARTICLES.register("smoke", LodestoneParticleType::new);

    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(COLORED_SMOKE.get(), LodestoneParticleType.Factory::new);
    }
}