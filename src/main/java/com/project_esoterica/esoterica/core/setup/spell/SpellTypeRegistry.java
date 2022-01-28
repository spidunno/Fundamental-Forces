package com.project_esoterica.esoterica.core.setup.spell;

import com.project_esoterica.esoterica.core.systems.magic.spell.SpellInstance;
import com.project_esoterica.esoterica.core.systems.magic.spell.SpellType;

import java.util.HashMap;

public class SpellTypeRegistry {

    public static final HashMap<String, SpellType<SpellInstance>> SPELL_TYPES = new HashMap<>();

    private static SpellType<SpellInstance> registerSpellType(SpellType<SpellInstance> spellType) {
        SPELL_TYPES.put(spellType.id, spellType);
        return spellType;
    }
}
