package com.project_esoterica.esoterica.common.capability;

import com.project_esoterica.esoterica.core.config.CommonConfig;
import com.project_esoterica.esoterica.core.systems.capability.SimpleCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class ChunkDataCapability implements SimpleCapability {

    //shove all chunk data here, use ChunkDataCapability.getCapability(chunk) to access data.

    public static Capability<ChunkDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public int chunkChanges;

    public ChunkDataCapability() {
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
