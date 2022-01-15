package com.project_esoterica.esoterica.core.eventhandlers;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.common.options.ScreenshakeOption;
import com.project_esoterica.esoterica.core.systems.meteorfire.MeteorFireHandler;
import com.project_esoterica.esoterica.core.systems.rendering.RenderManager;
import com.project_esoterica.esoterica.core.systems.screenshake.ScreenshakeHandler;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

import java.util.ArrayList;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientRuntimeEvents {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level != null) {
                if (minecraft.isPaused()) {
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

    @SubscribeEvent
    public static void setupScreen(ScreenEvent.InitScreenEvent.Post event) {
        final ArrayList<Option> options = new ArrayList<>();

        ScreenshakeOption.setup(event);
    }

    @SubscribeEvent
    public static void renderBlockOverlay(RenderBlockOverlayEvent event) {
    }
}