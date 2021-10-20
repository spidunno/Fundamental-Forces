package com.project_esoterica.esoterica.core.systems.worldevent;

import net.minecraft.nbt.CompoundTag;

public abstract class WorldEventReader {

    public WorldEventReader() {
    }

    public WorldEventInstance createInstance(CompoundTag tag) {
        return null;
    }
}
