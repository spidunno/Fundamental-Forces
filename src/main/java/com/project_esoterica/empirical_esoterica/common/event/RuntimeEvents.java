package com.project_esoterica.empirical_esoterica.common.event;

import com.project_esoterica.empirical_esoterica.common.capability.WorldDataCapability;
import com.project_esoterica.empirical_esoterica.core.systems.capability.SimpleCapabilityProvider;
import com.project_esoterica.empirical_esoterica.EsotericHelper;
import com.project_esoterica.empirical_esoterica.common.capability.PlayerDataCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RuntimeEvents {

    @SubscribeEvent
    public static void attachWorldCapability(AttachCapabilitiesEvent<Level> event) {
        final WorldDataCapability capability = new WorldDataCapability();
        event.addCapability(EsotericHelper.prefix("world_data"), new SimpleCapabilityProvider<>(WorldDataCapability.CAPABILITY, () -> capability));
    }

    @SubscribeEvent
    public static void attachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            final PlayerDataCapability capability = new PlayerDataCapability();
            event.addCapability(EsotericHelper.prefix("player_data"), new SimpleCapabilityProvider<>(PlayerDataCapability.CAPABILITY, () -> capability));
        }
    }
}
