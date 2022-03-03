package com.sammy.fundamental_forces.core.systems.worldevent;

import com.sammy.fundamental_forces.common.packets.SyncWorldEventPacket;
import com.sammy.fundamental_forces.core.setup.server.PacketRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

import java.util.UUID;

public abstract class WorldEventInstance {
    public UUID uuid;
    public String type;
    public boolean discarded;

    public WorldEventInstance(WorldEventType type) {
        this.uuid = UUID.randomUUID();
        this.type = type.id;
    }

    public void start(Level level) {
        if (!level.isClientSide && isClientSynced()) {
            sync(this);
        }
    }

    public void tick(Level level) {

    }

    public void end(Level level) {
        discarded = true;
    }

    public boolean isClientSynced() {
        return false;
    }

    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putUUID("uuid", uuid);
        tag.putString("type", type);
        tag.putBoolean("discarded", discarded);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        uuid = tag.getUUID("uuid");
        type = tag.getString("type");
        discarded = tag.getBoolean("discarded");
    }

    public static <T extends WorldEventInstance> void sync(T instance) {
        PacketRegistry.INSTANCE.send(PacketDistributor.ALL.noArg(), new SyncWorldEventPacket(instance.type, true, instance.serializeNBT(new CompoundTag())));
    }

    public static <T extends WorldEventInstance> void sync(T instance, ServerPlayer player) {
        PacketRegistry.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SyncWorldEventPacket(instance.type, false, instance.serializeNBT(new CompoundTag())));
    }
}