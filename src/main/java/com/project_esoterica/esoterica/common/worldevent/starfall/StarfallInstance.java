package com.project_esoterica.esoterica.common.worldevent.starfall;

import com.project_esoterica.esoterica.core.config.CommonConfig;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventActivator;
import com.project_esoterica.esoterica.core.registry.worldevent.StarfallResults;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventInstance;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventReader;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.UUID;

public class StarfallInstance extends WorldEventInstance {

    public static final String STARFALL_ID = "starfall";
    public static final WorldEventReader STARFALL_READER = new WorldEventReader(STARFALL_ID) {
        @Override
        public WorldEventInstance createInstance(CompoundTag tag) {
            return fromNBT(tag);
        }
    };

    public StarfallResult result;
    @Nullable
    public UUID targetedUUID;
    public LivingEntity targetedEntity;
    public BlockPos targetedPos;
    public int startingCountdown;
    public int countdown;
    protected boolean loop;
    protected boolean determined;
    protected boolean exactPosition;

    private StarfallInstance() {
        super(STARFALL_ID);
    }
    public StarfallInstance(StarfallResult result) {
        super(STARFALL_ID);
        this.result = result;
    }

    public static StarfallInstance fromNBT(CompoundTag tag) {
        StarfallInstance instance = new StarfallInstance();
        instance.deserializeNBT(tag);
        return instance;
    }

    public StarfallInstance targetEntity(LivingEntity target) {
        this.targetedUUID = target.getUUID();
        this.targetedEntity = target;
        this.targetedPos = target.getOnPos();
        return this;
    }

    public StarfallInstance targetPosition(BlockPos targetedPos) {
        this.targetedPos = targetedPos;
        return this;
    }

    public StarfallInstance targetExactPosition(BlockPos targetedPos) {
        this.targetedPos = targetedPos;
        this.exactPosition = true;
        return this;
    }

    public StarfallInstance randomizedStartingCountdown(ServerLevel level, int parentCountdown) {
        return exactStartingCountdown(result.randomizedCountdown(level.random, parentCountdown));
    }

    public StarfallInstance randomizedStartingCountdown(ServerLevel level) {
        return exactStartingCountdown(result.randomizedCountdown(level.random));
    }

    public StarfallInstance exactStartingCountdown(int startingCountdown) {
        this.startingCountdown = startingCountdown;
        this.countdown = startingCountdown;
        return this;
    }

    public StarfallInstance looping() {
        this.loop = true;
        return this;
    }

    public StarfallInstance determined() {
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
            while (true) {
                int failures = 0;
                int maximumFailures = CommonConfig.STARFALL_MAXIMUM_FAILURES.get();
                BlockPos target = exactPosition ? targetedPos : result.randomizedStarfallPosition(level, targetedPos);
                boolean success = exactPosition || result.canFall(level, target);
                if (success) {
                    result.fall(level, target);
                    break;
                } else {
                    failures++;
                    if (failures >= maximumFailures) {
                        break;
                    }
                }
            }
        } else {
            BlockPos target = exactPosition ? targetedPos : result.randomizedStarfallPosition(level, targetedPos);
            boolean success = exactPosition || result.canFall(level, target);
            if (success) {
                result.fall(level, target);
            }
        }
        if (loop && isEntityValid(level)) {
            WorldEventActivator.addSpaceDebris(level, targetedEntity, true);
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
        tag.putString("resultId", result.id);
        if (targetedUUID != null) {
            tag.putUUID("targetedUUID", targetedUUID);
        }
        tag.putIntArray("pos", new int[]{targetedPos.getX(), targetedPos.getY(), targetedPos.getZ()});
        tag.putInt("startingCountdown", startingCountdown);
        tag.putInt("countdown", countdown);
        tag.putBoolean("loop", loop);
        tag.putBoolean("determined", determined);
        tag.putBoolean("exactPosition", exactPosition);
        super.serializeNBT(tag);
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        result = StarfallResults.STARFALL_RESULTS.get(tag.getString("resultId"));
        targetedUUID = tag.getUUID("targetedUUID");
        int[] positions = tag.getIntArray("pos");
        targetedPos = new BlockPos(positions[0], positions[1], positions[2]);
        startingCountdown = tag.getInt("startingCountdown");
        countdown = tag.getInt("countdown");
        loop = tag.getBoolean("loop");
        determined = tag.getBoolean("determined");
        exactPosition = tag.getBoolean("exactPosition");
        super.deserializeNBT(tag);
    }
}