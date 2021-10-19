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