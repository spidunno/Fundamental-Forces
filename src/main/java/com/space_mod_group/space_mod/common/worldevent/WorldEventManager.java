package com.space_mod_group.space_mod.common.worldevent;

import com.space_mod_group.space_mod.common.worldevent.starfall.StarfallInstance;
import com.space_mod_group.space_mod.core.systems.worldevent.WorldEventInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorldEventManager {

    private static ArrayList<WorldEventInstance> INBOUND_WORLD_EVENTS = new ArrayList<>();

    public static <T extends WorldEventInstance> ArrayList<T> getEvents(Class<T> T) {
        return WorldEventManager.INBOUND_WORLD_EVENTS.stream().filter(e -> T.isInstance(e.getClass())).map(e -> (T) e).collect(Collectors.toCollection(ArrayList::new));
    }
    public static void addWorldEvent(ServerLevel level, WorldEventInstance instance)
    {
        INBOUND_WORLD_EVENTS.add(instance);
        instance.start(level);
    }

    public static void worldTick(ServerLevel level) {
        for (WorldEventInstance instance : INBOUND_WORLD_EVENTS)
        {
            instance.tick(level);
        }
        INBOUND_WORLD_EVENTS.removeIf(e -> e.invalidated);
    }

    public static void serializeNBT(CompoundTag tag) {
        tag.putInt("starfallCount", INBOUND_WORLD_EVENTS.size());
        for (int i = 0; i < INBOUND_WORLD_EVENTS.size(); i++)
        {
            WorldEventInstance instance = INBOUND_WORLD_EVENTS.get(i);
            CompoundTag instanceTag = new CompoundTag();
            instance.serializeNBT(instanceTag);
            tag.put("world_event_" + i, instanceTag);
        }
    }

    public static void deserializeNBT(CompoundTag tag) {
        INBOUND_WORLD_EVENTS = new ArrayList<>();
        int starfallCount = tag.getInt("starfallCount");
        for (int i = 0; i < starfallCount; i++)
        {
            CompoundTag instanceTag = tag.getCompound("world_event_" + i);
            StarfallInstance instance = StarfallInstance.deserializeNBT(instanceTag);
            INBOUND_WORLD_EVENTS.add(instance);
        }
    }
}