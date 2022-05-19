package com.sammy.fufo.core.setup.client;

import com.sammy.fufo.FufoMod;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.textureloader.OrtusTextureLoader;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class FufoTextureGrabber extends OrtusTextureLoader {

    public static void setup() {
        copyTextureWithChanges(FufoMod.fufoPath("fire_0"), FufoMod.fufoPath("block/fire_0"), new ResourceLocation("textures/block/fire_0.png"), (image) -> multiColorGradient(Easing.LINEAR, image, LUMINOUS, new Color(254, 255, 237), new Color(255, 69, 228), new Color(106, 0, 177)));
        copyTextureWithChanges(FufoMod.fufoPath("fire_1"), FufoMod.fufoPath("block/fire_1"), new ResourceLocation("textures/block/fire_1.png"), (image) -> multiColorGradient(Easing.LINEAR, image, LUMINOUS, new Color(254, 255, 237), new Color(255, 69, 228), new Color(106, 0, 177)));
        copyTextureWithChanges(FufoMod.fufoPath("painting_loader"), FufoMod.fufoPath("painting_target_location"), new ResourceLocation("textures/block/fire_1.png"), (image) -> image);
    }
}