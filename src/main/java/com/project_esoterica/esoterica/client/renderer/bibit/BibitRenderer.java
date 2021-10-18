package com.project_esoterica.esoterica.client.renderer.bibit;

import com.project_esoterica.esoterica.client.model.bibit.BibitModel;
import com.project_esoterica.esoterica.common.entity.robot.BibitEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BibitRenderer extends GeoEntityRenderer<BibitEntity> {
    public BibitRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BibitModel());
        this.shadowRadius = 0.5F;
        this.shadowStrength = 0.5F;
        addLayer(new BibitGlowLayer(this));
    }
}