package com.space_mod_group.space_mod.common.capability;

import com.space_mod_group.space_mod.SpaceMod;
import com.space_mod_group.space_mod.common.starfall.StarfallManager;
import com.space_mod_group.space_mod.core.systems.capability.SimpleCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class WorldDataCapability implements SimpleCapability {

    public static Capability<WorldDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    public WorldDataCapability() {
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        StarfallManager.serializeNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        StarfallManager.deserializeNBT(nbt);
    }
}
