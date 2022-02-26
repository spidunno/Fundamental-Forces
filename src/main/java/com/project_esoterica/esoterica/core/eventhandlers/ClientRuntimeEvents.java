package com.project_esoterica.esoterica.core.eventhandlers;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.esoterica.core.handlers.ScreenParticleHandler;
import com.project_esoterica.esoterica.core.handlers.PlayerSpellHotbarHandler;
import com.project_esoterica.esoterica.core.systems.rendering.RenderManager;
import com.project_esoterica.esoterica.core.handlers.ScreenshakeHandler;
import com.project_esoterica.esoterica.core.handlers.WorldEventHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.project_esoterica.esoterica.core.setup.client.OptionsRegistry.OPTIONS;

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
                WorldEventHandler.clientWorldTick(minecraft.level);
                ScreenshakeHandler.clientTick(camera, EsotericaMod.RANDOM);
                PlayerSpellHotbarHandler.ClientOnly.clientTick(event);
                PlayerDataCapability.ClientOnly.clientTick(event);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderLast(RenderLevelLastEvent event) {
        RenderManager.onRenderLast(event);
    }

    @SuppressWarnings("ALL")
    @SubscribeEvent
    public static void setupScreen(ScreenEvent.InitScreenEvent.Post event) {
        if (event.getScreen() instanceof SimpleOptionsSubScreen subScreen) {
            subScreen.list.addSmall(OPTIONS.stream().filter(e -> e.canAdd(event)).toArray(Option[]::new));
        }
    }

    @SubscribeEvent
    public static void renderBlockOverlay(RenderBlockOverlayEvent event) {
    }

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Pre event) {
        PlayerSpellHotbarHandler.ClientOnly.moveOverlays(event);
    }

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Post event) {
        PlayerSpellHotbarHandler.ClientOnly.renderSpellHotbar(event);
        ScreenParticleHandler.renderParticles(event);
    }
}