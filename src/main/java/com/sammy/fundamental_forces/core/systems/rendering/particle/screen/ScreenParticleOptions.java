package com.sammy.fundamental_forces.core.systems.rendering.particle.screen;

import com.sammy.fundamental_forces.core.systems.rendering.particle.SimpleParticleOptions;
import com.sammy.fundamental_forces.core.systems.rendering.particle.screen.base.ScreenParticle;

public class ScreenParticleOptions extends SimpleParticleOptions {

    public final ScreenParticleType<?> type;
    public ScreenParticle.RenderOrder renderOrder;
    public ScreenParticleOptions(ScreenParticleType<?> type) {
        this.type = type;
    }
}