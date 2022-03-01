package com.sammy.fundamental_forces.core.systems.rendering.screenparticle.emitter;

import net.minecraft.world.item.ItemStack;

public interface ItemParticleEmitter {
    public void tick(ItemStack stack, float pXPosition, float pYPosition);
}
