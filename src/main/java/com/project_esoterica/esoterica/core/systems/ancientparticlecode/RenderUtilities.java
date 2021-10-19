package com.project_esoterica.esoterica.core.systems.ancientparticlecode;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.core.registry.misc.ShaderRegistry;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class RenderUtilities {
    public static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("lightning_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static RenderType createGlowingTextureRenderType(ResourceLocation resourceLocation) {
        return RenderType.create(
                EsotericaMod.MOD_ID + ":glowing_texture",
                DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
                VertexFormat.Mode.QUADS, 256,
                false, false,
                RenderType.CompositeState.builder()
                        .setShaderState(ShaderRegistry.ADDITIVE_SHADER_STATE)
                        .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, true))
                        .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                        .setTransparencyState(ADDITIVE_TRANSPARENCY)
                        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                        .setCullState(new RenderStateShard.CullStateShard(true))
                        .createCompositeState(true)
        );
    }
    /*GLOWING = RenderType.create(
            EsotericaMod.MOD_ID + ":glowing",
            DefaultVertexFormat.POSITION_COLOR,
            GL11.GL_QUADS, 256,
            RenderType.CompositeState.builder()
                    .shadeModel(new RenderState.ShadeModelState(true))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(ADDITIVE_TRANSPARENCY)
                    .build(false)
    ), DELAYED_PARTICLE = RenderType.create(
            EsotericaMod.MOD_ID + ":delayed_particle",
            DefaultVertexFormat.PARTICLE,
            GL11.GL_QUADS, 256,
            RenderType.CompositeState.builder()
                    .shadeModel(new RenderState.ShadeModelState(true))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(NORMAL_TRANSPARENCY)
                    .texture(new RenderState.TextureState(AtlasTexture.LOCATION_PARTICLES_TEXTURE, false, false))
                    .build(false)
    ), GLOWING_PARTICLE = RenderType.create(
            EsotericaMod.MOD_ID + ":glowing_particle",
            DefaultVertexFormat.PARTICLE,
            GL11.GL_QUADS, 256,
            RenderType.CompositeState.builder()
                    .shadeModel(new RenderState.ShadeModelState(true))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(ADDITIVE_TRANSPARENCY)
                    .texture(new RenderState.TextureState(AtlasTexture.LOCATION_PARTICLES_TEXTURE, false, false))
                    .build(false)
    ), GLOWING_BLOCK_PARTICLE = RenderType.create(
            EsotericaMod.MOD_ID + ":glowing_particle",
            DefaultVertexFormat.PARTICLE,
            GL11.GL_QUADS, 256,
            RenderType.CompositeState.builder()
                    .shadeModel(new RenderState.ShadeModelState(true))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(ADDITIVE_TRANSPARENCY)
                    .texture(new RenderState.TextureState(AtlasTexture.LOCATION_BLOCKS_TEXTURE, false, false))
                    .build(false)
    );*/
}