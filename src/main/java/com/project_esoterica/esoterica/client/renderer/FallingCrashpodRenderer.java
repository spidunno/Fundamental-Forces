package com.project_esoterica.esoterica.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.project_esoterica.esoterica.common.entity.falling.FallingCrashpodEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FallingCrashpodRenderer extends EntityRenderer<FallingCrashpodEntity> {

    public FallingCrashpodRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(FallingCrashpodEntity p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
        super.render(p_114485_, p_114486_, p_114487_, p_114488_, p_114489_, p_114490_);
    }

    @Override
    public ResourceLocation getTextureLocation(FallingCrashpodEntity p_114482_) {
        return null;
    }
}
