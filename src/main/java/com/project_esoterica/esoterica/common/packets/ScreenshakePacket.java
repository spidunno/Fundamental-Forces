package com.project_esoterica.esoterica.common.packets;

import com.project_esoterica.esoterica.core.systems.screenshake.ScreenshakeHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

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

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ScreenshakeHandler.addScreenshake(factor, falloff));
        context.get().setPacketHandled(true);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ScreenshakePacket.class, ScreenshakePacket::encode, ScreenshakePacket::decode, ScreenshakePacket::execute);
    }
}
