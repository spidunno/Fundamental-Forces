package com.sammy.fufo.common.packets;

import com.sammy.fufo.common.capability.FufoPlayerDataCapability;
import com.sammy.ortus.network.packet.OrtusSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class FufoPlayerCapabilitySyncPacket extends OrtusSyncPacket {
    private final UUID uuid;
    public static final String STRING_ID ="SyncEnterCapabilityPacket";
    public FufoPlayerCapabilitySyncPacket(CompoundTag tag) {
        super(tag);
        this.uuid = tag.getUUID(STRING_ID);
    }
    public FufoPlayerCapabilitySyncPacket(UUID uuid, CompoundTag tag) {
        super(handleTag(uuid,tag));
        this.uuid = uuid;
    }
    public static CompoundTag handleTag(UUID id,CompoundTag tag){
        tag.putUUID(STRING_ID,id);
        return tag;
    }

    @Override
    public CompoundTag getClientTag(Supplier<NetworkEvent.Context> context) {
        assert Minecraft.getInstance().level != null;
        Player player = Minecraft.getInstance().level.getPlayerByUUID(uuid);
        assert player != null;
        return FufoPlayerDataCapability.getCapability(player).orElse(new FufoPlayerDataCapability()).serializeNBT();

    }

    @Override
    public CompoundTag getServerTag(Supplier<NetworkEvent.Context> context) {
        Player player = Objects.requireNonNull(context.get().getSender()).level.getPlayerByUUID(uuid);
        assert player != null;
        return FufoPlayerDataCapability.getCapability(player).orElse(new FufoPlayerDataCapability()).serializeNBT();
    }

    @Override
    public void modifyClient(Supplier<NetworkEvent.Context> context, CompoundTag tag) {
        assert Minecraft.getInstance().level != null;
        Player player = Minecraft.getInstance().level.getPlayerByUUID(uuid);
        assert player != null;
        FufoPlayerDataCapability.getCapability(player).ifPresent(c -> c.deserializeNBT(tag));

    }

    @Override
    public void modifyServer(Supplier<NetworkEvent.Context> context, CompoundTag tag) {
        Player player = Objects.requireNonNull(context.get().getSender()).level.getPlayerByUUID(uuid);
        assert player != null;
        FufoPlayerDataCapability.getCapability(player).ifPresent(c -> c.deserializeNBT(tag));
    }
}
