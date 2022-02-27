package com.project_esoterica.esoterica.core.eventhandlers;

import com.project_esoterica.esoterica.core.handlers.RenderHandler;
import com.project_esoterica.esoterica.core.setup.client.KeyBindingRegistry;
import com.project_esoterica.esoterica.core.setup.client.ParticleRegistry;
import com.project_esoterica.esoterica.core.setup.client.ScreenParticleRegistry;
import com.project_esoterica.esoterica.core.setup.content.worldevent.WorldEventRenderers;
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
        RenderHandler.setupDelayedRenderer(event);
        KeyBindingRegistry.registerKeyBinding(event);
    }
    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        ParticleRegistry.registerParticleFactory(event);
        ScreenParticleRegistry.registerParticleFactory(event);
    }
}