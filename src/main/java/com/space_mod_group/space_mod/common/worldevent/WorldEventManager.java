package com.space_mod_group.space_mod.common.worldevent;

import com.space_mod_group.space_mod.common.capability.WorldDataCapability;
import com.space_mod_group.space_mod.common.worldevent.starfall.StarfallInstance;
import com.space_mod_group.space_mod.core.systems.worldevent.WorldEventInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class WorldEventManager {

    public static <T extends WorldEventInstance> T addWorldEvent(ServerLevel level, T instance, boolean inbound) {
        return inbound ? addInboundWorldEvent(level, instance) : addWorldEvent(level, instance);
    }
    public static <T extends WorldEventInstance> T addInboundWorldEvent(ServerLevel level, T instance) {
        WorldDataCapability.getCapability(level).ifPresent(capability -> {
            capability.INBOUND_WORLD_EVENTS.add(instance);
            instance.start(level);
        });
        return instance;
    }

    public static <T extends WorldEventInstance> T addWorldEvent(ServerLevel level, T instance) {
        WorldDataCapability.getCapability(level).ifPresent(capability -> {
            capability.ACTIVE_WORLD_EVENTS.add(instance);
            instance.start(level);
        });
        return instance;
    }

    public static void worldTick(ServerLevel level) {
        WorldDataCapability.getCapability(level).ifPresent(capability -> {
            for (WorldEventInstance instance : capability.ACTIVE_WORLD_EVENTS) {
                instance.tick(level);
            }
            capability.ACTIVE_WORLD_EVENTS.removeIf(e -> e.invalidated);
            capability.ACTIVE_WORLD_EVENTS.addAll(capability.INBOUND_WORLD_EVENTS);
            capability.INBOUND_WORLD_EVENTS.clear();
        });
    }

    public static void serializeNBT(WorldDataCapability capability, CompoundTag tag) {
        tag.putInt("starfallCount", capability.ACTIVE_WORLD_EVENTS.size());
        for (int i = 0; i < capability.ACTIVE_WORLD_EVENTS.size(); i++) {
            WorldEventInstance instance = capability.ACTIVE_WORLD_EVENTS.get(i);
            CompoundTag instanceTag = new CompoundTag();
            instance.serializeNBT(instanceTag);
            tag.put("world_event_" + i, instanceTag);
        }
    }

    public static void deserializeNBT(WorldDataCapability capability, CompoundTag tag) {
        capability.ACTIVE_WORLD_EVENTS.clear();
        int starfallCount = tag.getInt("starfallCount");
        for (int i = 0; i < starfallCount; i++) {
            CompoundTag instanceTag = tag.getCompound("world_event_" + i);
            StarfallInstance instance = StarfallInstance.deserializeNBT(instanceTag);
            capability.ACTIVE_WORLD_EVENTS.add(instance);
        }
    }
}