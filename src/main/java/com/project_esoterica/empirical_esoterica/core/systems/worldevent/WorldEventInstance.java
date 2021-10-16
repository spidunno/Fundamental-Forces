package com.project_esoterica.empirical_esoterica.core.systems.worldevent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

public abstract class WorldEventInstance {
    public boolean invalidated;
    public void start(ServerLevel level){

    }
    public void tick(ServerLevel level) {

    }
    public void end(ServerLevel level){
        invalidate(level);
    }
    public void invalidate(ServerLevel level){
        invalidated = true;
    }
    public void serializeNBT(CompoundTag tag) {

    }
}