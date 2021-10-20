package com.project_esoterica.esoterica.client.particle.wisp;


import com.mojang.blaze3d.vertex.VertexConsumer;
import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.core.config.ClientConfig;
import com.project_esoterica.esoterica.core.systems.ancientparticlecode.rendertypes.SpriteParticleRenderType;
import com.project_esoterica.esoterica.core.systems.rendering.RenderManager;
import com.project_esoterica.esoterica.core.systems.rendering.particle.GenericParticle;
import com.project_esoterica.esoterica.core.systems.rendering.particle.options.ParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderType;


public class WispParticle extends GenericParticle {
    public WispParticle(ClientLevel world, ParticleOptions data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
    }

    @Override
    protected int getLightColor(float p_107249_) {
        return 0xF000F0;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return SpriteParticleRenderType.INSTANCE;
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        super.render(consumer, camera, partialTicks);
    }
}