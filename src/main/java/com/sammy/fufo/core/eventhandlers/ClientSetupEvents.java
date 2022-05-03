package com.sammy.fufo.core.eventhandlers;

import com.sammy.fufo.core.setup.client.FufoParticleRegistry;
import com.sammy.fufo.core.setup.client.KeyBindingRegistry;
import com.sammy.fufo.core.setup.content.item.ItemRegistry;
import com.sammy.fufo.core.setup.content.magic.FireEffectTypeRegistry;
import com.sammy.fufo.core.setup.content.worldevent.WorldEventRenderers;
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
    }
    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        FufoParticleRegistry.registerParticleFactory(event);
    }
}