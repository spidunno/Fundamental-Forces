package com.sammy.fundamental_forces.common.entity.falling;

import com.sammy.fundamental_forces.core.setup.content.entity.EntityRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class FallingCrashpodEntity extends FallingEntity {
    public FallingCrashpodEntity(Level level) {
        super(EntityRegistry.FALLING_CRASHPOD.get(), level);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}