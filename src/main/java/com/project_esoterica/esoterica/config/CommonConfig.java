package com.project_esoterica.esoterica.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class CommonConfig {

    public static ForgeConfigSpec.BooleanValue STARFALLS_ENABLED;

    public static ForgeConfigSpec.BooleanValue UNSAFE_STARFALLS;

    public static ForgeConfigSpec.ConfigValue<Integer> MAXIMUM_CHUNK_CHANGES;
    public static ForgeConfigSpec.ConfigValue<Integer> STARFALL_SAFETY_RANGE;
    public static ForgeConfigSpec.ConfigValue<Integer> STARFALL_MAXIMUM_TRIES;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> STARFALL_ALLOWED_DIMENSIONS;
    public static ForgeConfigSpec.ConfigValue<Integer> STARFALL_SPAWN_HEIGHT;
    public static ForgeConfigSpec.ConfigValue<Integer> MINIMUM_STARFALL_DISTANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> MAXIMUM_STARFALL_DISTANCE;

    public static ForgeConfigSpec.DoubleValue ASTEROID_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> MAXIMUM_ASTEROID_AMOUNT;
    public static ForgeConfigSpec.DoubleValue MINIMUM_ASTEROID_TIME_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue MAXIMUM_ASTEROID_TIME_MULTIPLIER;

    public static ForgeConfigSpec.ConfigValue<Integer> MINIMUM_METEOR_FLAME_COST;
    public static ForgeConfigSpec.ConfigValue<Integer> MAXIMUM_METEOR_FLAME_COST;

    public static ForgeConfigSpec.ConfigValue<Integer> INITIAL_DEBRIS_COUNTDOWN;
    public static ForgeConfigSpec.ConfigValue<Integer> NATURAL_DEBRIS_COUNTDOWN;
    public static ForgeConfigSpec.DoubleValue MINIMUM_DEBRIS_COUNTDOWN_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue MAXIMUM_DEBRIS_COUNTDOWN_MULTIPLIER;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("World Events").push("world_events");

        builder.comment("Starfalls").push("starfalls");

        STARFALLS_ENABLED = builder.comment("Are starfalls enabled?")
                .define("enable_starfalls", true);

        UNSAFE_STARFALLS = builder.comment("If enabled, starfalls will fall wherever they please, disregarding any OSHA regulations.")
                .define("unsafe_starfalls", false);

        MAXIMUM_CHUNK_CHANGES = builder.comment("If a chunk is modified enough by a player, it and neighboring chunks are marked as invalid for starfalls. How many chunk changes are needed to invalidate a chunk?")
                .define("maximum_chunk_changes", 50);

        STARFALL_SAFETY_RANGE = builder.comment("How many blocks around the starfall impact are checked for blocks placed most likely by a player?")
                .define("starfall_safety_range", 20);

        STARFALL_MAXIMUM_TRIES = builder.comment("A starfall can potentially fail to find a valid place to fall, how many times will it try again?")
                .define("starfall_maximum_failures", 8);

        STARFALL_ALLOWED_DIMENSIONS = builder
                .comment("Which dimensions can starfalls take place in?")
                .defineList("starfall_permitted_dimensions", new ArrayList<>(List.of("minecraft:overworld")),
                        s -> s instanceof String);

        STARFALL_SPAWN_HEIGHT = builder.comment("How many blocks above the surface do starfalls spawn?")
                .define("starfall_spawn_height", 600);

        MINIMUM_STARFALL_DISTANCE = builder.comment("What's the minimum distance away from a player in which a starfall can occur?")
                .define("minimum_starfall_distance", 64);

        MAXIMUM_STARFALL_DISTANCE = builder.comment("What's the maximum distance away from a player in which a starfall can occur?")
                .define("maximum_starfall_distance", 256);

        builder.comment("Asteroids").push("asteroids");
        ASTEROID_CHANCE = builder.comment("Asteroids are scheduled to fall alongside space debris. What's the chance for this to happen?")
                .defineInRange("asteroid_fall_chance", 0.6, 0, 1.0);

        MAXIMUM_ASTEROID_AMOUNT = builder.comment("What's the maximum possible amount of asteroids to schedule? Each time an asteroid is spawned the chance for another one is lowered.")
                .define("maximum_asteroid_count", 4);

        MINIMUM_ASTEROID_TIME_MULTIPLIER = builder.comment("Asteroids fall sometime after the space debris. How close to the space debris starfall can the asteroid starfall be?")
                .defineInRange("minimum_asteroid_countdown_multiplier", 0.1, 0, Integer.MAX_VALUE);

        MAXIMUM_ASTEROID_TIME_MULTIPLIER = builder.comment("Asteroids fall sometime after the space debris. How close to the next space debris starfall can the asteroid starfall be?")
                .defineInRange("maximum_asteroid_countdown_multiplier", 0.95, 0, Integer.MAX_VALUE);

        MINIMUM_METEOR_FLAME_COST = builder.comment("What's the lowest amount of durability needed to light a meteor rock on fire?")
                .define("minimum_durability_meteor_flame_cost", 8);

        MAXIMUM_METEOR_FLAME_COST = builder.comment("What's the highest amount of durability needed to light a meteor rock on fire?")
                .define("maximum_durability_meteor_flame_cost", 14);


        builder.pop();

        builder.comment("Space Debris").push("space_debris");

        INITIAL_DEBRIS_COUNTDOWN = builder.comment("What's the base countdown for the very first space debris starfall?")
                .define("initial_space_debris_countdown", 100);

        NATURAL_DEBRIS_COUNTDOWN = builder.comment("What's the base countdown for every subsequent space debris starfall?")
                .define("natural_space_debris_countdown", 500);

        MINIMUM_DEBRIS_COUNTDOWN_MULTIPLIER = builder.comment("What's the minimum multiplier for a space debris starfall countdown?")
                .defineInRange("minimum_asteroid_countdown_multiplier", 0.85, 0, Integer.MAX_VALUE);

        MAXIMUM_DEBRIS_COUNTDOWN_MULTIPLIER = builder.comment("What's the maximum multiplier for a space debris starfall countdown?")
                .defineInRange("maximum_asteroid_countdown_multiplier", 1.25, 0, Integer.MAX_VALUE);
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