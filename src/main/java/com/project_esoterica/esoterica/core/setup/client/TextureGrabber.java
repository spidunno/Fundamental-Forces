package com.project_esoterica.esoterica.core.setup.client;

import com.mojang.blaze3d.platform.NativeImage;
import com.project_esoterica.esoterica.core.helper.ColorHelper;
import com.project_esoterica.esoterica.core.helper.DataHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.client.MinecraftForgeClient;

import java.awt.*;
import java.io.IOException;

//https://github.com/Tamaized/Frostfell/blob/1.18/src/main/java/tamaized/frostfell/client/ClientListener.java#L23-L57
public class TextureGrabber {

    public static void setup() {
        registerGrabber("fire_0", "textures/block/fire_0.png", (image)->colorGradient(image, new Color(251, 209, 240), new Color(187, 97, 249), new Color(49, 155, 255)));
        registerGrabber("fire_1", "textures/block/fire_1.png", (image)->colorGradient(image, new Color(251, 209, 240), new Color(187, 97, 249), new Color(49, 155, 255)));
    }
    public static void registerGrabber(String loaderName, String sourcePath, TextureModifier modifier) {
        MinecraftForgeClient.registerTextureAtlasSpriteLoader(DataHelper.prefix(loaderName), (atlas, resourceManager, textureInfo, resource, atlasWidth, atlasHeight, spriteX, spriteY, mipmapLevel, image) -> {
            Resource r = null;
            try {
                r = resourceManager.getResource(new ResourceLocation(sourcePath));
                image = modifier.modifyTexture(NativeImage.read(r.getInputStream()));
            } catch (Throwable throwable1) {
                if (r != null) {
                    try {
                        r.close();
                    } catch (Throwable throwable) {
                        throwable1.addSuppressed(throwable);
                    }
                }

                throwable1.printStackTrace();
            }
            if (r != null) {
                try {
                    r.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new TextureAtlasSprite(atlas, textureInfo, mipmapLevel, atlasWidth, atlasHeight, spriteX, spriteY, image) {
            };
        });
    }
    public static NativeImage grayscale(NativeImage nativeimage)
    {
        for (int x = 0; x < nativeimage.getWidth(); x++) {
            for (int y = 0; y < nativeimage.getHeight(); y++) {
                int pixel = nativeimage.getPixelRGBA(x, y);
                int L = (int) (0.299D * ((pixel) & 0xFF) + 0.587D * ((pixel >> 8) & 0xFF) + 0.114D * ((pixel >> 16) & 0xFF));
                nativeimage.setPixelRGBA(x, y, NativeImage.combine((pixel >> 24) & 0xFF, L, L, L));
            }
        }
        return nativeimage;
    }
    public static NativeImage colorGradient(NativeImage nativeimage, Color brightColor, Color darkColor)
    {
        for (int x = 0; x < nativeimage.getWidth(); x++) {
            for (int y = 0; y < nativeimage.getHeight(); y++) {
                int pixel = nativeimage.getPixelRGBA(x, y);
                int L = (int) (0.299D * ((pixel) & 0xFF) + 0.587D * ((pixel >> 8) & 0xFF) + 0.114D * ((pixel >> 16) & 0xFF));
                Color color = ColorHelper.colorLerp(L/255f, brightColor, darkColor);
                nativeimage.setPixelRGBA(x, y, NativeImage.combine((pixel >> 24) & 0xFF, color.getBlue(), color.getGreen(), color.getRed()));
            }
        }
        return nativeimage;
    }
    public static NativeImage colorGradient(NativeImage nativeimage, Color brightColor, Color middleColor, Color darkColor)
    {
        for (int x = 0; x < nativeimage.getWidth(); x++) {
            for (int y = 0; y < nativeimage.getHeight(); y++) {
                int pixel = nativeimage.getPixelRGBA(x, y);
                int L = (int) (0.299D * ((pixel) & 0xFF) + 0.587D * ((pixel >> 8) & 0xFF) + 0.114D * ((pixel >> 16) & 0xFF));
                float lerp = L/255f;
                Color color = lerp <= 0.5f ? ColorHelper.colorLerp(lerp*2f, middleColor, darkColor) : ColorHelper.colorLerp(lerp*2f-1, brightColor, middleColor);
                nativeimage.setPixelRGBA(x, y, NativeImage.combine((pixel >> 24) & 0xFF, color.getBlue(), color.getGreen(), color.getRed()));
            }
        }
        return nativeimage;
    }
    public interface TextureModifier
    {
        NativeImage modifyTexture(NativeImage nativeImage);
    }
}