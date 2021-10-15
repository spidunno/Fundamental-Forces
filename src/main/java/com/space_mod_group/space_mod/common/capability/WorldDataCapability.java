package com.space_mod_group.space_mod.common.capability;

import com.space_mod_group.space_mod.common.worldevent.WorldEventManager;
import com.space_mod_group.space_mod.core.systems.capability.SimpleCapability;
import com.space_mod_group.space_mod.core.systems.worldevent.WorldEventInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;

public class WorldDataCapability implements SimpleCapability {

    //shove all level data here, use WorldDataCapability.getCapability(level) to access data.
    //level refers to dimension, not world. Each dimension will have it's own capability. (Need to confirm this.)
    public static Capability<WorldDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public final ArrayList<WorldEventInstance> ACTIVE_WORLD_EVENTS = new ArrayList<>();
    public final ArrayList<WorldEventInstance> INBOUND_WORLD_EVENTS = new ArrayList<>();
    public WorldDataCapability() {
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        WorldEventManager.serializeNBT(this, tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        WorldEventManager.deserializeNBT(this, nbt);
    }
    public static LazyOptional<WorldDataCapability> getCapability(Level level)
    {
        return level.getCapability(CAPABILITY);
    }
}
