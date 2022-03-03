package com.sammy.fundamental_forces.common.worldevents.starfall;

import com.sammy.fundamental_forces.core.setup.content.worldevent.StarfallActors;
import com.sammy.fundamental_forces.core.setup.content.worldevent.WorldEventTypes;
import com.sammy.fundamental_forces.core.systems.screenshake.PositionedScreenshakeInstance;
import com.sammy.fundamental_forces.core.handlers.ScreenshakeHandler;
import com.sammy.fundamental_forces.core.systems.worldevent.WorldEventInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FallingStarfallEvent extends WorldEventInstance {
    public static final float ACCELERATION = 0.01f;

    public StarfallActor actor;
    public BlockPos targetedPos = BlockPos.ZERO;
    public Vec3 position = Vec3.ZERO;
    public Vec3 motion = Vec3.ZERO;
    public float speed;

    private FallingStarfallEvent() {
        super(WorldEventTypes.FALLING_STARFALL);
    }

    public FallingStarfallEvent(StarfallActor actor) {
        super(WorldEventTypes.FALLING_STARFALL);
        this.actor = actor;
    }

    public static FallingStarfallEvent fromNBT(CompoundTag tag) {
        FallingStarfallEvent instance = new FallingStarfallEvent();
        instance.deserializeNBT(tag);
        return instance;
    }

    public FallingStarfallEvent startPosition(Vec3 position) {
        this.position = position;
        return this;
    }

    public FallingStarfallEvent motion(Vec3 motion) {
        this.motion = motion;
        return this;
    }

    public FallingStarfallEvent targetPosition(BlockPos targetedPos) {
        this.targetedPos = targetedPos;
        return this;
    }

    @Override
    public void tick(Level level) {
        move();
        if (position.y() <= targetedPos.getY()) {
            end(level);
        }
    }

    @Override
    public void end(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            actor.act(serverLevel, targetedPos);
        }
        else
        {
            ScreenshakeHandler.addScreenshake(new PositionedScreenshakeInstance(position, 80, 200, 0.85f, 0.04f, 40, 0.01f, 0.04f));
        }
        discarded = true;
    }

    @Override
    public boolean isClientSynced() {
        return true;
    }


    private void move() {
        position = position.add(motion.multiply(speed, speed, speed));
        speed += ACCELERATION;
    }

    @Override
    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putString("resultId", actor.id);
        tag.putIntArray("targetedPos", new int[]{targetedPos.getX(), targetedPos.getY(), targetedPos.getZ()});
        tag.putDouble("posX", position.x());
        tag.putDouble("posY", position.y());
        tag.putDouble("posZ", position.z());
        tag.putDouble("motionX", motion.x());
        tag.putDouble("motionY", motion.y());
        tag.putDouble("motionZ", motion.z());
        tag.putFloat("speed", speed);
        return super.serializeNBT(tag);
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        actor = StarfallActors.ACTORS.get(tag.getString("resultId"));
        int[] positions = tag.getIntArray("targetedPos");
        targetedPos = new BlockPos(positions[0], positions[1], positions[2]);
        position = new Vec3(tag.getDouble("posX"), tag.getDouble("posY"), tag.getDouble("posZ"));
        motion = new Vec3(tag.getDouble("motionX"), tag.getDouble("motionY"), tag.getDouble("motionZ"));
        speed = tag.getFloat("speed");
        super.deserializeNBT(tag);
    }
}