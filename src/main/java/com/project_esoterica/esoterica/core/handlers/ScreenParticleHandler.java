package com.project_esoterica.esoterica.core.handlers;

import com.mojang.blaze3d.vertex.Tesselator;
import com.project_esoterica.esoterica.core.helper.ParticleHelper;
import com.project_esoterica.esoterica.core.setup.client.ScreenParticleRegistry;
import com.project_esoterica.esoterica.core.systems.rendering.particle.options.ScreenParticleOptions;
import com.project_esoterica.esoterica.core.systems.rendering.screenparticle.ScreenParticle;
import com.project_esoterica.esoterica.core.systems.rendering.screenparticle.ScreenParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ScreenParticleHandler {

    public static Map<ParticleRenderType, ArrayList<ScreenParticle>> PARTICLES;

    public static void clientTick(TickEvent.ClientTickEvent event) {
        ParticleHelper.create(ScreenParticleRegistry.WISP)
                .addVelocity(1, 0)
                .setLifetime(200)
                .setColor(24/255f, 20/255f, 142/255f, 85/255f, 2/255f, 10/255f)
                .setAlphaCurveMultiplier(0.5f)
                .setScale(2, 0.5f)
                .setAlpha(0.25f, 0)
                .spawn(100, 100);

        PARTICLES.forEach((type, particles) -> {
            Iterator<ScreenParticle> iterator = particles.iterator();
            while (iterator.hasNext()) {
                ScreenParticle particle = iterator.next();
                particle.tick();
                if (!particle.isAlive()) {
                    iterator.remove();
                }
            }
        });
    }
    public static final Tesselator TESSELATOR = new Tesselator();

    public static void renderParticles(RenderGameOverlayEvent.Post event) {
        PARTICLES.forEach((type, particles) -> {
            event.getMatrixStack().pushPose();
            type.begin(TESSELATOR.getBuilder(), Minecraft.getInstance().textureManager);
            particles.forEach(p -> p.render(TESSELATOR.getBuilder(), event));
            type.end(TESSELATOR);
            event.getMatrixStack().popPose();
        });
    }

    @SuppressWarnings("ALL")
    public static <T extends ScreenParticleOptions> ScreenParticle addParticle(T options, double pX, double pY, double pXSpeed, double pYSpeed) {
        Minecraft minecraft = Minecraft.getInstance();
        ScreenParticleType<T> type = (ScreenParticleType<T>) options.type;
        ScreenParticleType.ParticleProvider<T> provider = type.provider;
        ScreenParticle particle = provider.createParticle(minecraft.level, options, pX, pY, pXSpeed, pYSpeed);
        ArrayList<ScreenParticle> list = PARTICLES.computeIfAbsent(particle.getRenderType(), (a) -> new ArrayList<>());
        list.add(particle);
        return particle;
    }
}