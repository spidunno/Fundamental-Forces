package com.project_esoterica.esoterica.core.setup.spell;

import com.project_esoterica.esoterica.core.systems.spell.SpellType;

import java.util.HashMap;

public class SpellTypeRegistry {

    public static final HashMap<String, SpellType> SPELL_TYPES = new HashMap<>();

    private static SpellType registerSpellType(SpellType spellType) {
        SPELL_TYPES.put(spellType.id, spellType);
        return spellType;
    }
}
