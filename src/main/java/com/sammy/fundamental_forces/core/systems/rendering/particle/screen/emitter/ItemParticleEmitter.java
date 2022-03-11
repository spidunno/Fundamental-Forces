package com.sammy.fundamental_forces.core.systems.rendering.particle.screen.emitter;

import com.sammy.fundamental_forces.core.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.world.item.ItemStack;

public interface ItemParticleEmitter {
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder, ParticleEmitter emitter);
}
