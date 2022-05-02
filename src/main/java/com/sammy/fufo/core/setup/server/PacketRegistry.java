package com.sammy.fufo.core.setup.server;

import com.sammy.fufo.common.packets.SyncFufoEntityCapabilityDataPacket;
import com.sammy.fufo.common.packets.SyncFufoPlayerCapabilityDataPacket;
import com.sammy.fufo.common.packets.SyncPlayerCapabilityDataServerPacket;
import com.sammy.fufo.common.packets.spell.UpdateCooldownPacket;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.sammy.fufo.FufoMod.FUFO;
import static com.sammy.fufo.FufoMod.prefix;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = FUFO, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PacketRegistry {
    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(prefix("main"), () -> PacketRegistry.PROTOCOL_VERSION, PacketRegistry.PROTOCOL_VERSION::equals, PacketRegistry.PROTOCOL_VERSION::equals);

    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void registerPackets(FMLCommonSetupEvent event) {
        int index = 0;
        SyncFufoPlayerCapabilityDataPacket.register(INSTANCE, index++);
        SyncPlayerCapabilityDataServerPacket.register(INSTANCE, index++);
        SyncFufoEntityCapabilityDataPacket.register(INSTANCE, index++);
        UpdateCooldownPacket.register(INSTANCE, index++);
    }
}