package com.sammy.fufo.common.entity.wisp;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class WispEntity extends Entity {

    public WispEntity nearestEntity;
    public int findNearestCooldown;

    public WispEntity(EntityType<?> p_19870, Level p_19871_) {
        super(p_19870, p_19871_);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData() {

    }


    @Override
    public void tick() {
        super.tick();
        if (findNearestCooldown == 0) {
//            if (this.level instanceof ServerLevel) {
//                WispEntity nearest = level.getNearestEntity(EntityTypeTest.forClass(WispEntity.class), this.getBoundingBox().inflate(0.5D), this::canMerge);
//                for(WispEntity otherWisp : this.level.getNearestEntity(EntityTypeTest.forClass(WispEntity.class), this.getBoundingBox().inflate(0.5D), this::canMerge)) {
//                    this.merge(otherWisp);
//                }
//            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}