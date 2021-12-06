package com.project_esoterica.esoterica.common.packets;

import com.project_esoterica.esoterica.core.registry.worldevent.WorldEventTypes;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventInstance;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventType;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class AddWorldEventToClientPacket {
    String type;
    public boolean start;
    public CompoundTag eventData;

    public AddWorldEventToClientPacket(String type, boolean start, CompoundTag eventData) {
        this.type = type;
        this.start = start;
        this.eventData = eventData;
    }

    public static AddWorldEventToClientPacket decode(FriendlyByteBuf buf) {
        return new AddWorldEventToClientPacket(buf.readUtf(), buf.readBoolean(), buf.readNbt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(type);
        buf.writeBoolean(start);
        buf.writeNbt(eventData);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ClientOnly.addWorldEvent(type, start, eventData));
        context.get().setPacketHandled(true);
    }
    public static class ClientOnly
    {
        public static void addWorldEvent(String type, boolean start, CompoundTag eventData)
        {
            WorldEventType eventType = WorldEventTypes.EVENT_TYPES.get(type);
            WorldEventInstance instance = WorldEventManager.addClientWorldEvent(Minecraft.getInstance().level, eventType.createInstance(eventData));
            if (start) {
                instance.clientStart(Minecraft.getInstance().level);
            }
        }
    }
}