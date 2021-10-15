package com.space_mod_group.space_mod.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {

    public static ForgeConfigSpec.ConfigValue<Boolean> STARFALLS_ENABLED;
    public static ForgeConfigSpec.ConfigValue<Integer> MINIMUM_STARFALL_DISTANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> MAXIMUM_STARFALL_DISTANCE;

    public static ForgeConfigSpec.DoubleValue ASTEROID_CHANCE;
    public static ForgeConfigSpec.DoubleValue MINIMUM_ASTEROID_COUNTDOWN_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue MAXIMUM_ASTEROID_COUNTDOWN_MULTIPLIER;

    public static ForgeConfigSpec.ConfigValue<Integer> INITIAL_SPACE_DEBRIS_COUNTDOWN;
    public static ForgeConfigSpec.ConfigValue<Integer> NATURAL_SPACE_DEBRIS_COUNTDOWN;
    public static ForgeConfigSpec.ConfigValue<Float> MINIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Float> MAXIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("World Events").push("world_events");

        builder.comment("Starfalls").push("starfalls");

        STARFALLS_ENABLED = builder.comment("Are starfalls enabled?")
                .define("enable_starfalls", true);

        MINIMUM_STARFALL_DISTANCE = builder.comment("What's the minimum distance away from a player in which a starfall can occur?")
                .define("minimum_starfall_distance", 64);

        MAXIMUM_STARFALL_DISTANCE = builder.comment("What's the maximum distance away from a player in which a starfall can occur?")
                .define("maximum_starfall_distance", 256);

        builder.comment("Asteroids").push("asteroids");
        ASTEROID_CHANCE = builder.comment("Asteroids are scheduled to fall alongside space debris. What's the chance for this to happen?")
                .defineInRange("asteroid_fall_chance", 0.6, 0, 1.0);

        MINIMUM_ASTEROID_COUNTDOWN_MULTIPLIER = builder.comment("Asteroids fall sometime after the space debris. How close to the space debris starfall can the asteroid starfall be?")
                .defineInRange("minimum_asteroid_countdown_multiplier", 0.1, 0.01, 1.0);

        MAXIMUM_ASTEROID_COUNTDOWN_MULTIPLIER = builder.comment("Asteroids fall sometime after the space debris. How close to the next space debris starfall can the asteroid starfall be?")
                .defineInRange("maximum_asteroid_countdown_multiplier", 0.95, 0.01, 1.0);
        builder.pop();

        builder.comment("Space Debris").push("space_debris");

        INITIAL_SPACE_DEBRIS_COUNTDOWN = builder.comment("What's the base countdown for the very first space debris starfall?")
                .define("initial_space_debris_countdown", 100);

        NATURAL_SPACE_DEBRIS_COUNTDOWN = builder.comment("What's the base countdown for every subsequent space debris starfall?")
                .define("natural_space_debris_countdown", 500);

        MINIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER = builder.comment("What's the minimum multiplier for a space debris starfall countdown?")
                .define("minimum_asteroid_countdown_multiplier", 0.85f);

        MAXIMUM_SPACE_DEBRIS_COUNTDOWN_MULTIPLIER = builder.comment("What's the maximum multiplier for a space debris starfall countdown?")
                .define("maximum_asteroid_countdown_multiplier", 1.25f);
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