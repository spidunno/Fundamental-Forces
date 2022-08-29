package team.lodestar.fufo.registry.client;

import team.lodestar.fufo.client.rendering.worldevent.FallingStarfallEventRenderer;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import team.lodestar.fufo.registry.common.worldevent.FufoWorldEventTypes;

import static team.lodestar.lodestone.setup.worldevent.LodestoneWorldEventRendererRegistry.registerRenderer;

public class FufoWorldEventRenderers {

    public static void registerRenderers(FMLClientSetupEvent event) {
        registerRenderer(FufoWorldEventTypes.FALLING_STARFALL, new FallingStarfallEventRenderer());
    }
}