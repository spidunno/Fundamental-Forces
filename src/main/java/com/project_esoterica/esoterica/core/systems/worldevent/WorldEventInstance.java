package com.project_esoterica.esoterica.core.systems.worldevent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

public abstract class WorldEventInstance {
    public String id;
    public boolean invalidated;

    public WorldEventInstance(String id) {
        this.id = id;
    }

    public void start(ServerLevel level) {

    }

    public void tick(ServerLevel level) {

    }

    public void end(ServerLevel level) {
        invalidated = true;
    }

    public void serializeNBT(CompoundTag tag) {
        tag.putString("id", id);
        tag.putBoolean("invalidated", invalidated);
    }

    public void deserializeNBT(CompoundTag tag) {
        id = tag.getString("id");
        invalidated = tag.getBoolean("invalidated");

    }
}