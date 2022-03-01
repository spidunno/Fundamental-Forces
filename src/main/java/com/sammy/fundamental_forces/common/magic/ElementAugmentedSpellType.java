package com.sammy.fundamental_forces.common.magic;

import com.sammy.fundamental_forces.core.systems.magic.element.MagicElement;
import com.sammy.fundamental_forces.core.systems.magic.spell.SpellType;

public class ElementAugmentedSpellType extends SpellType {
    public final MagicElement element;
    public ElementAugmentedSpellType(String id, MagicElement element) {
        super(id);
        this.element = element;
    }
}
