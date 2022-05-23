package com.sammy.fufo.common.packets;

import com.sammy.fufo.common.capability.FufoPlayerDataCapability;
import com.sammy.ortus.systems.network.OrtusSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

@SuppressWarnings("ConstantConditions")
public class FufoPlayerCapabilitySyncPacket extends OrtusSyncPacket {

    public static final String PLAYER_UUID = "player_uuid";

    private final UUID uuid;

    public FufoPlayerCapabilitySyncPacket(CompoundTag tag) {
        super(tag);
        if (!tag.contains(PLAYER_UUID)) {
            throw new RuntimeException("Created a packet instance without a target player.");
        }
        this.uuid = tag.getUUID(PLAYER_UUID);
    }

    public FufoPlayerCapabilitySyncPacket(UUID uuid, CompoundTag tag) {
        super(handleTag(uuid, tag));
        this.uuid = uuid;
    }

    public static CompoundTag handleTag(UUID id, CompoundTag tag) {
        tag.putUUID(PLAYER_UUID, id);
        return tag;
    }

    @Override
    public void modifyClient(Supplier<NetworkEvent.Context> context, CompoundTag tag) {
        Player player = Minecraft.getInstance().level.getPlayerByUUID(uuid);
        FufoPlayerDataCapability.getCapability(player).ifPresent(c -> c.deserializeNBT(tag));
    }

    @Override
    public void modifyServer(Supplier<NetworkEvent.Context> context, CompoundTag tag) {
        Player player = context.get().getSender().level.getPlayerByUUID(uuid);
        FufoPlayerDataCapability.getCapability(player).ifPresent(c -> c.deserializeNBT(tag));
    }
}