package com.space_mod_group.space_mod.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {

    public static ForgeConfigSpec.ConfigValue<Boolean> TEST_BOOLEAN;
    public static ForgeConfigSpec.ConfigValue<Boolean> TEST_BOOLEAN_AGAIN;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("World Events").push("world_events");

        builder.comment("Starfalls").push("starfalls");

        builder.comment("Asteroids").push("asteroids");
        TEST_BOOLEAN = builder.comment("Should asteroids fall out of the sky?")
                .define("spawnAsteroids", true);
        builder.pop();

        builder.comment("Space Debris").push("space_debris");
        TEST_BOOLEAN_AGAIN = builder.comment("Should space debris fall out of the sky?")
                .define("spawnAsteroids", true);
        builder.pop();

        builder.pop();

        builder.pop();
    }

    public static final CommonConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
