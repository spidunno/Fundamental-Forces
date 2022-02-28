package com.project_esoterica.esoterica.core.systems.rendering.screenparticle.options;

import com.project_esoterica.esoterica.core.systems.rendering.particle.options.ParticleOptionsBase;
import com.project_esoterica.esoterica.core.systems.rendering.screenparticle.ScreenParticleType;

public class ScreenParticleOptions extends ParticleOptionsBase {

    public final ScreenParticleType<?> type;
    public ScreenParticleOptions(ScreenParticleType<?> type) {
        this.type = type;
    }
}