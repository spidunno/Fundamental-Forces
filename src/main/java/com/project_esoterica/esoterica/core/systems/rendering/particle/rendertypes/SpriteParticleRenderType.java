package com.project_esoterica.esoterica.core.systems.rendering.particle.rendertypes;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.GL11;

public class SpriteParticleRenderType implements ParticleRenderType {
    public static final SpriteParticleRenderType INSTANCE = new SpriteParticleRenderType();

    private static void beginRenderCommon(BufferBuilder bufferBuilder, TextureManager textureManager) {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        RenderSystem.alphaFunc(GL11.GL_GEQUAL, 0.00390625f);

        textureManager.bindForSetup(TextureAtlas.LOCATION_PARTICLES);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    private static void endRenderCommon() {
        Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).restoreLastBlurMipmap();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
    }

    @Override
    public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
        beginRenderCommon(bufferBuilder, textureManager);
    }

    @Override
    public void end(Tesselator tesselator) {
        tesselator.end();
        RenderSystem.enableDepthTest();
        endRenderCommon();
    }
}
