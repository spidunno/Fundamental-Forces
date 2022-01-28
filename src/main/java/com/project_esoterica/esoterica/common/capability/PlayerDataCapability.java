package com.project_esoterica.esoterica.common.capability;

import com.project_esoterica.esoterica.core.systems.capability.SimpleCapability;
import com.project_esoterica.esoterica.core.systems.spell.hotbar.PlayerSpellHotbar;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerDataCapability implements SimpleCapability {

    //shove all player data here, use PlayerDataCapability.getCapability(player) to access data.

    public static Capability<PlayerDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public boolean firstTimeJoin;
    public boolean unlockedSpellHotbar;
    public PlayerSpellHotbar spellHotbar = new PlayerSpellHotbar(9);

    public PlayerDataCapability() {
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("firstTimeJoin", firstTimeJoin);
        if (unlockedSpellHotbar) {
            tag.putBoolean("unlockedSpellHotbar", true);
            spellHotbar.serializeNBT(tag);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        firstTimeJoin = tag.getBoolean("firstTimeJoin");
        if (tag.contains("unlockedSpellHotbar")) {
            unlockedSpellHotbar = true;
            spellHotbar.deserializeNBT(tag);
        }
    }

    public static LazyOptional<PlayerDataCapability> getCapability(Player player) {
        return player.getCapability(CAPABILITY);
    }
}
