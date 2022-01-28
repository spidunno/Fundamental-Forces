package com.project_esoterica.esoterica.core.systems.spell;

import com.project_esoterica.esoterica.core.helper.DataHelper;
import net.minecraft.resources.ResourceLocation;

public class SpellType {

    public final static SpellType EMPTY = new SpellType("empty").disableRendering();

    public final String id;
    public boolean shouldRender = true;

    public SpellType(String id) {
        this.id = id;
    }

    public ResourceLocation getIconLocation() {
        return DataHelper.prefix("spell/icon/" + id);
    }

    public ResourceLocation getBackgroundLocation() {
        return DataHelper.prefix("spell/background/" + id);
    }

    public SpellType disableRendering() {
        this.shouldRender = false;
        return this;
    }
}