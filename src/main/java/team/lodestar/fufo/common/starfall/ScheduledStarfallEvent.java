package team.lodestar.fufo.common.starfall;

import org.checkerframework.checker.nullness.qual.NonNull;
import team.lodestar.fufo.common.starfall.StarfallData.StarfallDataBuilder;
import team.lodestar.fufo.config.CommonConfig;
import team.lodestar.fufo.registry.common.worldevent.FufoStarfallActors;
import team.lodestar.fufo.registry.common.worldevent.FufoWorldEventTypes;
import team.lodestar.lodestone.capability.LodestoneWorldDataCapability;
import team.lodestar.lodestone.handlers.WorldEventHandler;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ScheduledStarfallEvent extends WorldEventInstance {

    public final StarfallData starfallData;
    public LivingEntity targetedEntity;
    public BlockPos targetedPos;
    public int countdown;
    public int failures;

    public ScheduledStarfallEvent(StarfallData starfallData) {
        super(FufoWorldEventTypes.SCHEDULED_STARFALL);
        this.starfallData = starfallData;
    }

    public ScheduledStarfallEvent(StarfallData starfallData, BlockPos targetedPos) {
        this(starfallData);
        this.targetedPos = targetedPos;
    }

    @Override
    public CompoundTag serializeNBT(CompoundTag tag) {
        starfallData.serializeNBT(tag);
        tag.putIntArray("targetedPos", new int[]{targetedPos.getX(), targetedPos.getY(), targetedPos.getZ()});
        tag.putInt("countdown", countdown);
        tag.putInt("failures", failures);
        return super.serializeNBT(tag);
    }

    public static ScheduledStarfallEvent deserializeNBT(CompoundTag tag) {
        ScheduledStarfallEvent scheduledStarfallEvent = new ScheduledStarfallEvent(StarfallData.fromNBT(tag));
        int[] positions = tag.getIntArray("targetedPos");
        scheduledStarfallEvent.targetedPos = new BlockPos(positions[0], positions[1], positions[2]);
        scheduledStarfallEvent.countdown = tag.getInt("countdown");
        scheduledStarfallEvent.failures = tag.getInt("failures");
        return scheduledStarfallEvent;
    }

    @Override
    public void tick(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            if (level.getGameTime() % 100L == 0) {
                if (isEntityValid(serverLevel)) {
                    targetedPos = targetedEntity.getOnPos();
                }
            }
            countdown--;
            if (countdown <= 0) {
                end(level);
            }
        }
    }

    @Override
    public void end(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            if (!CommonConfig.IMPATIENT_STARFALLS.getConfigValue() && isEntityValid(serverLevel) && !targetedEntity.level.canSeeSky(targetedEntity.blockPosition())) {
                countdown = starfallData.actor.randomizeStartingCountdown(level.random, starfallData.startingCountdown / 10);
                failures++;
                return;
            }
            boolean disregardOSHARegulations = CommonConfig.UNSAFE_STARFALLS.getConfigValue();
            if (starfallData.determined) {
                int failures = 0;
                int maximumFailures = CommonConfig.STARFALL_MAXIMUM_TRIES.getConfigValue();
                while (true) {
                    if (failures >= maximumFailures) {
                        break;
                    }
                    BlockPos target = starfallData.precise ? targetedPos : starfallData.actor.randomizedStarfallTargetPosition(serverLevel, targetedPos);
                    if (target == null) {
                        failures++;
                        continue;
                    }
                    boolean success = disregardOSHARegulations || starfallData.precise || starfallData.actor.canFall(serverLevel, target);
                    if (success) {
                        Vec3 spawnPos = starfallData.actor.randomizedStarfallStartPosition(serverLevel, target, targetedPos);
                        Vec3 motion = spawnPos.vectorTo(new Vec3(target.getX(), target.getY(), target.getZ())).normalize();
                        WorldEventHandler.addWorldEvent(level, new FallingStarfallEvent(starfallData.actor, spawnPos, motion, target));
                        break;
                    } else {
                        failures++;
                    }
                }
            } else {
                BlockPos target = starfallData.precise ? targetedPos : starfallData.actor.randomizedStarfallTargetPosition(serverLevel, targetedPos);
                if (target != null) {
                    boolean success = disregardOSHARegulations || starfallData.precise || starfallData.actor.canFall(serverLevel, target);
                    if (success) {
                        Vec3 targetVec = new Vec3(target.getX(), target.getY(), target.getZ());
                        Vec3 spawnPos = new Vec3(targetedPos.getX(), targetedPos.getY(), targetedPos.getZ()).add(Mth.nextDouble(level.random, -150, 150), CommonConfig.STARFALL_SPAWN_HEIGHT.getConfigValue(), Mth.nextDouble(level.random, -150, 150));
                        Vec3 motion = spawnPos.vectorTo(targetVec).normalize();
                        WorldEventHandler.addWorldEvent(level, new FallingStarfallEvent(starfallData.actor, spawnPos, motion, target));
                    }
                }
            }
            if (starfallData.looping && isEntityValid(serverLevel)) {
                addNaturalStarfall(serverLevel, targetedEntity);
            }
            super.end(level);
        }
    }

    public boolean isEntityValid(ServerLevel level) {
        if (targetedEntity == null && starfallData.targetedUUID != null) {
            targetedEntity = (LivingEntity) level.getEntity(starfallData.targetedUUID);
        }
        return targetedEntity != null && targetedEntity.isAlive() && targetedEntity.level.equals(level);
    }

    public static void addMissingStarfall(ServerLevel level, Player player) {
        LodestoneWorldDataCapability.getCapabilityOptional(level).ifPresent(capability -> {
            boolean isMissingStarfall = true;
            for (WorldEventInstance instance : capability.activeWorldEvents) {
                if (instance instanceof ScheduledStarfallEvent scheduledStarfallEvent) {
                    if (player.getUUID().equals(scheduledStarfallEvent.starfallData.targetedUUID)) {
                        isMissingStarfall = false;
                        break;
                    }
                }
            }

            if (isMissingStarfall) {
                addNaturalStarfall(level, player);
            }
        });
    }

    public static void addNaturalStarfall(ServerLevel level, @NonNull LivingEntity entity) {
        if (areStarfallsAllowed(level)) {
            ScheduledStarfallEvent debrisInstance = WorldEventHandler.addWorldEvent(level, new ScheduledStarfallEvent(new StarfallDataBuilder(FufoStarfallActors.SPACE_DEBRIS, entity.getUUID(), a -> a.randomizeStartingCountdown(level.random)).setLooping().setDetermined().build()));
            Double chance = CommonConfig.ASTEROID_CHANCE.getConfigValue();
            int maxAsteroids = CommonConfig.MAXIMUM_ASTEROID_AMOUNT.getConfigValue();
            if (maxAsteroids > 0) {
                for (int i = 0; i < maxAsteroids; i++) {
                    if (level.random.nextFloat() < chance) {
                        WorldEventHandler.addWorldEvent(level, new ScheduledStarfallEvent(new StarfallDataBuilder(FufoStarfallActors.ASTEROID, entity.getUUID(), a -> a.randomizeStartingCountdown(level.random, debrisInstance.starfallData.startingCountdown)).setLooping().setDetermined().build()));
                        chance *= 0.8f;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public static boolean areStarfallsAllowed(ServerLevel level) {
        return CommonConfig.STARFALLS_ENABLED.getConfigValue() && CommonConfig.STARFALL_ALLOWED_DIMENSIONS.getConfigValue().contains(level.dimension().location().toString());
    }
}