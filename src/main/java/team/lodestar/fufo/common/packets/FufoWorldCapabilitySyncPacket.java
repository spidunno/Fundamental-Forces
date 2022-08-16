package team.lodestar.fufo.common.packets;

import team.lodestar.fufo.common.capability.FufoWorldDataCapability;
import team.lodestar.lodestone.systems.network.LodestoneClientNBTPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class FufoWorldCapabilitySyncPacket extends LodestoneClientNBTPacket {

	public FufoWorldCapabilitySyncPacket(CompoundTag tag) {
		super(tag);
	}

	@Override
	public void execute(Supplier<Context> context, CompoundTag tag) {
		Level world = Minecraft.getInstance().level;
		FufoWorldDataCapability.getCapabilityOptional(world).ifPresent(c -> c.deserializeNBT(tag));
	}


	public static void register(SimpleChannel instance, int index) {
		instance.registerMessage(index, FufoWorldCapabilitySyncPacket.class, FufoWorldCapabilitySyncPacket::encode, FufoWorldCapabilitySyncPacket::decode, FufoWorldCapabilitySyncPacket::handle);
	}

	public static FufoWorldCapabilitySyncPacket decode(FriendlyByteBuf buf) {
		return new FufoWorldCapabilitySyncPacket(buf.readNbt());
	}
}