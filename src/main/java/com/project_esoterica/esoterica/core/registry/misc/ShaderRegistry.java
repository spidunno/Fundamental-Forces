package com.project_esoterica.esoterica.core.registry.misc;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.EsotericaMod;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = EsotericaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShaderRegistry {

    private static ShaderInstance additiveTexture;
    public static Supplier<ShaderInstance> getAdditiveTextureShader()
    {
        return () -> additiveTexture;
    }
    private static ShaderInstance additiveParticle;
    public static Supplier<ShaderInstance> getAdditiveParticleShader()
    {
        return () -> additiveParticle;
    }
    public static final ShaderStateShard ADDITIVE_TEXTURE_SHADER_STATE = new ShaderStateShard(getAdditiveTextureShader());
    public static final ShaderStateShard ADDITIVE_PARTICLE_SHADER_STATE = new ShaderStateShard(getAdditiveParticleShader());
    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceManager(), EsotericaHelper.prefix("rendertype_additive_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> additiveTexture = shaderInstance);
        event.registerShader(new ShaderInstance(event.getResourceManager(), EsotericaHelper.prefix("additive_particle"), DefaultVertexFormat.PARTICLE), shaderInstance -> additiveParticle = shaderInstance);
    }
}
