package com.space_mod_group.space_mod.common.worldevent.starfall;

import com.space_mod_group.space_mod.common.worldevent.WorldEventActivator;
import com.space_mod_group.space_mod.common.worldevent.WorldEventManager;
import com.space_mod_group.space_mod.core.registry.worldevent.StarfallResults;
import com.space_mod_group.space_mod.core.systems.worldevent.WorldEventInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
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
    public boolean loop;

    public StarfallInstance(StarfallResult result,ServerLevel level, LivingEntity targetedEntity) {
        this(result, targetedEntity.getUUID(), targetedEntity.getOnPos(), result.randomizeCountdown(level.random));
    }

    public StarfallInstance(StarfallResult result, ServerLevel level,BlockPos targetedPos) {
        this(result,null, targetedPos, result.randomizeCountdown(level.random));
    }

    public StarfallInstance(StarfallResult result,@Nullable UUID targetedUUID, BlockPos targetedPos, int startingCountdown) {
        this.result = result;
        this.targetedUUID = targetedUUID;
        this.targetedPos = targetedPos;
        this.startingCountdown = startingCountdown;
        this.countdown = startingCountdown;
    }
    public StarfallInstance randomizeCountdown(ServerLevel level, int parentCountdown)
    {
        this.startingCountdown = result.randomizeCountdown(level.random, parentCountdown);
        return this;
    }
    public StarfallInstance setLooping()
    {
        this.loop = true;
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
        if (isEntityValid(level)) {
            targetedEntity.sendMessage(new TextComponent("" + countdown), targetedUUID);
        }
        if (countdown <= 0) {
            end(level);
        }
    }

    @Override
    public void end(ServerLevel level) {
        boolean success = canFall(level);
        if (success) {
            result.fall(level, targetedPos);
        }
        if (loop && isEntityValid(level))
        {
            WorldEventActivator.addSpaceDebris(level, targetedEntity, true);
        }
        super.end(level);
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
        tag.putBoolean("loop", loop);
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
        instance.loop = tag.getBoolean("loop");
        return instance;
    }
}