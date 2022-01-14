package com.project_esoterica.esoterica.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {

    public static ForgeConfigSpec.ConfigValue<Boolean> DELAYED_PARTICLE_RENDERING;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_SCREENSHAKE;
    public static ForgeConfigSpec.ConfigValue<Float> MAX_SCREENSHAKE_INTENSITY;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Rendering settings").push("rendering");

        builder.comment("Screenshake settings").push("screenshake");
        ENABLE_SCREENSHAKE = builder.comment("Enable screenshake?")
                .define("enable_screenshake", true);
        MAX_SCREENSHAKE_INTENSITY = builder.comment("Maximum screenshake strength")
                .define("maximum_screenshake_strength", 10f);
        builder.pop();

        builder.comment("Graphics settings").push("graphics");
        DELAYED_PARTICLE_RENDERING = builder.comment("Render particles on the delayed buffer, properly rendering them after clouds do but potentially causing issues with mods like Sodium. Disable if crashes occur when rendering particles.")
                .define("buffer_particles", true);
        builder.pop();
        builder.pop();
    }

    public static final ClientConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
