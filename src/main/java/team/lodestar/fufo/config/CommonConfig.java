package team.lodestar.fufo.config;

import team.lodestar.lodestone.systems.config.LodestoneConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static team.lodestar.fufo.FufoMod.FUFO;

public class CommonConfig extends LodestoneConfig {

    public static final LodestoneConfig.ConfigValueHolder<Boolean> STARFALLS_ENABLED = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/starfalls", (builder) ->
            builder.comment("Are impact events enabled?")
                    .define("enable_starfalls", true));

    public static final LodestoneConfig.ConfigValueHolder<Boolean> IMPATIENT_STARFALLS = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/starfalls", (builder) ->
            builder.comment("If enabled, impact events will not give a damn about the player's position when trying to fall.")
                    .define("impatient_starfalls", false));

    public static final LodestoneConfig.ConfigValueHolder<Boolean> UNSAFE_STARFALLS = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/starfalls", (builder) ->
            builder.comment("If enabled, impact events will skip the following safety checks, as well as disregarding any OSHA regulations")
                    .define("unsafe_starfalls", false));

    public static final LodestoneConfig.ConfigValueHolder<Integer> MAXIMUM_CHUNK_CHANGES = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/starfalls", (builder) ->
            builder.comment("Number of player-made changes to a chunk to mark it as unsafe for an impact event to land on")
                    .define("maximum_chunk_changes", 50));

    public static final LodestoneConfig.ConfigValueHolder<Integer> STARFALL_SAFETY_RANGE = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/starfalls", (builder) ->
            builder.comment("Number of blocks around the impact site to be checked for blocks that may be placed by a player or structure")
                    .define("starfall_safety_range", 20));

    public static final LodestoneConfig.ConfigValueHolder<Integer> STARFALL_MAXIMUM_TRIES = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/starfalls", (builder) ->
            builder.comment("Number of retries if an impact event fails to find a valid location to fall")
                    .define("starfall_maximum_failures", 8));

    public static final LodestoneConfig.ConfigValueHolder<List<? extends String>> STARFALL_ALLOWED_DIMENSIONS = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/starfalls", (builder) ->
            builder.comment("Dimensions impact events can take place in")
                    .defineList("starfall_permitted_dimensions", new ArrayList<>(List.of("minecraft:overworld")), s -> s instanceof String));

    public static final LodestoneConfig.ConfigValueHolder<Integer> STARFALL_SPAWN_HEIGHT = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/starfalls", (builder) ->
            builder.comment("Altitude for impact events to spawn their incoming objects")
                    .define("starfall_spawn_height", 600));

    public static final LodestoneConfig.ConfigValueHolder<Integer> STARFALL_ATMOSPHERE_ENTRY_HEIGHT = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/starfalls", (builder) ->
            builder.comment("Altitude for impact event objects to enter the atmosphere")
                    .define("starfall_atmosphere_entry_height", 150));

    public static final LodestoneConfig.ConfigValueHolder<Integer> MINIMUM_STARFALL_DISTANCE = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/starfalls", (builder) ->
            builder.comment("Minimum distance away from a player to spawn an impact event")
                    .define("minimum_starfall_distance", 64));

    public static final LodestoneConfig.ConfigValueHolder<Integer> MAXIMUM_STARFALL_DISTANCE = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/starfalls", (builder) ->
            builder.comment("Maximum distance away from a player to spawn an impact event")
                    .define("maximum_starfall_distance", 256));

    public static final LodestoneConfig.ConfigValueHolder<Double> ASTEROID_CHANCE = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/asteroids", (builder) ->
            builder.comment("Chance for a space debris impact to come with a certain amount of accompanying asteroids directly after")
                    .defineInRange("asteroid_fall_chance", 0.6, 0, 1.0));

    public static final LodestoneConfig.ConfigValueHolder<Integer> MAXIMUM_ASTEROID_AMOUNT = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/asteroids", (builder) ->
            builder.comment("Maximum possible amount of asteroids to schedule. Chance for extra asteroids to spawn decreases as they are scheduled.")
                    .define("maximum_asteroid_count", 4));

    public static final LodestoneConfig.ConfigValueHolder<Double> MINIMUM_ASTEROID_TIME_MULTIPLIER = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/asteroids", (builder) ->
            builder.comment("Minimum cooldown multiplier for asteroid impacts that directly follow space debris impacts")
                    .defineInRange("minimum_asteroid_countdown_multiplier", 0.1, 0, Integer.MAX_VALUE));

    public static final LodestoneConfig.ConfigValueHolder<Double> MAXIMUM_ASTEROID_TIME_MULTIPLIER = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/asteroids", (builder) ->
            builder.comment("Maximum cooldown multiplier for asteroid impacts that directly follow space debris impacts")
                    .defineInRange("maximum_asteroid_countdown_multiplier", 0.95, 0, Integer.MAX_VALUE));

    public static final LodestoneConfig.ConfigValueHolder<Integer> INITIAL_DEBRIS_COUNTDOWN = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/space_debris", (builder) ->
            builder.comment("Base countdown for the very first space debris impact event")
                    .define("initial_space_debris_countdown", 100));

    public static final LodestoneConfig.ConfigValueHolder<Integer> NATURAL_DEBRIS_COUNTDOWN = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/space_debris", (builder) ->
            builder.comment("Base countdown for every subsequent space debris impact events")
                    .define("natural_space_debris_countdown", 50000));

    public static final LodestoneConfig.ConfigValueHolder<Double> MINIMUM_DEBRIS_COUNTDOWN_MULTIPLIER = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/space_debris", (builder) ->
            builder.comment("Minimum multiplier for space debris impact event countdowns. Every time an impact event occurs, the countdown for the next one is generated and randomized between these two multipliers.")
                    .defineInRange("minimum_asteroid_countdown_multiplier", 0.85, 0, 10));

    public static final LodestoneConfig.ConfigValueHolder<Double> MAXIMUM_DEBRIS_COUNTDOWN_MULTIPLIER = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/world_events/space_debris", (builder) ->
            builder.comment("Maximum multiplier for a space debris impact event countdowns")
                    .defineInRange("maximum_asteroid_countdown_multiplier", 1.25, 0, 10));

    public static final LodestoneConfig.ConfigValueHolder<Integer> MINIMUM_METEOR_FLAME_COST = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/gameplay/meteor_flame", (builder) ->
            builder.comment("Minimum durability consumed to light a meteor rock on fire")
                    .define("minimum_durability_meteor_flame_cost", 8));

    public static final LodestoneConfig.ConfigValueHolder<Integer> MAXIMUM_METEOR_FLAME_COST = new LodestoneConfig.ConfigValueHolder<>(FUFO,"common/gameplay/meteor_flame", (builder) ->
            builder.comment("Maximum durability consumed to light a meteor rock on fire")
                    .define("maximum_durability_meteor_flame_cost", 14));

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        super(FUFO, "common", builder);
    }

    public static final CommonConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}