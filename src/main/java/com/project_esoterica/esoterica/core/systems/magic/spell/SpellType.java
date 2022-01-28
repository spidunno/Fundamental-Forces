package com.project_esoterica.esoterica.core.systems.magic.spell;

import com.project_esoterica.esoterica.core.helper.DataHelper;
import com.project_esoterica.esoterica.core.setup.spell.SpellTypeRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class SpellType<T extends SpellInstance> {

    public final static SpellType<SpellInstance> EMPTY = new SpellType<SpellInstance>("empty", (tag)->new SpellInstance(SpellType.EMPTY)).disableRendering();

    public final String id;
    public final SpellInstanceSupplier supplier;
    public boolean shouldRender = true;
    public SpellType(String id, SpellInstanceSupplier supplier) {
        this.id = id;
        this.supplier = supplier;
    }

    public void cast(T instance) {

    }

    public CompoundTag serializeNBT(T instance, CompoundTag tag) {
        tag.putString("id", id);
        return tag;
    }

    public SpellInstance deserializeNBT(T instance, CompoundTag tag) {
        return new SpellInstance(SpellTypeRegistry.SPELL_TYPES.getOrDefault(tag.getString("type"), SpellType.EMPTY));
    }

    public ResourceLocation getIconLocation() {
        return DataHelper.prefix("spell/icon/" + id);
    }

    public ResourceLocation getBackgroundLocation() {
        return DataHelper.prefix("spell/background/" + id);
    }

    public SpellType<T> disableRendering() {
        this.shouldRender = false;
        return this;
    }

    public interface SpellInstanceSupplier {
        SpellInstance fromNbt(CompoundTag tag);
    }
}