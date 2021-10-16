package com.project_esoterica.empirical_esoterica.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {

    public static ForgeConfigSpec.ConfigValue<Boolean> BETTER_LAYERING;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_SCREENSHAKE;
    public static ForgeConfigSpec.ConfigValue<Float> MAX_SCREENSHAKE_INTENSITY;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Rendering settings").push("rendering");

        builder.comment("Screenshake settings").push("screenshake");
        ENABLE_SCREENSHAKE = builder.comment("Enable screenshake?")
                .define("screenshake", true);
        MAX_SCREENSHAKE_INTENSITY = builder.comment("What's the maximum strength for screenshake?")
                .define("maximum_screenshake_strength", 10f);
        builder.pop();

        builder.comment("Graphics settings").push("graphics");
        BETTER_LAYERING = builder.comment("Enable better particle/effect layering. Fixes particles and effects rendering behind clouds and weather. NOTE: Does NOT work with fabulous graphics mode.")
                .define("better_layering", true);
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
