package com.project_esoterica.esoterica.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.project_esoterica.esoterica.client.model.bibit.BibitModel;
import com.project_esoterica.esoterica.common.entity.Bibit;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class BibitGlowLayer extends GeoLayerRenderer<Bibit> {
    public BibitGlowLayer(IGeoRenderer<Bibit> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Bibit bibit, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        float r = 1;
        float g = 1;
        float b = 1;
        if (bibit.feelings.equals(Bibit.feelingsEnum.PROUD))
        {
            int i1 = 25; //Here lies garbage copied from SheepFurLayer.
            int i = bibit.tickCount / 25 + bibit.getId();
            int j = DyeColor.values().length;
            int k = i % j;
            int l = (i + 1) % j;
            float f3 = ((float)(bibit.tickCount % i1) + partialTicks) / i1;
            float[] afloat1 = Sheep.getColorArray(DyeColor.byId(k));
            float[] afloat2 = Sheep.getColorArray(DyeColor.byId(l));
            r = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
            g = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
            b = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
        }
        renderCopyModel(getRenderer().getGeoModelProvider(), BibitModel.getOverlayTextureLocation(bibit),matrixStackIn,bufferIn,0xF000F0,bibit,partialTicks, r, g, b);
    }

    @Override
    public RenderType getRenderType(ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(textureLocation);
    }
}
