package team.lodestar.fufo.registry.common.magic;


import team.lodestar.fufo.client.rendering.fire.MeteorFireEffectRenderer;
import team.lodestar.lodestone.setup.LodestoneFireEffectRendererRegistry;
import team.lodestar.lodestone.systems.fireeffect.FireEffectType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static team.lodestar.lodestone.setup.LodestoneFireEffectRegistry.registerType;

public class FufoFireEffects {
    public static final FireEffectType METEOR_FIRE = registerType(new FireEffectType("meteor_fire", 1, 20));
    public static final FireEffectType GREATER_FIRE = registerType(new FireEffectType("greater_fire", 1, 10));

    public static class ClientOnly {
        public static void clientSetup(FMLClientSetupEvent event) {
            LodestoneFireEffectRendererRegistry.registerRenderer(METEOR_FIRE, new MeteorFireEffectRenderer());
        }
    }
}