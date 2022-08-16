package team.lodestar.fufo.common.packets;

import team.lodestar.fufo.common.capability.FufoPlayerDataCapability;
import team.lodestar.lodestone.systems.network.LodestoneTwoWayNBTPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.UUID;
import java.util.function.Supplier;

@SuppressWarnings("ConstantConditions")
public class FufoPlayerCapabilitySyncPacket extends LodestoneTwoWayNBTPacket {

    public static final String PLAYER_UUID = "player_uuid";

    private final UUID uuid;

    public FufoPlayerCapabilitySyncPacket(CompoundTag tag) {
        super(tag);
        this.uuid = tag.getUUID(PLAYER_UUID);
    }

    public FufoPlayerCapabilitySyncPacket(UUID uuid, CompoundTag tag) {
        super(handleTag(uuid, tag));
        this.uuid = uuid;
    }

    public static CompoundTag handleTag(UUID id, CompoundTag tag) {
        tag.putUUID(PLAYER_UUID, id);
        return tag;
    }

    @Override
    public void clientExecute(Supplier<NetworkEvent.Context> context, CompoundTag tag) {
        Player player = Minecraft.getInstance().level.getPlayerByUUID(uuid);
        FufoPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> c.deserializeNBT(tag));
    }

    @Override
    public void serverExecute(Supplier<NetworkEvent.Context> context, CompoundTag tag) {
        Player player = context.get().getSender().level.getPlayerByUUID(uuid);
        FufoPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> c.deserializeNBT(tag));
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, FufoPlayerCapabilitySyncPacket.class, FufoPlayerCapabilitySyncPacket::encode, FufoPlayerCapabilitySyncPacket::decode, FufoPlayerCapabilitySyncPacket::handle);
    }

    public static FufoPlayerCapabilitySyncPacket decode(FriendlyByteBuf buf) {
        return new FufoPlayerCapabilitySyncPacket(buf.readNbt());
    }
}