package com.project_esoterica.empirical_esoterica.core.eventhandlers;

import com.project_esoterica.empirical_esoterica.EmpiricalEsoterica;
import com.project_esoterica.empirical_esoterica.common.capability.ChunkDataCapability;
import com.project_esoterica.empirical_esoterica.core.systems.screenshake.ScreenshakeHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientRuntimeEvents {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        ScreenshakeHandler.clientTick(EmpiricalEsoterica.RANDOM);
    }
}