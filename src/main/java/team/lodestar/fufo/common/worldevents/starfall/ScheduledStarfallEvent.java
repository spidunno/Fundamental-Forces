package team.lodestar.fufo.common.worldevents.starfall;

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

import javax.annotation.Nullable;
import java.util.UUID;

public class ScheduledStarfallEvent extends WorldEventInstance {

    public StarfallActor actor;
    @Nullable
    public UUID targetedUUID;
    public LivingEntity targetedEntity;
    public BlockPos targetedPos;
    public int startingCountdown;
    public int countdown;
    public int timesDelayed;
    protected boolean loop;
    protected boolean determined;
    protected boolean exactPosition;

    public ScheduledStarfallEvent() {
        super(FufoWorldEventTypes.SCHEDULED_STARFALL);
    }

    public ScheduledStarfallEvent(StarfallActor actor) {
        super(FufoWorldEventTypes.SCHEDULED_STARFALL);
        this.actor = actor;
    }

    public ScheduledStarfallEvent targetEntity(LivingEntity target) {
        this.targetedUUID = target.getUUID();
        this.targetedEntity = target;
        this.targetedPos = target.getOnPos();
        return this;
    }

    public ScheduledStarfallEvent targetPosition(BlockPos targetedPos) {
        this.targetedPos = targetedPos;
        return this;
    }

    public ScheduledStarfallEvent targetExactPosition(BlockPos targetedPos) {
        this.targetedPos = targetedPos;
        this.exactPosition = true;
        return this;
    }

    public ScheduledStarfallEvent randomizedStartingCountdown(ServerLevel level, int parentCountdown) {
        return exactStartingCountdown(actor.randomizedCountdown(level.random, parentCountdown));
    }

    public ScheduledStarfallEvent randomizedStartingCountdown(ServerLevel level) {
        return exactStartingCountdown(actor.randomizedCountdown(level.random));
    }

    public ScheduledStarfallEvent exactStartingCountdown(int startingCountdown) {
        this.startingCountdown = startingCountdown;
        this.countdown = startingCountdown;
        return this;
    }

    public ScheduledStarfallEvent looping() {
        this.loop = true;
        return this;
    }

