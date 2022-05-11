package com.sammy.fufo.core.setup.client;

import com.mojang.blaze3d.platform.NativeImage;
import com.sammy.fufo.FufoMod;
import com.sammy.ortus.helpers.ColorHelper;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.textureloader.OrtusTextureLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.awt.*;

public class FufoTextureGrabber extends OrtusTextureLoader {

    public static void setup() {
        copyTextureWithChanges(FufoMod.prefix("fire_0"), FufoMod.prefix("block/fire_0"), new ResourceLocation("textures/block/fire_0.png"), (image) -> multiColorGradient(Easing.LINEAR, image, LUMINOUS, new Color(254, 255, 237), new Color(255, 69, 228), new Color(106, 0, 177)));
        copyTextureWithChanges(FufoMod.prefix("fire_1"), FufoMod.prefix("block/fire_1"), new ResourceLocation("textures/block/fire_1.png"), (image) -> multiColorGradient(Easing.LINEAR, image, LUMINOUS, new Color(254, 255, 237), new Color(255, 69, 228), new Color(106, 0, 177)));
    }
}