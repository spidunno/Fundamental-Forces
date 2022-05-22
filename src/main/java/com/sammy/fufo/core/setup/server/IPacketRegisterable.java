package com.sammy.fufo.core.setup.server;

import com.sammy.ortus.network.packet.OrtusPacket;
import net.minecraftforge.network.simple.SimpleChannel;

public interface IPacketRegisterable {
    public static <T extends OrtusPacket> void register(Class<T> type, SimpleChannel instance, int index) {
        instance.registerMessage(index, type, T::encode, T::decode, T::handle);
    }
}
