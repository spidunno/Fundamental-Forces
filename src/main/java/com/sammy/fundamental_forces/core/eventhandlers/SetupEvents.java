package com.sammy.fundamental_forces.core.eventhandlers;

import com.sammy.fundamental_forces.common.capability.*;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupEvents {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        FufoWorldDataCapability.registerCapabilities(event);
        FufoChunkDataCapability.registerCapabilities(event);
        FufoEntityDataCapability.registerCapabilities(event);
        FufoItemStackCapability.registerCapabilities(event);
        FufoPlayerDataCapability.registerCapabilities(event);
    }
}
