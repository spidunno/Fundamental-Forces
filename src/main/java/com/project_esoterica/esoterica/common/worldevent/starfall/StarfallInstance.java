package com.project_esoterica.esoterica.common.worldevent.starfall;

import com.project_esoterica.esoterica.core.config.CommonConfig;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventActivator;
import com.project_esoterica.esoterica.core.registry.worldevent.StarfallResults;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.UUID;

public class StarfallInstance extends WorldEventInstance {
    public final StarfallResult result;
    @Nullable
    public final UUID targetedUUID;
    public LivingEntity targetedEntity;
    public BlockPos targetedPos;
    public int startingCountdown;
    public int countdown;
    protected boolean loop;
    protected boolean determined;

    public StarfallInstance(StarfallResult result, LivingEntity targetedEntity, int startingCountdown) {
        this(result, targetedEntity.getUUID(), targetedEntity.getOnPos(), startingCountdown);
    }

    public StarfallInstance(StarfallResult result, BlockPos targetedPos, int startingCountdown) {
        this(result, null, targetedPos, startingCountdown);
    }

    public StarfallInstance(StarfallResult result, ServerLevel level, LivingEntity targetedEntity) {
        this(result, targetedEntity.getUUID(), targetedEntity.getOnPos(), result.randomizedCountdown(level.random));
    }

    public StarfallInstance(StarfallResult result, ServerLevel level, BlockPos targetedPos) {
        this(result, null, targetedPos, result.randomizedCountdown(level.random));
    }

    public StarfallInstance(StarfallResult result, @Nullable UUID targetedUUID, BlockPos targetedPos, int startingCountdown) {
        this.result = result;
        this.targetedUUID = targetedUUID;
        this.targetedPos = targetedPos;
        this.startingCountdown = startingCountdown;
        this.countdown = startingCountdown;
    }

    public StarfallInstance randomizeCountdown(ServerLevel level, int parentCountdown) {
        this.startingCountdown = result.randomizedCountdown(level.random, parentCountdown);
        return this;
    }

    public StarfallInstance setLooping() {
        this.loop = true;
        return this;
    }

    public StarfallInstance setDetermined() {
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
//        if (isEntityValid(level)) {
//            targetedEntity.sendMessage(new TextComponent("" + countdown), targetedUUID);
//        }
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
                BlockPos randomizedTarget = result.randomizedStarfallPosition(level, targetedPos);
                boolean success = result.canFall(level, randomizedTarget);
                if (success) {
                    result.fall(level, randomizedTarget);
                    break;
                } else {
                    failures++;
                    if (failures >= maximumFailures) {
                        break;
                    }
                }
            }
        }
        else
        {
            BlockPos randomizedTarget = result.randomizedStarfallPosition(level, targetedPos);
            boolean success = result.canFall(level, randomizedTarget);
            if (success) {
                result.fall(level, randomizedTarget);
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
        tag.putBoolean("invalidated", invalidated);
        tag.putInt("countdown", countdown);
        tag.putBoolean("loop", loop);
        tag.putBoolean("determined", determined);
    }

    public static StarfallInstance deserializeNBT(CompoundTag tag) {
        StarfallResult result = StarfallResults.STARFALL_RESULTS.get(tag.getString("resultId"));
        UUID targetedUUID = tag.getUUID("targetedUUID");
        int[] positions = tag.getIntArray("pos");
        BlockPos targetedPos = new BlockPos(positions[0], positions[1], positions[2]);
        int startingCountdown = tag.getInt("startingCountdown");
        StarfallInstance instance = new StarfallInstance(result, targetedUUID, targetedPos, startingCountdown);
        instance.invalidated = tag.getBoolean("invalidated");
        instance.countdown = tag.getInt("countdown");
        instance.loop = tag.getBoolean("loop");
        instance.determined = tag.getBoolean("determined");
        return instance;
    }
}