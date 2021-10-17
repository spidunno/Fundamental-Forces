package com.project_esoterica.empirical_esoterica.common.capability;

import com.project_esoterica.empirical_esoterica.core.systems.capability.SimpleCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.world.ChunkDataEvent;

public class ChunkDataCapability implements SimpleCapability {

    //shove all player data here, use ChunkDataCapability.getCapability(chunk) to access data.

    public static Capability<ChunkDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public int heightmapChanges;

    public ChunkDataCapability() {
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (heightmapChanges > 50) {
            tag.putBoolean("invalid", true);
        }
        else{
            tag.putInt("heightmapChanges", heightmapChanges);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        heightmapChanges = tag.getBoolean("invalid") ? 50 : tag.getInt("heightmapChanges");
    }

    public static LazyOptional<ChunkDataCapability> getCapability(LevelChunk chunk) {
        return chunk.getCapability(CAPABILITY);
    }
    public static int getHeightmapChanges(LevelChunk chunk) {
        return chunk.getCapability(CAPABILITY).orElse(new ChunkDataCapability()).heightmapChanges;
    }
}
