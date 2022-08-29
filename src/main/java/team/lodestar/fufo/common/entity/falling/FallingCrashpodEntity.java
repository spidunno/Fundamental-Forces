package team.lodestar.fufo.common.entity.falling;

import team.lodestar.fufo.registry.common.FufoEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class FallingCrashpodEntity extends FallingEntity {
    public FallingCrashpodEntity(Level level) {
        super(FufoEntities.FALLING_CRASHPOD.get(), level);
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