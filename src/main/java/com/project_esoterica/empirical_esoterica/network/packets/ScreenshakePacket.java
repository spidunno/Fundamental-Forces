package com.project_esoterica.empirical_esoterica.network.packets;

import com.project_esoterica.empirical_esoterica.core.systems.screenshake.ScreenshakeHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class ScreenshakePacket {
    float factor;

    public ScreenshakePacket(float factor) {
        this.factor = factor;
    }

    public static ScreenshakePacket decode(FriendlyByteBuf buf) {
        return new ScreenshakePacket(buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(factor);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ScreenshakeHandler.addScreenshake(factor));
        context.get().setPacketHandled(true);
    }
}
