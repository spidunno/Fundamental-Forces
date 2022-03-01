package com.sammy.fundamental_forces.core.systems.worldevent;

import net.minecraft.nbt.CompoundTag;

public class WorldEventType {

    public final String id;
    public final EventTypeSupplier supplier;
    public WorldEventType(String id, EventTypeSupplier supplier) {
        this.id = id;
        this.supplier = supplier;
    }

    public WorldEventInstance createInstance(CompoundTag tag) {
        return supplier.fromNbt(tag);
    }
    public interface EventTypeSupplier {
        WorldEventInstance fromNbt(CompoundTag tag);
    }
}
