package com.project_esoterica.esoterica.core.setup;

import com.project_esoterica.esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.esoterica.common.capability.WorldDataCapability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityRegistry {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(WorldDataCapability.class);
        event.register(PlayerDataCapability.class);
    }
}