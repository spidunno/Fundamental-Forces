package com.project_esoterica.esoterica.core.systems.spell.hotbar;

import net.minecraft.nbt.CompoundTag;

public class PlayerSpellHotbar extends AbstractSpellHotbar{
    public boolean open;
    public PlayerSpellHotbar(int size) {
        super(size);
    }

    @Override
    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putBoolean("open", open);
        return super.serializeNBT(tag);
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        open = tag.getBoolean("open");
        super.deserializeNBT(tag);
    }
}
