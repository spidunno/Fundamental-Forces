package team.lodestar.fufo.core.setup.client;

import team.lodestar.fufo.client.renderers.postprocess.*;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;
import team.lodestar.fufo.client.renderers.postprocess.*;

public class FufoPostProcessorRegistry {
    public static final NormalMapGenerator NORMAL = new NormalMapGenerator();
    public static final WorldHighlightPostProcessor WORLD_HIGHLIGHT = new WorldHighlightPostProcessor();
    public static final ImpactFramePostProcessor IMPACT_FRAME = new ImpactFramePostProcessor();
    public static final EnergyScanPostProcessor ENERGY_SCAN = new EnergyScanPostProcessor();
    public static final EnergySpherePostProcessor ENERGY_SPHERE = new EnergySpherePostProcessor();

    public static void register() {
        PostProcessHandler.addInstance(NORMAL);
        PostProcessHandler.addInstance(WORLD_HIGHLIGHT);
        PostProcessHandler.addInstance(IMPACT_FRAME);
        PostProcessHandler.addInstance(ENERGY_SCAN);
        PostProcessHandler.addInstance(ENERGY_SPHERE);
    }
}
