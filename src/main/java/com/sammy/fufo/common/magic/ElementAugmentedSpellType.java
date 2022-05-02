package com.sammy.fufo.common.magic;

import com.sammy.fufo.core.systems.magic.element.MagicElement;
import com.sammy.fufo.core.systems.magic.spell.SpellType;

public class ElementAugmentedSpellType extends SpellType {
    public final MagicElement element;
    public ElementAugmentedSpellType(String id, MagicElement element) {
        super(id);
        this.element = element;
    }
}
