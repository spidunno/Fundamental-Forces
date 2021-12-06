package com.project_esoterica.esoterica.core.systems.worldevent;

import com.project_esoterica.esoterica.common.packets.AddWorldEventToClientPacket;
import com.project_esoterica.esoterica.core.registry.misc.PacketRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.UUID;

public abstract class WorldEventInstance {
    public UUID uuid;
    public String type;
    public boolean invalidated;

    public WorldEventInstance(WorldEventType type) {
        this.uuid = UUID.randomUUID();
        this.type = type.id;
    }

    public void start(ServerLevel level) {

    }

    public void tick(ServerLevel level) {

    }

    public void end(ServerLevel level) {
        invalidated = true;
    }

    public boolean existsOnClient()
    {
        return false;
    }
    public void addToClient() {
        PacketRegistry.INSTANCE.send(PacketDistributor.ALL.noArg(), new AddWorldEventToClientPacket(type, true, serializeNBT(new CompoundTag())));
    }

    public void addToClient(ServerPlayer player) {
        PacketRegistry.INSTANCE.send(PacketDistributor.PLAYER.with(()->player), new AddWorldEventToClientPacket(type, false, serializeNBT(new CompoundTag())));
    }

    public void clientStart(Level level) {

    }

    public void clientTick(Level level) {

    }

    public void clientEnd(Level level) {
        invalidated = true;
    }

    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putUUID("uuid", uuid);
        tag.putString("type", type);
        tag.putBoolean("invalidated", invalidated);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        uuid = tag.getUUID("uuid");
        type = tag.getString("type");
        invalidated = tag.getBoolean("invalidated");
    }
}