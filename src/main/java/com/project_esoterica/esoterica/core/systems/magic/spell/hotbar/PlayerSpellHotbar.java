package com.project_esoterica.esoterica.core.systems.magic.spell.hotbar;

import net.minecraft.nbt.CompoundTag;

public class PlayerSpellHotbar extends AbstractSpellHotbar{
    public PlayerSpellHotbar(int size) {
        super(size);
    }

    @Override
    public CompoundTag serializeNBT(CompoundTag tag) {
        return super.serializeNBT(tag);
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
    }
}
