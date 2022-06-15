package com.sammy.fufo.common.packets;

import java.util.function.Supplier;

import com.sammy.fufo.common.capability.FufoWorldDataCapability;
import com.sammy.ortus.systems.network.OrtusSyncPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent.Context;

public class FufoWorldCapabilitySyncPacket extends OrtusSyncPacket {

	public FufoWorldCapabilitySyncPacket(CompoundTag tag) {
		super(tag);
	}

	@Override
	public void modifyClient(Supplier<Context> context, CompoundTag tag) {
		Level world = Minecraft.getInstance().level;
		FufoWorldDataCapability.getCapability(world).ifPresent(c -> c.deserializeNBT(tag));
	}

	@Override
	public void modifyServer(Supplier<Context> context, CompoundTag tag) {
		// no-op
	}
}
