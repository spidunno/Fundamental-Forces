package com.project_esoterica.esoterica.common.packets;

import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventInstance;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventReader;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class AddWorldEventToClientPacket {
    String type;
    public CompoundTag eventData;
    public AddWorldEventToClientPacket(String type, CompoundTag eventData) {
        this.type = type;
        this.eventData = eventData;
    }

    public static AddWorldEventToClientPacket decode(FriendlyByteBuf buf) {
        return new AddWorldEventToClientPacket(buf.readUtf(), buf.readNbt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(type);
        buf.writeNbt(eventData);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            WorldEventReader reader = WorldEventManager.READERS.get(type);
            WorldEventInstance instance = WorldEventManager.addClientWorldEvent(Minecraft.getInstance().level, reader.createInstance(eventData));
            instance.clientStart(Minecraft.getInstance().level);
        });
        context.get().setPacketHandled(true);
    }
}
