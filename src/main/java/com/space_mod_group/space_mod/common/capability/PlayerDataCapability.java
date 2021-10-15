package com.space_mod_group.space_mod.common.capability;

import com.space_mod_group.space_mod.core.systems.capability.SimpleCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class PlayerDataCapability implements SimpleCapability {

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
}
