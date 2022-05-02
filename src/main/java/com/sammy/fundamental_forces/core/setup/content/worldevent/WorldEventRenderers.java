package com.sammy.fundamental_forces.core.setup.content.worldevent;

import com.sammy.fundamental_forces.client.renderers.worldevent.FallingStarfallEventRenderer;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.sammy.ortus.setup.worldevent.OrtusWorldEventRendererRegistry.registerRenderer;

@SuppressWarnings("unchecked")
public class WorldEventRenderers {

    public static void registerRenderers(FMLClientSetupEvent event) {
        registerRenderer(WorldEventTypes.FALLING_STARFALL, new FallingStarfallEventRenderer());
    }
}