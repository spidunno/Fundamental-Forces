package com.sammy.fufo.core.setup.content.worldevent;

import com.sammy.fufo.client.renderers.worldevent.FallingStarfallEventRenderer;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.sammy.ortus.setup.worldevent.OrtusWorldEventRendererRegistry.registerRenderer;

@SuppressWarnings("unchecked")
public class WorldEventRenderers {

    public static void registerRenderers(FMLClientSetupEvent event) {
        registerRenderer(WorldEventTypes.FALLING_STARFALL, new FallingStarfallEventRenderer());
    }
}