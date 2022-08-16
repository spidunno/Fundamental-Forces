package team.lodestar.fufo.core.setup.content.worldevent;

import team.lodestar.fufo.client.renderers.worldevent.FallingStarfallEventRenderer;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static team.lodestar.lodestone.setup.worldevent.LodestoneWorldEventRendererRegistry.registerRenderer;

@SuppressWarnings("unchecked")
public class WorldEventRenderers {

    public static void registerRenderers(FMLClientSetupEvent event) {
        registerRenderer(WorldEventTypes.FALLING_STARFALL, new FallingStarfallEventRenderer());
    }
}