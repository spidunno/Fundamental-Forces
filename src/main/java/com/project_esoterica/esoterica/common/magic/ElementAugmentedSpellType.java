package com.project_esoterica.esoterica.common.magic;

import com.project_esoterica.esoterica.core.systems.magic.element.MagicElement;
import com.project_esoterica.esoterica.core.systems.magic.spell.SpellType;

public class ElementAugmentedSpellType extends SpellType {
    public final MagicElement element;
    public ElementAugmentedSpellType(String id, MagicElement element) {
        super(id);
        this.element = element;
    }
}
