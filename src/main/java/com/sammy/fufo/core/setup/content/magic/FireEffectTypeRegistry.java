package com.sammy.fufo.core.setup.content.magic;


import com.sammy.fufo.client.renderers.fire.MeteorFireEffectRenderer;
import com.sammy.ortus.setup.OrtusFireEffectRendererRegistry;
import com.sammy.ortus.systems.fireeffect.FireEffectType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.sammy.ortus.setup.OrtusFireEffectRegistry.registerType;

public class FireEffectTypeRegistry {
    public static final FireEffectType METEOR_FIRE = registerType(new FireEffectType("meteor_fire", 1, 20));
    public static final FireEffectType GREATER_FIRE = registerType(new FireEffectType("greater_fire", 1, 10));

    public static class ClientOnly {
        public static void clientSetup(FMLClientSetupEvent event) {
            OrtusFireEffectRendererRegistry.registerRenderer(METEOR_FIRE, new MeteorFireEffectRenderer());
        }
    }
}