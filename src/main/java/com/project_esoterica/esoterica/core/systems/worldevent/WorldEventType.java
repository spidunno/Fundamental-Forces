package com.project_esoterica.esoterica.core.systems.worldevent;

import net.minecraft.nbt.CompoundTag;

public class WorldEventType {

    public final String id;
    public final EventTypeSupplier supplier;
    public WorldEventType(String id, EventTypeSupplier supplier) {
        this.id = id;
        this.supplier = supplier;
    }

    public WorldEventInstance createInstance(CompoundTag tag) {
        return supplier.getBookObject(tag);
    }
    public interface EventTypeSupplier {
        WorldEventInstance getBookObject(CompoundTag tag);
    }
}
