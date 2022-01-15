package com.project_esoterica.esoterica.core.systems.texturegrabber;

import com.mojang.blaze3d.platform.NativeImage;
import com.project_esoterica.esoterica.core.helper.DataHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

//https://github.com/Tamaized/Frostfell/blob/1.18/src/main/java/tamaized/frostfell/client/ClientListener.java#L23-L57
public class TextureGrabber {

    private static ArrayList<Pair<String, String>> GRABBERS = new ArrayList<>();
    public static void setup()
    {
        registerGrabber("fire_0", "textures/block/fire_0.png", "block/fire_0");
        registerGrabber("fire_1", "textures/block/fire_1.png", "block/fire_1");
    }
    public static void grab(FMLClientSetupEvent event) {
        GRABBERS.forEach(TextureGrabber::createGrabber);
        GRABBERS = null;
    }

    public static void registerGrabber(String loaderName, String sourcePath, String resultPath) {
        IEventBus busMod = FMLJavaModLoadingContext.get().getModEventBus();
        GRABBERS.add(Pair.of(loaderName, sourcePath));
        busMod.addListener((Consumer<TextureStitchEvent.Pre>) event -> event.addSprite(DataHelper.prefix(resultPath)));
    }
    public static void createGrabber(Pair<String, String> textures) {
        MinecraftForgeClient.registerTextureAtlasSpriteLoader(DataHelper.prefix(textures.getLeft()), (atlas, resourceManager, textureInfo, resource, atlasWidth, atlasHeight, spriteX, spriteY, mipmapLevel, image) -> {
                    Resource r = null;
                    try {
                        r = resourceManager.getResource(new ResourceLocation(textures.getRight()));
                        NativeImage nativeimage = NativeImage.read(r.getInputStream());
                        for (int x = 0; x < nativeimage.getWidth(); x++) {
                            for (int y = 0; y < nativeimage.getHeight(); y++) {
                                int pixel = nativeimage.getPixelRGBA(x, y);
                                int L = (int) (0.299D * ((pixel) & 0xFF) + 0.587D * ((pixel >> 8) & 0xFF) + 0.114D * ((pixel >> 16) & 0xFF));
                                nativeimage.setPixelRGBA(x, y, NativeImage.combine((pixel >> 24) & 0xFF, L, L, L));
                            }
                        }
                        image = nativeimage;
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
}