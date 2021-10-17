package com.project_esoterica.esoterica.core.eventhandlers;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.core.systems.screenshake.ScreenshakeHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientRuntimeEvents {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        ScreenshakeHandler.clientTick(EsotericaMod.RANDOM);
    }
}