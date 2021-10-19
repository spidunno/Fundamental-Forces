package com.project_esoterica.esoterica.core.systems.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.client.renderer.falling.FallingStarRenderer;
import com.project_esoterica.esoterica.core.config.ClientConfig;
import com.project_esoterica.esoterica.core.registry.misc.ShaderRegistry;
import com.project_esoterica.esoterica.core.systems.ancientparticlecode.RenderUtilities;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid= EsotericaMod.MOD_ID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.FORGE)
public class RenderManager {
    @OnlyIn(Dist.CLIENT)
    static MultiBufferSource.BufferSource DELAYED_RENDER = null;

    @OnlyIn(Dist.CLIENT)
    public static MultiBufferSource.BufferSource getDelayedRender() {
        if (DELAYED_RENDER == null) {
            Map<RenderType, BufferBuilder> buffers = new HashMap<>();
            for (RenderType type : new RenderType[]{
                    FallingStarRenderer.RENDER_TYPE}) {
                buffers.put(type, new BufferBuilder(type.bufferSize()));
            }
            DELAYED_RENDER = MultiBufferSource.immediate(new BufferBuilder(256));
        }
        return DELAYED_RENDER;
    }
    @SubscribeEvent
    public static void onRenderLast(RenderWorldLastEvent event) {
        if (ClientConfig.BETTER_LAYERING.get()) {
            event.getMatrixStack().pushPose();
            getDelayedRender().endBatch();
            event.getMatrixStack().popPose();
        }
    }

    public static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("additive_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });
    public static final RenderStateShard.TransparencyStateShard NORMAL_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("normal_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
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
}