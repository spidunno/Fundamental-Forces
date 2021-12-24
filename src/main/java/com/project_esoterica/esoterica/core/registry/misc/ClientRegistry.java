package com.project_esoterica.esoterica.core.registry.misc;

import com.project_esoterica.esoterica.client.renderers.entity.bibit.BibitRenderer;
import com.project_esoterica.esoterica.client.renderers.entity.falling.FallingStarRenderer;
import com.project_esoterica.esoterica.core.registry.EntityRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistry {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.BIBIT.get(), BibitRenderer::new);
        event.registerEntityRenderer(EntityRegistry.FALLING_CRASHPOD.get(), FallingStarRenderer::new);
    }
}