package com.space_mod_group.space_mod.common.worldevent.starfall;

import com.space_mod_group.space_mod.common.worldevent.WorldEventManager;
import com.space_mod_group.space_mod.core.registry.worldevent.StarfallResults;
import com.space_mod_group.space_mod.core.systems.worldevent.WorldEventInstance;
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
    public final int startingCountdown;
    public int countdown;

    public StarfallInstance(StarfallResult result, LivingEntity targetedEntity) {
        this(result, targetedEntity.getUUID(), targetedEntity.getOnPos(), result.startingCountdown);
    }

    public StarfallInstance(StarfallResult result, BlockPos targetedPos) {
        this(result, null, targetedPos, result.startingCountdown);
    }

    public StarfallInstance(StarfallResult result, @Nullable UUID targetedUUID, BlockPos targetedPos, int startingCountdown) {
        this.result = result;
        this.targetedUUID = targetedUUID;
        this.targetedPos = targetedPos;
        this.startingCountdown = startingCountdown;
        this.countdown = startingCountdown;
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
            fall(level);
            end(level);
        }
    }

    @Override
    public void end(ServerLevel level) {
        if (isEntityValid(level))
        {
            WorldEventManager.addWorldEvent(level, new StarfallInstance(StarfallResults.DROP_POD, targetedEntity));
        }
        super.end(level);
    }

    public boolean fall(ServerLevel level) {
        boolean success = canFall(level);
        if (success) {
            result.fall(level, targetedPos);
        }
        return success;
    }

    public boolean canFall(ServerLevel level) {
        return true;
    }

    public boolean isEntityValid(ServerLevel level)
    {
        if (targetedEntity == null && targetedUUID != null)
        {
            targetedEntity = (LivingEntity) level.getEntity(targetedUUID);
        }
        return targetedEntity != null && targetedEntity.isAlive();
    }

    @Override
    public void serializeNBT(CompoundTag tag) {
        tag.putInt("resultId", result.id);
        if (targetedUUID != null)
        {
            tag.putUUID("targetedUUID", targetedUUID);
        }
        tag.putIntArray("pos", new int[]{targetedPos.getX(), targetedPos.getY(), targetedPos.getZ()});
        tag.putInt("startingCountdown", startingCountdown);
        tag.putBoolean("invalidated", invalidated);
        tag.putInt("countdown", countdown);
    }

    public static StarfallInstance deserializeNBT(CompoundTag tag) {
        StarfallResult result = StarfallResults.STARFALL_RESULTS.get(tag.getInt("resultId"));
        UUID targetedUUID = tag.getUUID("targetedUUID");
        int[] positions = tag.getIntArray("pos");
        BlockPos targetedPos = new BlockPos(positions[0], positions[1], positions[2]);
        int startingCountdown = tag.getInt("startingCountdown");
        StarfallInstance instance = new StarfallInstance(result, targetedUUID,targetedPos,startingCountdown);
        instance.invalidated = tag.getBoolean("invalidated");
        instance.countdown = tag.getInt("countdown");
        return instance;
    }
}