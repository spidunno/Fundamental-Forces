package com.project_esoterica.esoterica.common.entity.falling;

import com.project_esoterica.esoterica.core.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class FallingCrashpodEntity extends FallingEntity {
    public FallingCrashpodEntity(Level p_37249_, BlockPos targetBlockPos) {
        super(EntityRegistry.FALLING_CRASHPOD.get(), p_37249_, targetBlockPos);
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

    @Override
    protected void onImpact() {

    }
}