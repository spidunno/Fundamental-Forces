package com.project_esoterica.empirical_esoterica.core.registry.misc;

import com.project_esoterica.empirical_esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.empirical_esoterica.common.capability.WorldDataCapability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MiscRegistry {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(WorldDataCapability.class);
        event.register(PlayerDataCapability.class);
    }
}
