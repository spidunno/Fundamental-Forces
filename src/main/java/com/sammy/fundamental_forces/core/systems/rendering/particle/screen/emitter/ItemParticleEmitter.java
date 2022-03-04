package com.sammy.fundamental_forces.core.systems.rendering.particle.screen.emitter;

import com.sammy.fundamental_forces.core.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.world.item.ItemStack;

public interface ItemParticleEmitter {
    public void tick(ItemStack stack, float pXPosition, float pYPosition, ScreenParticle.RenderOrder renderOrder);
}
