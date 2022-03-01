package com.sammy.fundamental_forces.core.eventhandlers;

import com.sammy.fundamental_forces.FundamentalForcesMod;
import com.sammy.fundamental_forces.common.capability.PlayerDataCapability;
import com.sammy.fundamental_forces.core.handlers.ScreenParticleHandler;
import com.sammy.fundamental_forces.core.handlers.PlayerSpellHotbarHandler;
import com.sammy.fundamental_forces.core.handlers.RenderHandler;
import com.sammy.fundamental_forces.core.handlers.ScreenshakeHandler;
import com.sammy.fundamental_forces.core.handlers.WorldEventHandler;
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

import static com.sammy.fundamental_forces.core.setup.client.OptionsRegistry.OPTIONS;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientRuntimeEvents {

    public static boolean canSpawnParticles;

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
                ScreenshakeHandler.clientTick(camera, FundamentalForcesMod.RANDOM);
                PlayerSpellHotbarHandler.ClientOnly.clientTick(event);
                PlayerDataCapability.ClientOnly.clientTick(event);
                ScreenParticleHandler.clientTick(event);
                canSpawnParticles = true;
            }
        }
    }

    @SubscribeEvent
    public static void renderTick(TickEvent.RenderTickEvent event) {
        ScreenParticleHandler.renderParticles(event);
        canSpawnParticles = false;
    }

    @SubscribeEvent
    public static void renderLast(RenderLevelLastEvent event) {
        RenderHandler.renderLast(event);
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
    }
}