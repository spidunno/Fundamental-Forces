package team.lodestar.fufo.unsorted.eventhandlers;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import team.lodestar.fufo.registry.client.FufoKeybinds;
import team.lodestar.fufo.registry.client.FufoParticles;
import team.lodestar.fufo.registry.client.FufoPostProcessingEffects;
import team.lodestar.fufo.registry.client.FufoWorldEventRenderers;
import team.lodestar.fufo.registry.common.magic.FufoFireEffects;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        FufoWorldEventRenderers.registerRenderers(event);
        FufoKeybinds.registerKeyBinding(event);
        FufoFireEffects.ClientOnly.clientSetup(event);
        FufoPostProcessingEffects.register();

        event.enqueueWork(() -> Minecraft.getInstance().getMainRenderTarget().enableStencil());
        //Minecraft.getInstance().getMainRenderTarget().enableStencil();

    }

    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        FufoParticles.registerParticleFactory(event);
    }
}