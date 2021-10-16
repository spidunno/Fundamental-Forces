package com.project_esoterica.empirical_esoterica.common.capability;

import com.project_esoterica.empirical_esoterica.core.systems.capability.SimpleCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerDataCapability implements SimpleCapability {

    //shove all player data here, use PlayerDataCapability.getCapability(player) to access data.

    public static Capability<PlayerDataCapability> CAPABILITY =  CapabilityManager.get(new CapabilityToken<>(){});

    public boolean firstTimeJoin = false;
    public PlayerDataCapability() {
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("firstTimeJoin", firstTimeJoin);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        firstTimeJoin = nbt.getBoolean("firstTimeJoin");
    }
    public static LazyOptional<PlayerDataCapability> getCapability(Player player)
    {
        return player.getCapability(CAPABILITY);
    }
}
