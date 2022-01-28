package com.project_esoterica.esoterica.core.systems.spell;

import com.project_esoterica.esoterica.core.setup.spell.SpellTypeRegistry;
import net.minecraft.nbt.CompoundTag;

public class SpellInstance {

    public static final SpellInstance EMPTY = new SpellInstance(SpellType.EMPTY);
    public final SpellType type;

    public SpellInstance(SpellType type) {
        this.type = type;
    }

    public void cast()
    {

    }


    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putString("id", type.id);
        return tag;
    }

    public static SpellInstance deserializeNBT(CompoundTag tag) {
        return new SpellInstance(SpellTypeRegistry.SPELL_TYPES.getOrDefault(tag.getString("type"), SpellType.EMPTY));
    }
}
