package com.sammy.fufo.common.packets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public abstract class SemiPacketHandler {
    protected record DataHolder(CompoundTag data) {}

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, DataHolder.class, SemiPacketHandler::encode, SemiPacketHandler::decode, SemiPacketHandler::handle);
    }

    protected static void handle(DataHolder data, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be thread-safe (most work)
            ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
            execute(data,ctx);
        });
        ctx.get().setPacketHandled(true);
    }
    public abstract void execute(DataHolder data,Supplier<NetworkEvent.Context> ctx);

    protected static DataHolder decode(FriendlyByteBuf friendlyByteBuff){
        return new DataHolder(friendlyByteBuff.readNbt());
    }

    protected static void encode(DataHolder data,FriendlyByteBuf friendlyByteBuf){

    }
}
