package com.project_esoterica.esoterica.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class CommonConfig {

    public static ForgeConfigSpec.ConfigValue<Boolean> STARFALLS_ENABLED;

    public static ForgeConfigSpec.ConfigValue<Integer> MAXIMUM_HEIGHTMAP_CHANGES;
    public static ForgeConfigSpec.ConfigValue<Integer> STARFALL_SAFETY_CHECK_RANGE;
    public static ForgeConfigSpec.ConfigValue<Integer> STARFALL_MAXIMUM_FAILURES;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> STARFALL_ALLOWED_LEVELS;
    public static ForgeConfigSpec.ConfigValue<Integer> MINIMUM_STARFALL_DISTANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> MAXIMUM_STARFALL_DISTANCE;

    public static ForgeConfigSpec.DoubleValue ASTEROID_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> MAXIMUM_ASTEROID_COUNT;
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

        MAXIMUM_HEIGHTMAP_CHANGES = builder.comment("If a chunk is modified enough by a player, it and neighboring chunks are marked as invalid for starfalls. How many heightmap modifications are needed to invalidate a chunk?")
                .define("maximum_heightmap_changes", 50);

        STARFALL_SAFETY_CHECK_RANGE = builder.comment("How many blocks around the starfall impact are checked for blocks placed most likely by a player?")
                .define("starfall_safety_range", 20);

        STARFALL_MAXIMUM_FAILURES = builder.comment("A starfall can potentially fail to find a valid place to fall, how many times will it try again?")
                .define("starfall_maximum_failures", 8);

        STARFALL_ALLOWED_LEVELS = builder
                .comment("Which dimensions can starfalls take place in?")
                .defineList("starfall_permitted_dimensions", new ArrayList<>(List.of("minecraft:overworld")),
                        s -> s instanceof String);

        MINIMUM_STARFALL_DISTANCE = builder.comment("What's the minimum distance away from a player in which a starfall can occur?")
                .define("minimum_starfall_distance", 64);

        MAXIMUM_STARFALL_DISTANCE = builder.comment("What's the maximum distance away from a player in which a starfall can occur?")
                .define("maximum_starfall_distance", 256);

        builder.comment("Asteroids").push("asteroids");
        ASTEROID_CHANCE = builder.comment("Asteroids are scheduled to fall alongside space debris. What's the chance for this to happen?")
                .defineInRange("asteroid_fall_chance", 0.6, 0, 1.0);

        MAXIMUM_ASTEROID_COUNT = builder.comment("What's the maximum possible amount of asteroids to schedule? Each time an asteroid is spawned the chance for another one is lowered.")
                .define("maximum_asteroid_count", 2);

        MINIMUM_ASTEROID_COUNTDOWN_MULTIPLIER = builder.comment("Asteroids fall sometime after the space debris. How close to the space debris starfall can the asteroid starfall be?")
                .defineInRange("minimum_asteroid_countdown_multiplier", 0.1, 0.01, 1.5);

        MAXIMUM_ASTEROID_COUNTDOWN_MULTIPLIER = builder.comment("Asteroids fall sometime after the space debris. How close to the next space debris starfall can the asteroid starfall be?")
                .defineInRange("maximum_asteroid_countdown_multiplier", 0.95, 0.01, 1.5);
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