package team.lodestar.fufo.common.packets;

import team.lodestar.fufo.common.capability.FufoEntityDataCapability;
import team.lodestar.lodestone.systems.network.LodestoneTwoWayNBTPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

@SuppressWarnings("ConstantConditions")
public class FufoEntityCapabilitySyncPacket extends LodestoneTwoWayNBTPacket {
    public static final String ENTITY_ID = "entity_id";

    private final int entityID;

    public FufoEntityCapabilitySyncPacket(CompoundTag tag) {
        super(tag);
        this.entityID = tag.getInt(ENTITY_ID);
    }

    public FufoEntityCapabilitySyncPacket(int id, CompoundTag tag) {
        super(handleTag(id, tag));
        this.entityID = id;
    }

    public static CompoundTag handleTag(int id, CompoundTag tag) {
        tag.putInt(ENTITY_ID, id);
        return tag;
    }

    @Override
    public void clientExecute(Supplier<NetworkEvent.Context> context, CompoundTag tag) {
        Entity entity = Minecraft.getInstance().level.getEntity(entityID);
        FufoEntityDataCapability.getCapabilityOptional(entity).ifPresent(c -> c.deserializeNBT(tag));
    }

    @Override
    public void serverExecute(Supplier<NetworkEvent.Context> context, CompoundTag tag) {
        Entity entity = context.get().getSender().level.getEntity(entityID);
        FufoEntityDataCapability.getCapabilityOptional(entity).ifPresent(c -> c.deserializeNBT(tag));
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, FufoEntityCapabilitySyncPacket.class, FufoEntityCapabilitySyncPacket::encode, FufoEntityCapabilitySyncPacket::decode, FufoEntityCapabilitySyncPacket::handle);
    }

    public static FufoEntityCapabilitySyncPacket decode(FriendlyByteBuf buf) {
        return new FufoEntityCapabilitySyncPacket(buf.readNbt());
    }
}