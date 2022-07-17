package com.sammy.fufo.core.setup.client;

import com.sammy.fufo.client.renderers.postprocess.EnergyScanPostProcessor;
import com.sammy.fufo.client.renderers.postprocess.EnergySpherePostProcessor;
import com.sammy.ortus.systems.postprocess.PostProcessHandler;

public class FufoPostProcessorRegistry {
    public static final EnergyScanPostProcessor ENERGY_SCAN = new EnergyScanPostProcessor();
    public static final EnergySpherePostProcessor ENERGY_SPHERE = new EnergySpherePostProcessor();
//    public static final EdgePostProcessor EDGE = new EdgePostProcessor();

    public static void register() {
        PostProcessHandler.addInstance(ENERGY_SCAN);
        PostProcessHandler.addInstance(ENERGY_SPHERE);
//        PostProcessHandler.addInstance(EDGE);
    }
}
