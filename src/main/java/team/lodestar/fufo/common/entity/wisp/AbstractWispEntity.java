package team.lodestar.fufo.common.entity.wisp;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import team.lodestar.lodestone.helpers.EntityHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWispEntity extends Entity {

    public static final int MAX_AGE = 1200;
    public int age;
    public final ArrayList<EntityHelper.PastPosition> pastPositions = new ArrayList<>();

    public AbstractWispEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public AbstractWispEntity(EntityType<?> type, Level level, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        this(type, level);
        setPos(posX, posY, posZ);
        setDeltaMovement(velX, velY, velZ);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("age", age);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        age = pCompound.getInt("age");
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 movement = getDeltaMovement();
        setPos(getX() + movement.x, getY() + movement.y, getZ() + movement.z);
        age++;
        if (level.isClientSide) {
            trackPastPositions();
            return;
        }
        if (age >= MAX_AGE) {
            discard();
        }
    }

    public void trackPastPositions() {
        EntityHelper.trackPastPositions(pastPositions, position(), 0.01f);
        removeOldPositions(pastPositions);
    }

    public void removeOldPositions(List<EntityHelper.PastPosition> pastPositions) {
        int amount = pastPositions.size() - 1;
        List<EntityHelper.PastPosition> toRemove = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            EntityHelper.PastPosition excess = pastPositions.get(i);
            if (excess.time > Math.min(age/2f, 25)) {
                toRemove.add(excess);
            }
        }
        pastPositions.removeAll(toRemove);
    }


    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}