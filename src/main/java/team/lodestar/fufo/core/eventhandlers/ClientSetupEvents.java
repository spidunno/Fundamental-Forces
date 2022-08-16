package team.lodestar.fufo.core.eventhandlers;

import team.lodestar.fufo.core.setup.client.FufoParticleRegistry;
import team.lodestar.fufo.core.setup.client.FufoPostProcessorRegistry;
import team.lodestar.fufo.core.setup.client.KeyBindingRegistry;
import team.lodestar.fufo.core.setup.content.item.ItemRegistry;
import team.lodestar.fufo.core.setup.content.magic.FireEffectTypeRegistry;
import team.lodestar.fufo.core.setup.content.worldevent.WorldEventRenderers;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        WorldEventRenderers.registerRenderers(event);
        KeyBindingRegistry.registerKeyBinding(event);
        ItemRegistry.ClientOnly.registerParticleEmitters(event);
        FireEffectTypeRegistry.ClientOnly.clientSetup(event);
        FufoPostProcessorRegistry.register();

        event.enqueueWork(() -> Minecraft.getInstance().getMainRenderTarget().enableStencil());
        //Minecraft.getInstance().getMainRenderTarget().enableStencil();

    }
    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        FufoParticleRegistry.registerParticleFactory(event);
    }
}