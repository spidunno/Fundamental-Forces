package com.project_esoterica.esoterica.core.systems.magic.spell;

import com.project_esoterica.esoterica.core.setup.spell.SpellTypeRegistry;
import net.minecraft.nbt.CompoundTag;

public class SpellInstance {

    public static final SpellInstance EMPTY = new SpellInstance(SpellType.EMPTY);
    public final SpellType<SpellInstance> type;

    public SpellInstance(SpellType<SpellInstance> type) {
        this.type = type;
    }

    public void cast() {

    }


    public CompoundTag serializeNBT(CompoundTag tag) {
        return type.serializeNBT(this, tag);
    }

    public static SpellInstance deserializeNBT(CompoundTag tag) {
        return SpellTypeRegistry.SPELL_TYPES.getOrDefault(tag.getString("type"), SpellType.EMPTY).supplier.fromNbt(tag);
    }
}