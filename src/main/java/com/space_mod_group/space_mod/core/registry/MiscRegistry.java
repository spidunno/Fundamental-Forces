package com.space_mod_group.space_mod.core.registry;

import com.google.common.eventbus.Subscribe;
import com.space_mod_group.space_mod.common.capability.PlayerDataCapability;
import com.space_mod_group.space_mod.common.capability.WorldDataCapability;
import net.minecraft.world.level.storage.WorldData;
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
