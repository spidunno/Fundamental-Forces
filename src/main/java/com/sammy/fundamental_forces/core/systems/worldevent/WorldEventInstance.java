package com.sammy.fundamental_forces.core.systems.worldevent;

import com.sammy.fundamental_forces.common.packets.SyncWorldEventPacket;
import com.sammy.fundamental_forces.core.setup.server.PacketRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
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

    public void start(ServerLevel level) {

    }

    public void tick(ServerLevel level) {

    }

    public void end(ServerLevel level) {
        discarded = true;
    }

    public boolean existsOnClient()
    {
        return false;
    }
    public void addToClient() {
        PacketRegistry.INSTANCE.send(PacketDistributor.ALL.noArg(), new SyncWorldEventPacket(type, true, serializeNBT(new CompoundTag())));
    }

    public void addToClient(ServerPlayer player) {
        PacketRegistry.INSTANCE.send(PacketDistributor.PLAYER.with(()->player), new SyncWorldEventPacket(type, false, serializeNBT(new CompoundTag())));
    }

    public void clientStart(Level level) {

    }

    public void clientTick(Level level) {

    }

    public void clientEnd(Level level) {
        discarded = true;
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
}