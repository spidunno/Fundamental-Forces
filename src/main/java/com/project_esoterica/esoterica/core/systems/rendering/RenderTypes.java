package com.project_esoterica.esoterica.core.systems.rendering;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.core.setup.client.ShaderRegistry;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;
import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_TEX;

public class RenderTypes extends RenderStateShard{
    public RenderTypes(String p_110161_, Runnable p_110162_, Runnable p_110163_) {
        super(p_110161_, p_110162_, p_110163_);
    }

    public static final RenderType ADDITIVE_PARTICLE = createGenericRenderType("additive_particle", PARTICLE, VertexFormat.Mode.QUADS, ShaderRegistry.additiveParticle.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_PARTICLES);
    public static final RenderType ADDITIVE_SCREEN_PARTICLE = createGenericRenderType("additive_screen_particle", PARTICLE, VertexFormat.Mode.QUADS, ShaderRegistry.additiveParticle.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_PARTICLES);
    public static final RenderType ADDITIVE_BLOCK_PARTICLE = createGenericRenderType("additive_block_particle", PARTICLE, VertexFormat.Mode.QUADS, ShaderRegistry.additiveParticle.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_BLOCKS);

    public static RenderType createQuadRenderType(TransparencyStateShard transparencyStateShard, ResourceLocation resourceLocation) {
        return createGenericRenderType("additive_quad", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.additiveTexture.shard, transparencyStateShard, resourceLocation);
    }
    public static RenderType createRadialNoiseQuadRenderType(ResourceLocation resourceLocation) {
        return createGenericRenderType("radial_noise_quad", POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, ShaderRegistry.radialNoise.shard, StateShards.ADDITIVE_TRANSPARENCY, resourceLocation);
    }

    public static RenderType createRadialScatterNoiseQuadRenderType(ResourceLocation resourceLocation) {
        return createGenericRenderType("radial_scatter_noise_quad", POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, ShaderRegistry.radialScatterNoise.shard, StateShards.ADDITIVE_TRANSPARENCY, resourceLocation);
    }

    public static RenderType createColorGradientQuadRenderType(TransparencyStateShard transparencyStateShard, ResourceLocation resourceLocation) {
        return createGenericRenderType("color_gradient_quad", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.colorGradientTexture.shard, transparencyStateShard, resourceLocation);
    }

    public static RenderType createMovingTrailRenderType(TransparencyStateShard transparencyStateShard, ResourceLocation resourceLocation) {
        return createGenericRenderType("moving_trail", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.movingTrail.shard, transparencyStateShard, resourceLocation);
    }

    public static RenderType createBootlegTriangleRenderType(TransparencyStateShard transparencyStateShard, ResourceLocation resourceLocation) {
        return createGenericRenderType("bootleg_triangle", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.bootlegTriangle.shard, transparencyStateShard, resourceLocation);
    }

    public static RenderType createMovingBootlegTriangleRenderType(TransparencyStateShard transparencyStateShard, ResourceLocation resourceLocation) {
        return createGenericRenderType("moving_bootleg_triangle", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.movingBootlegTriangle.shard, transparencyStateShard, resourceLocation);
    }

    public static RenderType createGenericRenderType(String name, VertexFormat format, VertexFormat.Mode mode, ShaderStateShard shader, TransparencyStateShard transparency, ResourceLocation texture) {
        RenderType type =  RenderType.create(
                EsotericaMod.MODID + ":" + name, format, mode, 256, false, false,
                RenderType.CompositeState.builder()
                        .setShaderState(shader)
                        .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, true))
                        .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                        .setTransparencyState(transparency)
                        .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                        .setCullState(new RenderStateShard.CullStateShard(true))
                        .createCompositeState(true));
        RenderManager.BUFFERS.put(type, new BufferBuilder(type.bufferSize()));
        return type;
    }
    public static RenderType withShaderHandler(RenderType type, RenderTypeShaderHandler handler)
    {
        RenderManager.HANDLERS.put(type, handler);
        return type;
    }
}
