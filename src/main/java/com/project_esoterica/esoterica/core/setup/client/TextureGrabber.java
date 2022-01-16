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

    private static final ColorLerp GRADIENT = (image, x, y, luminosity) -> ((y % 16) / 16f);
    private static final ColorLerp LUMINOUS_GRADIENT = (image, x, y, luminosity) -> (((y % 16) / 16f) + luminosity/255f)/2f;
    private static final ColorLerp LUMINOUS = (image, x, y, luminosity) -> luminosity/255f;

    public static void setup() {
        registerGrabber("fire_0", "textures/block/fire_0.png", (image)->colorGradient(image, LUMINOUS, new Color(246, 255, 255), new Color(174, 198, 255), new Color(122, 56, 201)));
        registerGrabber("fire_1", "textures/block/fire_1.png", (image)->colorGradient(image, LUMINOUS, new Color(246, 255, 255), new Color(174, 198, 255), new Color(122, 56, 201)));
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
    //TODO: eventually rewrite these two methods to use a list of colors instead, allowing for any number of colors in a gradient
    public static NativeImage colorGradient(NativeImage nativeimage, ColorLerp colorLerp, Color brightColor, Color darkColor)
    {
        for (int x = 0; x < nativeimage.getWidth(); x++) {
            for (int y = 0; y < nativeimage.getHeight(); y++) {
                int pixel = nativeimage.getPixelRGBA(x, y);
                int luminosity = (int) (0.299D * ((pixel) & 0xFF) + 0.587D * ((pixel >> 8) & 0xFF) + 0.114D * ((pixel >> 16) & 0xFF));
                float lerp = colorLerp.lerp(pixel, x, y, luminosity);
                Color color = ColorHelper.colorLerp(lerp, brightColor, darkColor);
                nativeimage.setPixelRGBA(x, y, NativeImage.combine((pixel >> 24) & 0xFF, color.getBlue(), color.getGreen(), color.getRed()));
            }
        }
        return nativeimage;
    }
    public static NativeImage colorGradient(NativeImage nativeimage, ColorLerp colorLerp, Color brightColor, Color middleColor, Color darkColor)
    {
        for (int x = 0; x < nativeimage.getWidth(); x++) {
            for (int y = 0; y < nativeimage.getHeight(); y++) {
                int pixel = nativeimage.getPixelRGBA(x, y);
                int luminosity = (int) (0.299D * ((pixel) & 0xFF) + 0.587D * ((pixel >> 8) & 0xFF) + 0.114D * ((pixel >> 16) & 0xFF));
                float lerp = colorLerp.lerp(pixel, x, y, luminosity);
                Color color = lerp <= 0.5f ? ColorHelper.colorLerp(lerp*2f, middleColor, darkColor) : ColorHelper.colorLerp(lerp*2f-1, brightColor, middleColor);
                nativeimage.setPixelRGBA(x, y, NativeImage.combine((pixel >> 24) & 0xFF, color.getBlue(), color.getGreen(), color.getRed()));
            }
        }
        return nativeimage;
    }
    public interface ColorLerp
    {
        float lerp(int pixel, int x, int y, int luminosity);
    }
    public interface TextureModifier
    {
        NativeImage modifyTexture(NativeImage nativeImage);
    }
}