    public ScheduledStarfallEvent determined() {
        this.determined = true;
        return this;
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
                countdown = actor.randomizedCountdown(level.random, startingCountdown / 10);
                timesDelayed++;
                return;
            }
            boolean disregardOSHARegulations = CommonConfig.UNSAFE_STARFALLS.getConfigValue();
            if (determined) {
                int failures = 0;
                int maximumFailures = CommonConfig.STARFALL_MAXIMUM_TRIES.getConfigValue();
                while (true) {
                    if (failures >= maximumFailures) {
                        break;
                    }
                    BlockPos target = exactPosition ? targetedPos : actor.randomizedStarfallTargetPosition(serverLevel, targetedPos);
                    if (target == null) {
                        failures++;
                        continue;
                    }
                    boolean success = disregardOSHARegulations || exactPosition || actor.canFall(serverLevel, target);
                    if (success) {
                        Vec3 spawnPos = actor.randomizedStarfallStartPosition(serverLevel, target, targetedPos);
                        Vec3 motion = spawnPos.vectorTo(new Vec3(target.getX(), target.getY(), target.getZ())).normalize();
                        WorldEventHandler.addWorldEvent(level, new FallingStarfallEvent(actor, spawnPos, motion, target));
                        break;
                    } else {
                        failures++;
                    }
                }
            } else {
                BlockPos target = exactPosition ? targetedPos : actor.randomizedStarfallTargetPosition(serverLevel, targetedPos);
                if (target != null) {
                    boolean success = disregardOSHARegulations || exactPosition || actor.canFall(serverLevel, target);
                    if (success) {
                        Vec3 targetVec = new Vec3(target.getX(), target.getY(), target.getZ());
                        Vec3 spawnPos = new Vec3(targetedPos.getX(), targetedPos.getY(), targetedPos.getZ()).add(Mth.nextDouble(level.random, -150, 150), CommonConfig.STARFALL_SPAWN_HEIGHT.getConfigValue(), Mth.nextDouble(level.random, -150, 150));
                        Vec3 motion = spawnPos.vectorTo(targetVec).normalize();
                        WorldEventHandler.addWorldEvent(level, new FallingStarfallEvent(actor, spawnPos, motion, target));
                    }
                }
            }
            if (loop && isEntityValid(serverLevel)) {
                addNaturalStarfall(serverLevel, targetedEntity);
            }
            super.end(level);
        }
    }

    public boolean isEntityValid(ServerLevel level) {
        if (targetedEntity == null && targetedUUID != null) {
            targetedEntity = (LivingEntity) level.getEntity(targetedUUID);
        }
        return targetedEntity != null && targetedEntity.isAlive() && targetedEntity.level.equals(level);
    }

    @Override
    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putString("resultId", actor.id);
        if (targetedUUID != null) {
            tag.putUUID("targetedUUID", targetedUUID);
        }
        tag.putIntArray("targetedPos", new int[]{targetedPos.getX(), targetedPos.getY(), targetedPos.getZ()});
        tag.putInt("startingCountdown", startingCountdown);
        tag.putInt("countdown", countdown);
        tag.putInt("timesDelayed", timesDelayed);
        tag.putBoolean("loop", loop);
        tag.putBoolean("determined", determined);
        tag.putBoolean("exactPosition", exactPosition);
        return super.serializeNBT(tag);
    }

    @Override
    public ScheduledStarfallEvent deserializeNBT(CompoundTag tag) {
        actor = FufoStarfallActors.ACTORS.get(tag.getString("resultId"));
        targetedUUID = tag.getUUID("targetedUUID");
        int[] positions = tag.getIntArray("targetedPos");
        targetedPos = new BlockPos(positions[0], positions[1], positions[2]);
        startingCountdown = tag.getInt("startingCountdown");
        countdown = tag.getInt("countdown");
        timesDelayed = tag.getInt("timesDelayed");
        loop = tag.getBoolean("loop");
        determined = tag.getBoolean("determined");
        exactPosition = tag.getBoolean("exactPosition");
        super.deserializeNBT(tag);
        return this;
    }

    public static void addMissingStarfall(ServerLevel level, Player player) {
        LodestoneWorldDataCapability.getCapabilityOptional(level).ifPresent(capability -> {
            boolean isMissingStarfall = true;
            for (WorldEventInstance instance : capability.activeWorldEvents) {
                if (instance instanceof ScheduledStarfallEvent scheduledStarfallEvent) {
                    if (player.getUUID().equals(scheduledStarfallEvent.targetedUUID)) {
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

    public static void addNaturalStarfall(ServerLevel level, LivingEntity entity) {
        if (areStarfallsAllowed(level)) {
            ScheduledStarfallEvent debrisInstance = WorldEventHandler.addWorldEvent(level, new ScheduledStarfallEvent(FufoStarfallActors.SPACE_DEBRIS).targetEntity(entity).randomizedStartingCountdown(level).looping().determined());
            Double chance = CommonConfig.ASTEROID_CHANCE.getConfigValue();
            int maxAsteroids = CommonConfig.MAXIMUM_ASTEROID_AMOUNT.getConfigValue();
            for (int i = 0; i < maxAsteroids; i++) {
                if (level.random.nextFloat() < chance) {
                    WorldEventHandler.addWorldEvent(level, new ScheduledStarfallEvent(FufoStarfallActors.ASTEROID).targetEntity(entity).randomizedStartingCountdown(level, debrisInstance.startingCountdown).determined());
                    chance *= 0.8f;
                } else {
                    break;
                }
            }
        }
    }

    public static boolean areStarfallsAllowed(ServerLevel level) {
        return CommonConfig.STARFALLS_ENABLED.getConfigValue() && CommonConfig.STARFALL_ALLOWED_DIMENSIONS.getConfigValue().contains(level.dimension().location().toString());
    }
}