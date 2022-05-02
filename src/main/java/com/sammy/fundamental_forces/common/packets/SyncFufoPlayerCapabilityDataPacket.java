package com.sammy.fundamental_forces.common.packets;

import com.sammy.fundamental_forces.common.capability.FufoPlayerDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.UUID;
import java.util.function.Supplier;

public class SyncFufoPlayerCapabilityDataPacket {
    private final UUID uuid;
    private final CompoundTag tag;

    public SyncFufoPlayerCapabilityDataPacket(UUID uuid, CompoundTag tag) {
        this.uuid = uuid;
        this.tag = tag;
    }

    public static SyncFufoPlayerCapabilityDataPacket decode(FriendlyByteBuf buf) {
        return new SyncFufoPlayerCapabilityDataPacket(buf.readUUID(), buf.readNbt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeNbt(tag);
    }

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientOnly.syncData(uuid, tag);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SyncFufoPlayerCapabilityDataPacket.class, SyncFufoPlayerCapabilityDataPacket::encode, SyncFufoPlayerCapabilityDataPacket::decode, SyncFufoPlayerCapabilityDataPacket::execute);
    }

    public static class ClientOnly {
        public static void syncData(UUID uuid, CompoundTag tag) {
            Player player = Minecraft.getInstance().level.getPlayerByUUID(uuid);
            FufoPlayerDataCapability.getCapability(player).ifPresent(c -> c.deserializeNBT(tag));
        }
    }
}