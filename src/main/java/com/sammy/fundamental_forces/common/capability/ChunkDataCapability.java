package com.sammy.fundamental_forces.common.capability;

import com.sammy.fundamental_forces.config.CommonConfig;
import com.sammy.fundamental_forces.core.helper.DataHelper;
import com.sammy.fundamental_forces.core.systems.capability.SimpleCapability;
import com.sammy.fundamental_forces.core.systems.capability.SimpleCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class ChunkDataCapability implements SimpleCapability {

    //shove all chunk data here, use ChunkDataCapability.getCapability(chunk) to access data.

    public static Capability<ChunkDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public int chunkChanges;

    public ChunkDataCapability() {
    }
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ChunkDataCapability.class);
    }
    public static void attachChunkCapability(AttachCapabilitiesEvent<LevelChunk> event)
    {
        final ChunkDataCapability capability = new ChunkDataCapability();
        event.addCapability(DataHelper.prefix("chunk_data"), new SimpleCapabilityProvider<>(ChunkDataCapability.CAPABILITY, () -> capability));
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (chunkChanges >= CommonConfig.MAXIMUM_CHUNK_CHANGES.get()) {
            tag.putBoolean("invalid", true);
        } else {
            tag.putInt("chunkChanges", chunkChanges);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        chunkChanges = tag.getBoolean("invalid") ? CommonConfig.MAXIMUM_CHUNK_CHANGES.get() : tag.getInt("chunkChanges");
    }

    public static LazyOptional<ChunkDataCapability> getCapability(LevelChunk chunk) {
        return chunk.getCapability(CAPABILITY);
    }

    public static int getChunkChanges(LevelChunk chunk) {
        return chunk.getCapability(CAPABILITY).orElse(new ChunkDataCapability()).chunkChanges;
    }
}
