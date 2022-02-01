package com.project_esoterica.esoterica.common.packets.interaction;

import com.project_esoterica.esoterica.core.eventhandlers.RuntimeEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class RightClickEmptyPacket {


    public RightClickEmptyPacket() {
    }

    public static RightClickEmptyPacket decode(FriendlyByteBuf buf) {
        return new RightClickEmptyPacket();
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> RuntimeEvents.serverSidePlayerInteract(context.get().getSender()));
        context.get().setPacketHandled(true);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, RightClickEmptyPacket.class, RightClickEmptyPacket::encode, RightClickEmptyPacket::decode, RightClickEmptyPacket::execute);
    }
}