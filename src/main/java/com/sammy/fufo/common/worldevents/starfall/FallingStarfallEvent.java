package com.sammy.fufo.common.worldevents.starfall;

import com.mojang.math.Vector3f;
import com.sammy.fufo.client.renderers.postprocess.EnergyScanFx;
import com.sammy.fufo.client.renderers.postprocess.EnergySphereFx;
import com.sammy.fufo.config.CommonConfig;
import com.sammy.fufo.core.setup.client.FufoPostProcessorRegistry;
import com.sammy.fufo.core.setup.content.worldevent.StarfallActors;
import com.sammy.fufo.core.setup.content.worldevent.WorldEventTypes;
import com.sammy.ortus.helpers.EntityHelper;
import com.sammy.ortus.systems.worldevent.WorldEventInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class FallingStarfallEvent extends WorldEventInstance {

    public StarfallActor actor;
    public final ArrayList<EntityHelper.PastPosition> pastPositions = new ArrayList<>();
    public BlockPos targetedPos = BlockPos.ZERO;
    public Vec3 position = Vec3.ZERO;
    public Vec3 positionOld = Vec3.ZERO;
    public Vec3 motion = Vec3.ZERO;
    public float acceleration = 5.f;
    public float speed;
    public int startingHeight;
    public int atmosphericEntryHeight;

    public FallingStarfallEvent() {
        super(WorldEventTypes.FALLING_STARFALL);
    }

    public FallingStarfallEvent(StarfallActor actor, Vec3 position, Vec3 motion, BlockPos targetedPos) {
        this();
        this.actor = actor;
        this.startingHeight = (int) position.y;
        this.atmosphericEntryHeight = startingHeight - CommonConfig.STARFALL_SPAWN_HEIGHT.getConfigValue() + CommonConfig.STARFALL_ATMOSPHERE_ENTRY_HEIGHT.getConfigValue();
        this.position = position;
        this.motion = motion;
        this.targetedPos = targetedPos;
    }

    @Override
    public void tick(Level level) {
        move();
        trackPastPositions();
        if (position.y() <= targetedPos.getY()) {
            end(level);
        }
    }

    @Override
    public void end(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            actor.act(serverLevel, targetedPos);
        } else {
//            ScreenshakeHandler.addScreenshake(new PositionedScreenshakeInstance(position, 80, 200, 0.85f, 0.04f, 40, 0.01f, 0.04f));

            playImpactEffect(new Vector3f(Vec3.atCenterOf(targetedPos)));
        }
        discarded = true;
    }

    private void playImpactEffect(Vector3f position) {
        FufoPostProcessorRegistry.ENERGY_SCAN.addFxInstance(new EnergyScanFx(position) {
            @Override
            public void update(double deltaTime) {
                super.update(deltaTime);

                virtualRadius = getTime() / 7.5F * 300F;
                if (virtualRadius > 600F) {
                    remove();
                    return;
                }
            }
        });

        FufoPostProcessorRegistry.ENERGY_SPHERE.addFxInstance(new EnergySphereFx(position, 0, 1) {
            @Override
            public void update(double deltaTime) {
                super.update(deltaTime);

                float progress = getTime() / 7.5F;

                if (progress > 1) {
                    remove();
                    return;
                }

                this.radius = progress * 300F;
                this.intensity = (200F - radius) / 190F;
                this.intensity = (float) Mth.clamp(intensity, 0., 1.);
            }
        });
    }

    @Override
    public boolean isClientSynced() {
        return true;
    }

    private void move() {
        positionOld = position;
        speed += acceleration;
        position = position.add(motion.multiply(speed, speed, speed));
    }

    public void trackPastPositions() {
        EntityHelper.trackPastPositions(pastPositions, position, 0.01f);
        removeOldPositions(pastPositions);
    }

    public void removeOldPositions(ArrayList<EntityHelper.PastPosition> pastPositions) {
        int amount = pastPositions.size() - 1;
        ArrayList<EntityHelper.PastPosition> toRemove = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            EntityHelper.PastPosition excess = pastPositions.get(i);
            if (excess.time > 30) {
                toRemove.add(excess);
            }
        }
        pastPositions.removeAll(toRemove);
    }

    @Override
    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putString("actorId", actor.id);
        tag.putIntArray("targetedPos", new int[]{targetedPos.getX(), targetedPos.getY(), targetedPos.getZ()});
        tag.putDouble("posX", position.x());
        tag.putDouble("posY", position.y());
        tag.putDouble("posZ", position.z());
        tag.putDouble("motionX", motion.x());
        tag.putDouble("motionY", motion.y());
        tag.putDouble("motionZ", motion.z());
        tag.putFloat("speed", speed);
        tag.putFloat("acceleration", acceleration);
        tag.putInt("startingHeight", startingHeight);
        tag.putInt("atmosphericEntryHeight", atmosphericEntryHeight);
        return super.serializeNBT(tag);
    }

    @Override
    public FallingStarfallEvent deserializeNBT(CompoundTag tag) {
        actor = StarfallActors.ACTORS.get(tag.getString("actorId"));
        int[] positions = tag.getIntArray("targetedPos");
        targetedPos = new BlockPos(positions[0], positions[1], positions[2]);
        position = new Vec3(tag.getDouble("posX"), tag.getDouble("posY"), tag.getDouble("posZ"));
        motion = new Vec3(tag.getDouble("motionX"), tag.getDouble("motionY"), tag.getDouble("motionZ"));
        speed = tag.getFloat("speed");
        acceleration = tag.getFloat("acceleration");
        startingHeight = tag.getInt("startingHeight");
        atmosphericEntryHeight = tag.getInt("atmosphericEntryHeight");
        super.deserializeNBT(tag);
        return this;
    }
}