package com.sammy.fundamental_forces.core.systems.rendering.particle.screen;

import com.sammy.fundamental_forces.core.systems.rendering.particle.SimpleParticleOptions;
import com.sammy.fundamental_forces.core.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.world.item.ItemStack;

public class ScreenParticleOptions extends SimpleParticleOptions {

    public final ScreenParticleType<?> type;
    public ScreenParticle.RenderOrder renderOrder;
    public ItemStack stack;
    public float xOffset;
    public float yOffset;
    public ScreenParticleOptions(ScreenParticleType<?> type) {
        this.type = type;
    }
}