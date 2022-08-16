package team.lodestar.fufo.registry.client;

import team.lodestar.fufo.FufoMod;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.textureloader.LodestoneTextureLoader;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class FufoTextureLoaders extends LodestoneTextureLoader {

    public static void setup() {
        registerTextureLoader(FufoMod.fufoPath("fire_0"), FufoMod.fufoPath("block/fire_0"), new ResourceLocation("textures/block/fire_0.png"), (image) -> applyMultiColorGradient(Easing.LINEAR, image, LUMINOUS, new Color(254, 255, 237), new Color(255, 69, 228), new Color(106, 0, 177)));
        registerTextureLoader(FufoMod.fufoPath("fire_1"), FufoMod.fufoPath("block/fire_1"), new ResourceLocation("textures/block/fire_1.png"), (image) -> applyMultiColorGradient(Easing.LINEAR, image, LUMINOUS, new Color(254, 255, 237), new Color(255, 69, 228), new Color(106, 0, 177)));
    }
}