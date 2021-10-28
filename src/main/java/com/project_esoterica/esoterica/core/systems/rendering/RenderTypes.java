package com.project_esoterica.esoterica.core.systems.rendering;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.EsotericaMod;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class RenderTypes extends RenderStateShard{
    public RenderTypes(String p_110161_, Runnable p_110162_, Runnable p_110163_) {
        super(p_110161_, p_110162_, p_110163_);
    }
    public static RenderType TEST = createArmorGlintRenderType(EsotericaHelper.prefix("textures/misc/custom_glint_test.png"));
    public static RenderType TEST2 = createArmorEntityGlintRenderType(EsotericaHelper.prefix("textures/misc/custom_glint_test.png"));

    public static RenderType createArmorGlintRenderType(ResourceLocation resourceLocation) {
        return RenderType.create(
                EsotericaMod.MOD_ID + ":armor_glint",
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS, 256,
                false, false,
                RenderType.CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_ARMOR_GLINT_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, true, false))
                        .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                        .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                        .setTexturingState(RenderStateShard.GLINT_TEXTURING)
                        .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                        .createCompositeState(false)
        );
    }

    public static RenderType createArmorEntityGlintRenderType(ResourceLocation resourceLocation) {
        return RenderType.create(
                EsotericaMod.MOD_ID + ":armor_glint",
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS, 256,
                false, false,
                RenderType.CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_ARMOR_ENTITY_GLINT_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, true, false))
                        .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                        .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                        .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
                        .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                        .createCompositeState(false)
        );
    }

    public static RenderType createGlowingTextureRenderType(ResourceLocation resourceLocation) {
        return RenderType.create(
                EsotericaMod.MOD_ID + ":glowing_texture",
                DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
                VertexFormat.Mode.QUADS, 256,
                false, false,
                RenderType.CompositeState.builder()
                        .setShaderState(StateShards.ADDITIVE_TEXTURE_SHADER_STATE)
                        .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, true))
                        .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                        .setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)
                        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                        .setCullState(new RenderStateShard.CullStateShard(true))
                        .createCompositeState(true)
        );
    }

    public static RenderType createTest(ResourceLocation resourceLocation) {
        return RenderType.create(
                EsotericaMod.MOD_ID + ":test",
                DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
                VertexFormat.Mode.TRIANGLES, 256,
                false, false,
                RenderType.CompositeState.builder()
                        .setShaderState(StateShards.ADDITIVE_TEXTURE_SHADER_STATE)
                        .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, true))
                        .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                        .setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)
                        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                        .setCullState(new RenderStateShard.CullStateShard(true))
                        .createCompositeState(true)
        );
    }
}
