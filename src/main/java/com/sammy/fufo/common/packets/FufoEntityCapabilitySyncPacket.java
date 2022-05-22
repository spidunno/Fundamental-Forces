package com.sammy.fufo.common.packets;

import com.sammy.fufo.common.capability.FufoEntityDataCapability;
import com.sammy.ortus.network.packet.OrtusSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FufoEntityCapabilitySyncPacket extends OrtusSyncPacket {
    public static final String STRING_ID ="SyncEntityCapabilityPacket";
    private final int entityID;
    public FufoEntityCapabilitySyncPacket(CompoundTag tag) {
        super(tag);
        this.entityID = tag.getInt(STRING_ID);
    }
    public FufoEntityCapabilitySyncPacket(int id, CompoundTag tag) {
        super(handleTag(id,tag));
        this.entityID = id;
    }
    public static CompoundTag handleTag(int id,CompoundTag tag){
        tag.putInt(STRING_ID,id);
        return tag;
    }

    @Override
    public CompoundTag getClientTag(Supplier<NetworkEvent.Context> context) {
        assert Minecraft.getInstance().level != null;
        Entity entity = Minecraft.getInstance().level.getEntity(entityID);
        assert entity != null;
        return FufoEntityDataCapability.getCapability(entity).orElse(new FufoEntityDataCapability()).serializeNBT();
    }

    @Override
    public CompoundTag getServerTag(Supplier<NetworkEvent.Context> context) {
        return null;
    }

    @Override
    public void modifyClient(Supplier<NetworkEvent.Context> context, CompoundTag tag) {
        assert Minecraft.getInstance().level != null;
        Entity entity = Minecraft.getInstance().level.getEntity(entityID);
        assert entity != null;
        FufoEntityDataCapability.getCapability(entity).ifPresent(c -> c.deserializeNBT(tag));

    }

    @Override
    public void modifyServer(Supplier<NetworkEvent.Context> context, CompoundTag tag) {
    }
}
