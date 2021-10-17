package com.project_esoterica.esoterica.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.project_esoterica.esoterica.client.model.bibit.BibitModel;
import com.project_esoterica.esoterica.common.entity.Bibit;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class BibitGlowLayer extends GeoLayerRenderer<Bibit> {
    public BibitGlowLayer(IGeoRenderer<Bibit> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Bibit bibit, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        renderCopyModel(getRenderer().getGeoModelProvider(), BibitModel.getOverlayTextureLocation(bibit),matrixStackIn,bufferIn,packedLightIn,bibit,partialTicks,1,1,1);
    }

    @Override
    public RenderType getRenderType(ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(textureLocation);
    }
}
