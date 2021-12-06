package com.project_esoterica.esoterica.core.registry.misc;

import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.common.packets.AddWorldEventToClientPacket;
import com.project_esoterica.esoterica.common.packets.ScreenshakePacket;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

import static com.project_esoterica.esoterica.EsotericaMod.MOD_ID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PacketRegistry {
    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(EsotericaHelper.prefix("main"), () -> PacketRegistry.PROTOCOL_VERSION, PacketRegistry.PROTOCOL_VERSION::equals, PacketRegistry.PROTOCOL_VERSION::equals);

    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void registerPackets(FMLCommonSetupEvent event) {
        int index = 0;
        INSTANCE.registerMessage(index++, ScreenshakePacket.class, ScreenshakePacket::encode, ScreenshakePacket::decode, ScreenshakePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, AddWorldEventToClientPacket.class, AddWorldEventToClientPacket::encode, AddWorldEventToClientPacket::decode, AddWorldEventToClientPacket::whenThisPacketIsReceived);
    }
}