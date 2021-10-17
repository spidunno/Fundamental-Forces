package com.project_esoterica.esoterica.client.renderer;

import com.project_esoterica.esoterica.client.model.bibit.BibitModel;
import com.project_esoterica.esoterica.common.entity.Bibit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BibitRenderer extends GeoEntityRenderer<Bibit> {
    public BibitRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BibitModel());
        addLayer(new BibitGlowLayer(this));
    }
}