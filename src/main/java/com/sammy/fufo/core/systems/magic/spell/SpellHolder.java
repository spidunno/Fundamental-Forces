package com.sammy.fufo.core.systems.magic.spell;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;
import java.util.function.Supplier;

public class SpellHolder {

    public final ResourceLocation id;
    public Function<SpellHolder, SpellInstance> instance;

    public SpellHolder(ResourceLocation id) {
        this.id = id;
    }

    public SpellHolder(ResourceLocation id, Function<SpellHolder, SpellInstance> instance) {
        this.id = id;
        this.instance = instance;
    }

    public ResourceLocation getIconLocation() {
        return new ResourceLocation("fufo", "textures/spells/" + id + ".png");
    }

    public ResourceLocation getBackgroundLocation() {
        return new ResourceLocation("fufo", "textures/spells/" + id + "_background.png");
    }
}
