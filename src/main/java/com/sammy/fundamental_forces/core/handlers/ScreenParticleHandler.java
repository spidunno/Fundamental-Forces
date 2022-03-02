package com.sammy.fundamental_forces.core.handlers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Matrix4f;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.ScreenParticle;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.ScreenParticleType;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.emitter.ItemParticleEmitter;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.options.ScreenParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ScreenParticleHandler {

    public static Map<ParticleRenderType, ArrayList<ScreenParticle>> PARTICLES;
    public static Map<Item, ItemParticleEmitter> EMITTERS = new HashMap<>();
    public static final Tesselator TESSELATOR = new Tesselator();
    public static boolean canSpawnItemParticles;

    public static void clientTick(TickEvent.ClientTickEvent event) {
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
        canSpawnItemParticles = true;
    }

    public static void renderItem(ItemStack stack) {
        Minecraft minecraft = Minecraft.getInstance();
        if (canSpawnItemParticles && minecraft.level != null) {
            if (minecraft.isPaused()) {
                return;
            }
            if (!stack.isEmpty()) {
                ItemParticleEmitter emitter = ScreenParticleHandler.EMITTERS.get(stack.getItem());
                if (emitter != null) {
                    PoseStack posestack = RenderSystem.getModelViewStack();
                    Matrix4f last = posestack.last().pose();
                    float x = last.m03;
                    float y = last.m13;
                    emitter.tick(stack, x, y);
                }
            }
        }
    }

    public static void renderParticles(TickEvent.RenderTickEvent event) {
        if (Minecraft.getInstance().screen == null && event.phase.equals(TickEvent.Phase.END)) {
            renderParticles();
        }
    }
    public static void renderParticles() {
        PARTICLES.forEach((type, particles) -> {
            type.begin(TESSELATOR.getBuilder(), Minecraft.getInstance().textureManager);
            particles.forEach(p -> p.render(TESSELATOR.getBuilder()));
            type.end(TESSELATOR);
        });
        canSpawnItemParticles = false;
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

    public static void registerItemParticleEmitter(Item item, ItemParticleEmitter emitter) {
        EMITTERS.put(item, emitter);
    }

    public static void registerItemParticleEmitter(ItemParticleEmitter emitter, Item... items) {
        for (Item item : items) {
            EMITTERS.put(item, emitter);
        }
    }
}