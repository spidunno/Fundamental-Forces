package com.project_esoterica.esoterica.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.client.model.bibit.BibitModel;
import com.project_esoterica.esoterica.common.entity.Bibit;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class BibitRenderer extends GeoEntityRenderer<Bibit> {
    public BibitRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BibitModel());
        addLayer(new BibitGlowLayer(this));
    }
}