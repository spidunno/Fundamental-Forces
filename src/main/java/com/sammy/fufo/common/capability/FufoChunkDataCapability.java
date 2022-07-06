package com.sammy.fufo.common.capability;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.config.CommonConfig;
import com.sammy.ortus.systems.capability.OrtusCapability;
import com.sammy.ortus.systems.capability.OrtusCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class FufoChunkDataCapability implements OrtusCapability {

    //shove all chunk data here, use ChunkDataCapability.getCapability(chunk) to access data.

    public static Capability<FufoChunkDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public int chunkChanges;

    public FufoChunkDataCapability() {
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(FufoChunkDataCapability.class);
    }

    public static void attachChunkCapability(AttachCapabilitiesEvent<LevelChunk> event) {
        final FufoChunkDataCapability capability = new FufoChunkDataCapability();
        event.addCapability(FufoMod.fufoPath("chunk_data"), new OrtusCapabilityProvider<>(FufoChunkDataCapability.CAPABILITY, () -> capability));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (chunkChanges >= CommonConfig.MAXIMUM_CHUNK_CHANGES.getConfigValue()) {
            tag.putBoolean("invalid", true);
        } else {
            tag.putInt("chunkChanges", chunkChanges);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        chunkChanges = tag.getBoolean("invalid") ? CommonConfig.MAXIMUM_CHUNK_CHANGES.getConfigValue() : tag.getInt("chunkChanges");
    }

    public static LazyOptional<FufoChunkDataCapability> getCapabilityOptional(LevelChunk chunk) {
        return chunk.getCapability(CAPABILITY);
    }

    public static FufoChunkDataCapability getCapability(LevelChunk chunk) {
        return chunk.getCapability(CAPABILITY).orElse(new FufoChunkDataCapability());
    }
}
