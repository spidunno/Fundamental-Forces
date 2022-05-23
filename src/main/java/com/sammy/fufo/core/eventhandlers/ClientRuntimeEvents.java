package com.sammy.fufo.core.eventhandlers;

import com.sammy.fufo.client.renderers.entity.falling.FallingStarRenderer;
import com.sammy.fufo.core.handlers.PlayerSpellHotbarHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
                PlayerSpellHotbarHandler.ClientOnly.clientTick(event);
            }
        }
    }

    @SubscribeEvent
    public static void renderTick(TickEvent.RenderTickEvent event) {
        FallingStarRenderer.TextureSurgeon.renderTick(event);
    }

    @SubscribeEvent
    public static void renderLast(RenderLevelLastEvent event) {
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