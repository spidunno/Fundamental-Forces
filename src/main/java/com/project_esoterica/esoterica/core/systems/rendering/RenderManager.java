package com.project_esoterica.esoterica.core.systems.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.common.capability.WorldDataCapability;
import com.project_esoterica.esoterica.core.registry.misc.ShaderRegistry;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class RenderManager {
    @OnlyIn(Dist.CLIENT)
    public static MultiBufferSource.BufferSource DELAYED_RENDER = MultiBufferSource.immediate(new BufferBuilder(256));
    public static Frustum FRUSTUM;

    public static void onRenderLast(RenderWorldLastEvent event) {
        prepareFrustum(event.getMatrixStack(), Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition(), event.getProjectionMatrix());
        WorldDataCapability.getCapability(Minecraft.getInstance().level).ifPresent(capability -> {
            for (WorldEventInstance instance : capability.ACTIVE_WORLD_EVENTS) {
                if (instance.canRender()) {
                    instance.render(event.getMatrixStack(), RenderManager.DELAYED_RENDER, event.getPartialTicks());
                }
            }
        });
        event.getMatrixStack().pushPose();
        DELAYED_RENDER.endBatch();
        event.getMatrixStack().popPose();
    }

    public static void prepareFrustum(PoseStack p_172962_, Vec3 p_172963_, Matrix4f p_172964_) {
        Matrix4f matrix4f = p_172962_.last().pose();
        double d0 = p_172963_.x();
        double d1 = p_172963_.y();
        double d2 = p_172963_.z();
        FRUSTUM = new Frustum(matrix4f, p_172964_);
        FRUSTUM.prepare(d0, d1, d2);
    }

    public static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("additive_transparency", () -> {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
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
                        .setShaderState(ShaderRegistry.ADDITIVE_TEXTURE_SHADER_STATE)
                        .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, true))
                        .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                        .setTransparencyState(ADDITIVE_TRANSPARENCY)
                        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                        .setCullState(new RenderStateShard.CullStateShard(true))
                        .createCompositeState(true)
        );
    }
}