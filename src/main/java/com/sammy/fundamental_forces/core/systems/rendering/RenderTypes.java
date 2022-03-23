package com.sammy.fundamental_forces.core.systems.rendering;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;
import com.sammy.fundamental_forces.FundamentalForcesMod;
import com.sammy.fundamental_forces.core.handlers.RenderHandler;
import com.sammy.fundamental_forces.core.setup.client.ShaderRegistry;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.function.Function;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;

public class RenderTypes extends RenderStateShard {
    public RenderTypes(String p_110161_, Runnable p_110162_, Runnable p_110163_) {
        super(p_110161_, p_110162_, p_110163_);
    }

    public static final HashMap<Pair<Integer, RenderType>, RenderType> COPIES = new HashMap<>(); //Stores many copies of render types
    //key is an index + original render type, value is a copy of the original
    //We do most if not all of our rendering in RenderLevelLast, all of it buffered. When we end render batches, we also need to apply our shader uniform changes prior to ending a batch.
    //These uniform changes are cached per unique render type, copies become useful when we want to apply different uniform values to the same render type an undetermined amount of times
    //Rather than creating several static render types, we may copy the original render type during runtime and compute a copy if one is absent.

    public static final RenderType ADDITIVE_PARTICLE = createGenericRenderType("additive_particle", PARTICLE, VertexFormat.Mode.QUADS, ShaderRegistry.additiveParticle.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_PARTICLES);
    public static final RenderType ADDITIVE_BLOCK_PARTICLE = createGenericRenderType("additive_block_particle", PARTICLE, VertexFormat.Mode.QUADS, ShaderRegistry.additiveParticle.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_BLOCKS);

    public static final Function<ResourceLocation, RenderType> ADDITIVE_TEXTURE = (texture) -> createGenericRenderType("additive_texture", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.additiveTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);
    public static final Function<ResourceLocation, RenderType> RADIAL_NOISE = (texture) -> createGenericRenderType("radial_noise", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.radialNoise.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);
    public static final Function<ResourceLocation, RenderType> RADIAL_SCATTER_NOISE = (texture) -> createGenericRenderType("radial_scatter_noise", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.radialScatterNoise.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);
    public static final Function<ResourceLocation, RenderType> TEXTURE_TRIANGLE = (texture) -> createGenericRenderType("texture_triangle", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.triangleTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);
    public static final Function<ResourceLocation, RenderType> SCROLLING_TEXTURE_TRIANGLE = (texture) -> createGenericRenderType("scrolling_texture_triangle", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.scrollingTriangleTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);

    public static final Function<RenderTypeData, RenderType> GENERIC = (data) -> createGenericRenderType(data.name, data.format, data.mode, data.shader, data.transparency, data.texture);

    public static RenderType createGenericRenderType(String name, VertexFormat format, VertexFormat.Mode mode, ShaderStateShard shader, TransparencyStateShard transparency, ResourceLocation texture) {
        RenderType type = RenderType.create(
                FundamentalForcesMod.MODID + ":" + name, format, mode, 256, false, false,
                RenderType.CompositeState.builder()
                        .setShaderState(shader)
                        .setWriteMaskState(new WriteMaskStateShard(true, true))
                        .setLightmapState(new LightmapStateShard(false))
                        .setTransparencyState(transparency)
                        .setTextureState(new TextureStateShard(texture, false, false))
                        .setCullState(new CullStateShard(true))
                        .createCompositeState(true)
        );
        RenderHandler.BUFFERS.put(type, new BufferBuilder(type.bufferSize()));
        return type;
    }

    public static RenderType bufferUniformChanges(RenderType type, RenderTypeShaderHandler handler) {
        RenderHandler.HANDLERS.put(type, handler);
        return type;
    }

    public static RenderType copy(int index, RenderType type) {
        return COPIES.computeIfAbsent(Pair.of(index, type), (p)->GENERIC.apply(new RenderTypeData((RenderType.CompositeRenderType) type)));
    }

    public static class RenderTypeData {
        public final String name;
        public final VertexFormat format;
        public final VertexFormat.Mode mode;
        public final ShaderStateShard shader;
        public TransparencyStateShard transparency = StateShards.ADDITIVE_TRANSPARENCY;
        public final ResourceLocation texture;

        public RenderTypeData(String name, VertexFormat format, VertexFormat.Mode mode, ShaderStateShard shader, ResourceLocation texture) {
            this.name = name;
            this.format = format;
            this.mode = mode;
            this.shader = shader;
            this.texture = texture;
        }

        public RenderTypeData(String name, VertexFormat format, VertexFormat.Mode mode, ShaderStateShard shader, TransparencyStateShard transparency, ResourceLocation texture) {
            this(name, format, mode, shader, texture);
            this.transparency = transparency;
        }

        public RenderTypeData(RenderType.CompositeRenderType type) {
            this(type.toString(), type.format(), type.mode(), type.state.shaderState, type.state.transparencyState, type.state.textureState.cutoutTexture().get());
        }
    }
}