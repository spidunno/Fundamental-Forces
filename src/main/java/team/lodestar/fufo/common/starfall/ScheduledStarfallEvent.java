package team.lodestar.fufo.common.starfall;

import org.checkerframework.checker.nullness.qual.NonNull;
import team.lodestar.fufo.common.starfall.StarfallData.StarfallDataBuilder;
import team.lodestar.fufo.config.CommonConfig;
import team.lodestar.fufo.registry.common.worldevent.FufoStarfallActors;
import team.lodestar.fufo.registry.common.worldevent.FufoWorldEventTypes;
import team.lodestar.lodestone.capability.LodestoneWorldDataCapability;
import team.lodestar.lodestone.handlers.WorldEventHandler;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.helpers.DataHelper;
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
    public BlockPos targetedPos = BlockPos.ZERO;
    public int countdown;

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
        return super.serializeNBT(tag);
    }

    public static ScheduledStarfallEvent deserializeNBT(CompoundTag tag) {
        ScheduledStarfallEvent scheduledStarfallEvent = new ScheduledStarfallEvent(StarfallData.fromNBT(tag));
        int[] positions = tag.getIntArray("targetedPos");
        scheduledStarfallEvent.targetedPos = new BlockPos(positions[0], positions[1], positions[2]);
        scheduledStarfallEvent.countdown = tag.getInt("countdown");
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
                countdown = starfallData.startingCountdown / 20;
                return;
            }
            boolean disregardOSHARegulations = CommonConfig.UNSAFE_STARFALLS.getConfigValue();

            int failures = 0;
            int maximumFailures = starfallData.determined ? CommonConfig.STARFALL_MAXIMUM_TRIES.getConfigValue() : 1;
            while (true) {
                if (failures >= maximumFailures) {
                    break;
                }
                BlockPos actualTargetedPos = starfallData.precise ? targetedPos : starfallData.actor.getStarfallImpactPosition(serverLevel, targetedPos);
                if (actualTargetedPos == null) {
                    failures++;
                    continue;
                }
                boolean success = disregardOSHARegulations || starfallData.precise || starfallData.actor.canAct(serverLevel, actualTargetedPos);
                if (success) {
                    Vec3 spawnPos = starfallData.actor.getStarfallStartPosition(serverLevel, actualTargetedPos, targetedPos);
                    Vec3 motion = spawnPos.vectorTo(new Vec3(actualTargetedPos.getX(), actualTargetedPos.getY(), actualTargetedPos.getZ())).normalize();
                    WorldEventHandler.addWorldEvent(level, new FallingStarfallEvent(starfallData.actor, spawnPos, motion, actualTargetedPos));
                    if (starfallData.looping && isEntityValid(serverLevel)) {
                        callDownAsteroid(serverLevel, targetedEntity);
                    }
                    break;
                } else {
                    failures++;
                }
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

    public static void renewAsteroidStarfallLoop(ServerLevel level, Player player) {
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
                callDownAsteroid(level, player);
            }
        });
    }

    public static void callDownAsteroid(ServerLevel level, @NonNull LivingEntity entity) {
        if (areStarfallsAllowed(level)) {
            WorldEventHandler.addWorldEvent(level, new ScheduledStarfallEvent(new StarfallDataBuilder(FufoStarfallActors.ASTEROID, entity.getUUID(), a -> a.getStarfallCountdown(level.random)).setLooping().setDetermined().build()));
        }
    }

    public static boolean areStarfallsAllowed(ServerLevel level) {
        return CommonConfig.STARFALLS_ENABLED.getConfigValue() && CommonConfig.STARFALL_ALLOWED_DIMENSIONS.getConfigValue().contains(level.dimension().location().toString());
    }
}