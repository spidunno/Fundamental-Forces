package com.project_esoterica.esoterica.common.worldevents.starfall;

import com.project_esoterica.esoterica.common.capability.WorldDataCapability;
import com.project_esoterica.esoterica.core.config.CommonConfig;
import com.project_esoterica.esoterica.core.registry.worldevent.StarfallActors;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventInstance;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventReader;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public class StarfallEvent extends WorldEventInstance {

    public static final String STARFALL_ID = "starfall";

    static {
        new WorldEventReader(STARFALL_ID) {
            @Override
            public WorldEventInstance createInstance(CompoundTag tag) {
                return fromNBT(tag);
            }
        };
    }

    public StarfallActor actor;
    @Nullable
    public UUID targetedUUID;
    public LivingEntity targetedEntity;
    public BlockPos targetedPos;
    public int startingCountdown;
    public int countdown;
    protected boolean loop;
    protected boolean determined;
    protected boolean exactPosition;

    private StarfallEvent() {
        super(STARFALL_ID);
    }

    public StarfallEvent(StarfallActor actor) {
        super(STARFALL_ID);
        this.actor = actor;
    }

    public static StarfallEvent fromNBT(CompoundTag tag) {
        StarfallEvent instance = new StarfallEvent();
        instance.deserializeNBT(tag);
        return instance;
    }

    public StarfallEvent targetEntity(LivingEntity target) {
        this.targetedUUID = target.getUUID();
        this.targetedEntity = target;
        this.targetedPos = target.getOnPos();
        return this;
    }

    public StarfallEvent targetPosition(BlockPos targetedPos) {
        this.targetedPos = targetedPos;
        return this;
    }

    public StarfallEvent targetExactPosition(BlockPos targetedPos) {
        this.targetedPos = targetedPos;
        this.exactPosition = true;
        return this;
    }

    public StarfallEvent randomizedStartingCountdown(ServerLevel level, int parentCountdown) {
        return exactStartingCountdown(actor.randomizedCountdown(level.random, parentCountdown));
    }

    public StarfallEvent randomizedStartingCountdown(ServerLevel level) {
        return exactStartingCountdown(actor.randomizedCountdown(level.random));
    }

    public StarfallEvent exactStartingCountdown(int startingCountdown) {
        this.startingCountdown = startingCountdown;
        this.countdown = startingCountdown;
        return this;
    }

    public StarfallEvent looping() {
        this.loop = true;
        return this;
    }

    public StarfallEvent determined() {
        this.determined = true;
        return this;
    }

    @Override
    public void tick(ServerLevel level) {
        if (level.getGameTime() % 100L == 0) {
            if (isEntityValid(level)) {
                targetedPos = targetedEntity.getOnPos();
            }
        }
        countdown--;
        if (countdown <= 0) {
            end(level);
        }
    }

    @Override
    public void end(ServerLevel level) {
        if (determined) {
            int failures = 0;
            while (true) {
                int maximumFailures = CommonConfig.STARFALL_MAXIMUM_FAILURES.get();
                BlockPos target = exactPosition ? targetedPos : actor.randomizedStarfallPosition(level, targetedPos);
                if (targetedPos == null) {
                    failures++;
                    continue;
                }
                boolean success = exactPosition || actor.canFall(level, target);
                if (success) {
                    actor.fall(level, target);
                    break;
                } else {
                    failures++;
                    if (failures >= maximumFailures) {
                        break;
                    }
                }
            }
        } else {
            BlockPos target = exactPosition ? targetedPos : actor.randomizedStarfallPosition(level, targetedPos);
            if (target != null) {
                boolean success = exactPosition || actor.canFall(level, target);
                if (success) {
                    actor.fall(level, target);
                }
            }
        }
        if (loop && isEntityValid(level)) {
            addNaturalStarfall(level, targetedEntity, true);
        }
        super.end(level);
    }

    public boolean isEntityValid(ServerLevel level) {
        if (targetedEntity == null && targetedUUID != null) {
            targetedEntity = (LivingEntity) level.getEntity(targetedUUID);
        }
        return targetedEntity != null && targetedEntity.isAlive() && targetedEntity.level.equals(level);
    }

    @Override
    public void serializeNBT(CompoundTag tag) {
        tag.putString("resultId", actor.id);
        if (targetedUUID != null) {
            tag.putUUID("targetedUUID", targetedUUID);
        }
        tag.putIntArray("targetedPos", new int[]{targetedPos.getX(), targetedPos.getY(), targetedPos.getZ()});
        tag.putInt("startingCountdown", startingCountdown);
        tag.putInt("countdown", countdown);
        tag.putBoolean("loop", loop);
        tag.putBoolean("determined", determined);
        tag.putBoolean("exactPosition", exactPosition);
        super.serializeNBT(tag);
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        actor = StarfallActors.STARFALL_RESULTS.get(tag.getString("resultId"));
        targetedUUID = tag.getUUID("targetedUUID");
        int[] positions = tag.getIntArray("targetedPos");
        targetedPos = new BlockPos(positions[0], positions[1], positions[2]);
        startingCountdown = tag.getInt("startingCountdown");
        countdown = tag.getInt("countdown");
        loop = tag.getBoolean("loop");
        determined = tag.getBoolean("determined");
        exactPosition = tag.getBoolean("exactPosition");
        super.deserializeNBT(tag);
    }

    public static void addMissingStarfall(ServerLevel level, Player player) {
        WorldDataCapability.getCapability(level).ifPresent(capability -> {
            boolean isMissingStarfall = true;
            for (WorldEventInstance instance : capability.ACTIVE_WORLD_EVENTS) {
                if (instance instanceof StarfallEvent starfallEvent) {
                    if (player.getUUID().equals(starfallEvent.targetedUUID)) {
                        isMissingStarfall = false;
                        break;
                    }
                }
            }

            if (isMissingStarfall) {
                addNaturalStarfall(level, player, false);
            }
        });
    }

    public static void addNaturalStarfall(ServerLevel level, LivingEntity entity, boolean inbound) {
        if (areStarfallsAllowed(level)) {
            StarfallEvent debrisInstance = WorldEventManager.addWorldEvent(level, new StarfallEvent(StarfallActors.SPACE_DEBRIS).targetEntity(entity).randomizedStartingCountdown(level).looping().determined(), inbound);
            Double chance = CommonConfig.ASTEROID_CHANCE.get();
            int maxAsteroids = CommonConfig.MAXIMUM_ASTEROID_COUNT.get();
            for (int i = 0; i < maxAsteroids; i++) {
                if (level.random.nextFloat() < chance) {
                    WorldEventManager.addWorldEvent(level, new StarfallEvent(StarfallActors.ASTEROID).targetEntity(entity).randomizedStartingCountdown(level, debrisInstance.startingCountdown).determined(), inbound);
                    chance *= 0.8f;
                } else {
                    break;
                }
            }
        }
    }

    public static boolean areStarfallsAllowed(ServerLevel level) {
        return CommonConfig.STARFALLS_ENABLED.get() && CommonConfig.STARFALL_ALLOWED_LEVELS.get().contains(level.dimension().location().toString());
    }
}