package com.project_esoterica.esoterica.core.setup;

import com.project_esoterica.esoterica.common.packets.SyncEntityCapabilityDataPacket;
import com.project_esoterica.esoterica.common.packets.SyncPlayerCapabilityDataPacket;
import com.project_esoterica.esoterica.common.packets.SyncPlayerCapabilityDataServerPacket;
import com.project_esoterica.esoterica.common.packets.SyncWorldEventPacket;
import com.project_esoterica.esoterica.common.packets.screenshake.PositionedScreenshakePacket;
import com.project_esoterica.esoterica.common.packets.screenshake.ScreenshakePacket;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.project_esoterica.esoterica.EsotericaMod.MODID;
import static com.project_esoterica.esoterica.core.helper.DataHelper.prefix;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PacketRegistry {
    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(prefix("main"), () -> PacketRegistry.PROTOCOL_VERSION, PacketRegistry.PROTOCOL_VERSION::equals, PacketRegistry.PROTOCOL_VERSION::equals);

    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void registerPackets(FMLCommonSetupEvent event) {
        int index = 0;
        ScreenshakePacket.register(INSTANCE, index++);
        PositionedScreenshakePacket.register(INSTANCE, index++);
        SyncWorldEventPacket.register(INSTANCE, index++);
        SyncPlayerCapabilityDataPacket.register(INSTANCE, index++);
        SyncPlayerCapabilityDataServerPacket.register(INSTANCE, index++);
        SyncEntityCapabilityDataPacket.register(INSTANCE, index++);
    }
}