package com.sammy.fundamental_forces.core.eventhandlers;

import com.sammy.fundamental_forces.core.handlers.RenderHandler;
import com.sammy.fundamental_forces.core.setup.client.KeyBindingRegistry;
import com.sammy.fundamental_forces.core.setup.client.ParticleRegistry;
import com.sammy.fundamental_forces.core.setup.client.ScreenParticleRegistry;
import com.sammy.fundamental_forces.core.setup.content.item.ItemRegistry;
import com.sammy.fundamental_forces.core.setup.content.worldevent.WorldEventRenderers;
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
        ItemRegistry.ClientOnly.registerParticleEmitters(event);
    }
    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        ParticleRegistry.registerParticleFactory(event);
        ScreenParticleRegistry.registerParticleFactory(event);
    }
}