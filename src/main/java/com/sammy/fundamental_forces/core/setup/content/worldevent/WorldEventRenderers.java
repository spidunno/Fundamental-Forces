package com.sammy.fundamental_forces.core.setup.content.worldevent;

import com.sammy.fundamental_forces.client.renderers.worldevent.FallingStarfallEventRenderer;
import com.sammy.fundamental_forces.core.systems.worldevent.WorldEventInstance;
import com.sammy.fundamental_forces.core.systems.worldevent.WorldEventRenderer;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class WorldEventRenderers {
    public static HashMap<String, WorldEventRenderer<WorldEventInstance>> RENDERERS = new HashMap<>();

    public static void registerRenderers(FMLClientSetupEvent event)
    {
        registerRenderer(WorldEventTypes.FALLING_STARFALL.id, new FallingStarfallEventRenderer());
    }

    private static void registerRenderer(String id, WorldEventRenderer renderer) {
        RENDERERS.put(id, renderer);
    }
}
