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

        STARFALLS_ENABLED = builder.comment("Are impact events enabled?")
                .define("enable_starfalls", true);

        UNSAFE_STARFALLS = builder.comment("If enabled, impact events will skip the following safety checks, as well as disregarding any OSHA regulations")
                .define("unsafe_starfalls", false);

        MAXIMUM_CHUNK_CHANGES = builder.comment("Number of player-made changes to a chunk to mark it as unsafe for an impact event to land on")
                .define("maximum_chunk_changes", 50);

        STARFALL_SAFETY_RANGE = builder.comment("Number of blocks around the impact site to be checked for blocks that may be placed by a player")
                .define("starfall_safety_range", 20);

        STARFALL_MAXIMUM_TRIES = builder.comment("Number of retries if an impact event fails to find a valid location to fall")
                .define("starfall_maximum_failures", 8);

        STARFALL_ALLOWED_DIMENSIONS = builder
                .comment("Dimensions impact events can take place in")
                .defineList("starfall_permitted_dimensions", new ArrayList<>(List.of("minecraft:overworld")),
                        s -> s instanceof String);

        STARFALL_SPAWN_HEIGHT = builder.comment("Altitude for impact events to spawn their incoming objects")
                .define("starfall_spawn_height", 600);

        MINIMUM_STARFALL_DISTANCE = builder.comment("Minimum distance away from a player to spawn an impact event")
                .define("minimum_starfall_distance", 64);

        MAXIMUM_STARFALL_DISTANCE = builder.comment("Maximum distance away from a player to spawn an impact event")
                .define("maximum_starfall_distance", 256);

        builder.comment("Asteroids").push("asteroids");
        ASTEROID_CHANCE = builder.comment("Chance for a space debris impact to come with a certain amount of accompanying asteroids directly after")
                .defineInRange("asteroid_fall_chance", 0.6, 0, 1.0);

        MAXIMUM_ASTEROID_AMOUNT = builder.comment("Maximum possible amount of asteroids to schedule. Each time an asteroid is spawned the chance for another one is lowered")
                .define("maximum_asteroid_count", 4);

        MINIMUM_ASTEROID_TIME_MULTIPLIER = builder.comment("Minimum cooldown multiplier for asteroid impacts that directly follow space debris impacts")
                .defineInRange("minimum_asteroid_countdown_multiplier", 0.1, 0, Integer.MAX_VALUE);

        MAXIMUM_ASTEROID_TIME_MULTIPLIER = builder.comment("Maximum cooldown multiplier for asteroid impacts that directly follow space debris impacts")
                .defineInRange("maximum_asteroid_countdown_multiplier", 0.95, 0, Integer.MAX_VALUE);

        MINIMUM_METEOR_FLAME_COST = builder.comment("Minimum durability consumed to light a meteor rock on fire")
                .define("minimum_durability_meteor_flame_cost", 8);

        MAXIMUM_METEOR_FLAME_COST = builder.comment("Maximum durability consumed to light a meteor rock on fire")
                .define("maximum_durability_meteor_flame_cost", 14);


        builder.pop();

        builder.comment("Space Debris").push("space_debris");

        INITIAL_DEBRIS_COUNTDOWN = builder.comment("Base countdown for the very first space debris impact event")
                .define("initial_space_debris_countdown", 100);

        NATURAL_DEBRIS_COUNTDOWN = builder.comment("Base countdown for every subsequent space debris impact events")
                .define("natural_space_debris_countdown", 500);

        MINIMUM_DEBRIS_COUNTDOWN_MULTIPLIER = builder.comment("Minimum multiplier for space debris impact event countdowns")
                .defineInRange("minimum_asteroid_countdown_multiplier", 0.85, 0, Integer.MAX_VALUE);

        MAXIMUM_DEBRIS_COUNTDOWN_MULTIPLIER = builder.comment("Maximum multiplier for a space debris impact event countdowns")
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