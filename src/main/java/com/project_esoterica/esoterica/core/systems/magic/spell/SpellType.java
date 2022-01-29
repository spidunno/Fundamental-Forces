package com.project_esoterica.esoterica.core.systems.magic.spell;

import com.project_esoterica.esoterica.core.helper.DataHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class SpellType {

    public final String id;
    public boolean shouldRender = true;
    public SpellType(String id) {
        this.id = id;
    }

    public void cast(SpellInstance instance) {

    }

    public CompoundTag serializeNBT(SpellInstance instance, CompoundTag tag) {
        tag.putString("id", id);
        return tag;
    }

    public SpellInstance deserializeNBT(CompoundTag tag) {
        return new SpellInstance(this);
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