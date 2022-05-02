package com.sammy.fufo.core.setup.client;

import com.sammy.fufo.FufoMod;
import com.sammy.ortus.systems.textureloader.OrtusTextureLoader;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class FufoTextureGrabber extends OrtusTextureLoader {

    public static void setup() {
        copyTextureWithChanges(FufoMod.prefix("fire_0"), FufoMod.prefix("block/fire_0"), new ResourceLocation("textures/block/fire_0.png"), (image) -> multiColorGradient(image, LUMINOUS, new Color(255, 255, 255), new Color(255, 0, 255), new Color(100, 25, 200)));
        copyTextureWithChanges(FufoMod.prefix("fire_1"), FufoMod.prefix("block/fire_1"), new ResourceLocation("textures/block/fire_1.png"), (image) -> multiColorGradient(image, LUMINOUS, new Color(255, 255, 255), new Color(255, 0, 255), new Color(100, 25, 200)));
    }
}