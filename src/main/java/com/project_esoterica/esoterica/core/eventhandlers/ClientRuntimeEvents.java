package com.project_esoterica.esoterica.core.eventhandlers;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.core.systems.rendering.RenderManager;
import com.project_esoterica.esoterica.core.systems.screenshake.ScreenshakeHandler;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientRuntimeEvents {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level != null) {
                if (minecraft.isPaused())
                {
                    return;
                }
                Camera camera = minecraft.gameRenderer.getMainCamera();
                WorldEventManager.clientWorldTick(minecraft.level);
                ScreenshakeHandler.clientTick(camera, EsotericaMod.RANDOM);
            }
        }
    }
    @SubscribeEvent
    public static void onRenderLast(RenderLevelLastEvent event) {
        RenderManager.onRenderLast(event);
    }
}