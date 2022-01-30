package com.project_esoterica.esoterica.core.systems.magic.spell.hotbar;

import com.project_esoterica.esoterica.core.systems.magic.spell.SpellInstance;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;

public abstract class AbstractSpellHotbar {
    public int selectedSlot;
    public final int size;
    public NonNullList<SpellInstance> spells;

    protected AbstractSpellHotbar(int size) {
        this.size = size;
        this.spells = NonNullList.withSize(size, SpellInstance.EMPTY);
    }

    public SpellInstance getSelectedSpell() {
        return spells.get(selectedSlot);
    }

    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putInt("spellAmount", spells.size());
        for (int i = 0; i < spells.size(); i++) {
            CompoundTag spellTag = new CompoundTag();
            tag.put("spell_" + i, spells.get(i).serializeNBT(spellTag));
        }
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        int amount = tag.getInt("spellAmount");
        for (int i = 0; i < amount; i++) {
            CompoundTag spellTag = tag.getCompound("spell_" + i);
            spells.set(i,SpellInstance.deserializeNBT(spellTag));
        }
    }
}