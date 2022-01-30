package com.project_esoterica.esoterica.common.capability;

import com.project_esoterica.esoterica.core.helper.DataHelper;
import com.project_esoterica.esoterica.core.systems.capability.SimpleCapability;
import com.project_esoterica.esoterica.core.systems.capability.SimpleCapabilityProvider;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventInstance;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import java.util.ArrayList;

public class WorldDataCapability implements SimpleCapability {

    //shove all level data here, use WorldDataCapability.getCapability(level) to access data.
    //level refers to dimension, not world. Each dimension will have it's own capability. (Need to confirm this.)
    public static Capability<WorldDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    public final ArrayList<WorldEventInstance> ACTIVE_WORLD_EVENTS = new ArrayList<>();
    public final ArrayList<WorldEventInstance> INBOUND_WORLD_EVENTS = new ArrayList<>();

    public WorldDataCapability() {
    }

    public static void attachWorldCapability(AttachCapabilitiesEvent<Level> event) {
        final WorldDataCapability capability = new WorldDataCapability();
        event.addCapability(DataHelper.prefix("world_data"), new SimpleCapabilityProvider<>(WorldDataCapability.CAPABILITY, () -> capability));
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

    public static LazyOptional<WorldDataCapability> getCapability(Level level) {
        return level.getCapability(CAPABILITY);
    }
}
