package com.project_esoterica.esoterica.core.systems.rendering;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.core.registry.misc.ShaderRegistry;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP;

public class RenderTypes extends RenderStateShard{
    public RenderTypes(String p_110161_, Runnable p_110162_, Runnable p_110163_) {
        super(p_110161_, p_110162_, p_110163_);
    }

    public static RenderType createAdditiveQuadRenderType(ResourceLocation resourceLocation) {
        return createGenericRenderType("additive_quad", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.additiveTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, resourceLocation);
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
        return RenderType.create(
                EsotericaMod.MOD_ID + ":" + name, format, mode, 256, false, false,
                RenderType.CompositeState.builder()
                        .setShaderState(shader)
                        .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, true))
                        .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                        .setTransparencyState(transparency)
                        .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                        .setCullState(new RenderStateShard.CullStateShard(true))
                        .createCompositeState(true)
        );
    }
}
