package com.space_mod_group.space_mod.common.starfall;

import com.space_mod_group.space_mod.common.starfall.results.AsteroidStarfallResult;
import com.space_mod_group.space_mod.common.starfall.results.DropPodStarfallResult;
import com.space_mod_group.space_mod.common.starfall.results.InitialDropPodStarfallResult;
import com.space_mod_group.space_mod.core.systems.starfall.StarfallInstance;
import com.space_mod_group.space_mod.core.systems.starfall.StarfallResult;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;

public class StarfallManager {
    public static ArrayList<StarfallResult> STARFALL_RESULTS = new ArrayList<>();

    public static final AsteroidStarfallResult ASTEROID = new AsteroidStarfallResult();
    public static final DropPodStarfallResult DROP_POD = new DropPodStarfallResult();
    public static final InitialDropPodStarfallResult FIRST_DROP_POD = new InitialDropPodStarfallResult();

    public static ArrayList<StarfallInstance> INBOUND_STARFALLS = new ArrayList<>();

    public static void worldTick(ServerLevel level) {
        for (StarfallInstance instance : INBOUND_STARFALLS)
        {
            instance.tick(level);
        }
    }

    public static void playerJoin(ServerLevel level, Player player) {

    }

    public static void serializeNBT(CompoundTag tag) {
        tag.putInt("starfallCount", INBOUND_STARFALLS.size());
        for (int i = 0; i < INBOUND_STARFALLS.size(); i++)
        {
            StarfallInstance instance = INBOUND_STARFALLS.get(i);
            instance.serializeNBT(tag, i);
        }
    }

    public static void deserializeNBT(CompoundTag tag) {
        int starfallCount = tag.getInt("starfallCount");
        for (int i = 0; i < starfallCount; i++)
        {
            StarfallInstance instance = StarfallInstance.deserializeNBT(tag, i);
            INBOUND_STARFALLS.add(instance);
        }
    }
}