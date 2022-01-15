package com.project_esoterica.esoterica.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {

    public static ForgeConfigSpec.ConfigValue<Boolean> DELAYED_PARTICLE_RENDERING;
    public static ForgeConfigSpec.DoubleValue SCREENSHAKE_INTENSITY;
    public static ForgeConfigSpec.DoubleValue FIRE_OVERLAY_OFFSET;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Rendering settings").push("rendering");

        builder.comment("Screenshake settings").push("screenshake");
        SCREENSHAKE_INTENSITY = builder.comment("Screenshake intensity comment")
                .defineInRange("screenshake_intensity", 1d, 0d, 1d);
        builder.pop();

        builder.comment("Fire Overlay settings").push("fire");
        FIRE_OVERLAY_OFFSET = builder.comment("Fire overlay downwards offset comment")
                .defineInRange("fire_overlay_offset", 0d, 0d, 1d);
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
