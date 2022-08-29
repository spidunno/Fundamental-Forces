package team.lodestar.fufo.registry.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterTextureAtlasSpriteLoadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.lodestar.fufo.FufoMod;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.textureloader.LodestoneTextureLoader;

import java.awt.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FufoTextureLoaders extends LodestoneTextureLoader {

    @SubscribeEvent
    public static void setup(RegisterTextureAtlasSpriteLoadersEvent event) {
        registerTextureLoader("fire_0", FufoMod.fufoPath("block/fire_0"), new ResourceLocation("textures/block/fire_0.png"), (image) -> applyMultiColorGradient(Easing.LINEAR, image, LUMINOUS, new Color(254, 255, 237), new Color(255, 69, 228), new Color(106, 0, 177)), event);
        registerTextureLoader("fire_1", FufoMod.fufoPath("block/fire_1"), new ResourceLocation("textures/block/fire_1.png"), (image) -> applyMultiColorGradient(Easing.LINEAR, image, LUMINOUS, new Color(254, 255, 237), new Color(255, 69, 228), new Color(106, 0, 177)), event);
    }
}