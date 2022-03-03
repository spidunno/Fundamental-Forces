package com.sammy.fundamental_forces.core.systems.rendering.screenparticle.options;

import com.sammy.fundamental_forces.core.systems.rendering.particle.options.ParticleOptionsBase;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.base.ScreenParticle;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.ScreenParticleType;

public class ScreenParticleOptions extends ParticleOptionsBase {

    public final ScreenParticleType<?> type;
    public ScreenParticle.RenderOrder renderOrder;
    public ScreenParticleOptions(ScreenParticleType<?> type) {
        this.type = type;
    }
}