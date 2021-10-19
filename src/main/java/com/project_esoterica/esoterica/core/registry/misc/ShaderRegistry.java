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

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = EsotericaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShaderRegistry {

    private static ShaderInstance additive;
    public static final ShaderStateShard ADDITIVE_SHADER_STATE = new ShaderStateShard(() -> additive);
    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
        // Adds a shader to the list, the callback runs when loading is complete.
        event.registerShader(new ShaderInstance(event.getResourceManager(), EsotericaHelper.prefix("rendertype_additive_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> {
            additive = shaderInstance;
        });
    }
}
