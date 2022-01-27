package com.project_esoterica.esoterica.core.systems.spell.hotbar;

import com.project_esoterica.esoterica.core.systems.spell.SpellInstance;

import java.util.ArrayList;

public abstract class AbstractSpellHotbar {
    public final int size;
    public int selectedSlot;
    public ArrayList<SpellInstance> spells;

    protected AbstractSpellHotbar(int size) {
        this.size = size;
    }
}
