package com.space_mod_group.space_mod.core.systems.starfall;

import com.space_mod_group.space_mod.SpaceMod;
import com.space_mod_group.space_mod.common.starfall.StarfallManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.UUID;

public class StarfallInstance {
    public final StarfallResult result;
    @Nullable
    public final UUID targetedUUID;
    public LivingEntity targetedEntity;
    public BlockPos targetedPos;
    public final int startingCountdown;
    public int countdown;

    public StarfallInstance(StarfallResult result, UUID targetedUUID, int startingCountdown) {
        this(result, targetedUUID, null, startingCountdown);
    }

    public StarfallInstance(StarfallResult result, BlockPos targetedPos, int startingCountdown) {
        this(result, null, targetedPos, startingCountdown);
    }

    public StarfallInstance(StarfallResult result, @Nullable UUID targetedUUID, BlockPos targetedPos, int startingCountdown) {
        this.result = result;
        this.targetedUUID = targetedUUID;
        this.targetedPos = targetedPos;
        this.startingCountdown = startingCountdown;
        this.countdown = startingCountdown;
    }
    public void tick(ServerLevel level) {
        if (level.getGameTime() % 100L == 0) {
            if (isEntityValid(level)) {
                targetedPos = targetedEntity.getOnPos();
            }
        }
        countdown--;
        if (countdown <= 0) {
            fall(level);
        }
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

    public void serializeNBT(CompoundTag tag, int x) {
        CompoundTag instanceTag = new CompoundTag();
        instanceTag.putInt("resultId", result.id);
        if (targetedUUID != null)
        {
            instanceTag.putUUID("targetedUUID", targetedUUID);
        }
        instanceTag.putIntArray("pos", new int[]{targetedPos.getX(), targetedPos.getY(), targetedPos.getZ()});
        instanceTag.putInt("startingCountdown", startingCountdown);
        instanceTag.putInt("countdown", countdown);
        tag.put("compound_" + x, tag);
    }

    public static StarfallInstance deserializeNBT(CompoundTag nbt, int x) {
        CompoundTag instanceTag = nbt.getCompound("compound_" + x);
        StarfallResult result = StarfallManager.STARFALL_RESULTS.get(instanceTag.getInt("resultId"));
        UUID targetedUUID = instanceTag.getUUID("targetedUUID");
        int[] positions = instanceTag.getIntArray("pos");
        BlockPos targetedPos = new BlockPos(positions[0], positions[1], positions[2]);
        int startingCountdown = instanceTag.getInt("startingCountdown");
        int countdown = instanceTag.getInt("countdown");
        StarfallInstance instance = new StarfallInstance(result, targetedUUID,targetedPos,startingCountdown);
        instance.countdown = countdown;

        return instance;
    }
    public boolean isEntityValid(ServerLevel level)
    {
        if (targetedEntity == null && targetedUUID != null)
        {
            targetedEntity = (LivingEntity) level.getEntity(targetedUUID);
        }
        return targetedEntity != null && targetedEntity.isAlive();
    }
}