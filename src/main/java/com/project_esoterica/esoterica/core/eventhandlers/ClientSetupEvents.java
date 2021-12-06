package com.project_esoterica.esoterica.core.eventhandlers;

import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.common.worldevents.starfall.FallingStarfallEventRenderer;
import com.project_esoterica.esoterica.core.registry.worldevent.WorldEventRenderers;
import com.project_esoterica.esoterica.core.systems.rendering.RenderManager;
import com.project_esoterica.esoterica.core.systems.screenshake.ScreenshakeHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {
    @SubscribeEvent
    public static void clientTick(FMLClientSetupEvent event) {
        WorldEventRenderers.registerRenderers(event);
    }
}