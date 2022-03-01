package com.sammy.fundamental_forces.core.setup.server;

import com.sammy.fundamental_forces.common.packets.*;
import com.sammy.fundamental_forces.common.packets.interaction.ResetRightClickDelayPacket;
import com.sammy.fundamental_forces.common.packets.interaction.RightClickEmptyPacket;
import com.sammy.fundamental_forces.common.packets.interaction.UpdateLeftClickPacket;
import com.sammy.fundamental_forces.common.packets.interaction.UpdateRightClickPacket;
import com.sammy.fundamental_forces.common.packets.screenshake.PositionedScreenshakePacket;
import com.sammy.fundamental_forces.common.packets.screenshake.ScreenshakePacket;
import com.sammy.fundamental_forces.common.packets.spell.UpdateCooldownPacket;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.sammy.fundamental_forces.FundamentalForcesMod.MODID;
import static com.sammy.fundamental_forces.core.helper.DataHelper.prefix;

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
        RightClickEmptyPacket.register(INSTANCE, index++);
        UpdateLeftClickPacket.register(INSTANCE, index++);
        UpdateRightClickPacket.register(INSTANCE, index++);
        ResetRightClickDelayPacket.register(INSTANCE, index++);
        UpdateCooldownPacket.register(INSTANCE, index++);
    }
}