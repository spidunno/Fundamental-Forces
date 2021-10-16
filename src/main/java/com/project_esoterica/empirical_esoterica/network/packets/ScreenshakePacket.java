package com.project_esoterica.empirical_esoterica.network.packets;

import com.project_esoterica.empirical_esoterica.core.systems.screenshake.ScreenshakeHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class ScreenshakePacket {
    float factor;
    float falloff;

    public ScreenshakePacket(float factor, float falloff) {
        this.factor = factor;
        this.falloff = falloff;
    }

    public static ScreenshakePacket decode(FriendlyByteBuf buf) {
        return new ScreenshakePacket(buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(factor);
        buf.writeFloat(falloff);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ScreenshakeHandler.addScreenshake(factor, falloff));
        context.get().setPacketHandled(true);
    }
}
