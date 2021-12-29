package com.project_esoterica.esoterica.core.eventhandlers;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.core.systems.rendering.RenderManager;
import com.project_esoterica.esoterica.core.systems.screenshake.ScreenshakeHandler;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientRuntimeEvents {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) {
            if (Minecraft.getInstance().level != null) {
                WorldEventManager.clientWorldTick(Minecraft.getInstance().level);
                ScreenshakeHandler.clientTick(EsotericaMod.RANDOM);
            }
        }
    }
    @SubscribeEvent
    public static void onRenderLast(RenderLevelLastEvent event) {
        RenderManager.onRenderLast(event);
    }
}