package com.sammy.fufo.core.systems.magic.spell;

import com.sammy.fufo.core.systems.magic.spell.attributes.effect.SpellEffect;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class SpellHolder {

    public final ResourceLocation id;
    public Function<SpellHolder, SpellInstance> instanceFunction; //TODO probably shove these all into it's own record class?
    public Function<SpellInstance, SpellCooldown> cooldownFunction;
    public SpellEffect effect;


    //TODO: we wouldn't want to have to check != null everywhere for all these parameters, at least mainly SpellEffect
    public SpellHolder(ResourceLocation id, Function<SpellHolder, SpellInstance> instance, Function<SpellInstance, SpellCooldown> cooldownFunction, SpellEffect effect) {
        this.id = id;
        this.instanceFunction = instance;
        this.cooldownFunction = cooldownFunction;
        this.effect = effect;
    }
    public SpellHolder(ResourceLocation id, Function<SpellHolder, SpellInstance> instance, Function<SpellInstance, SpellCooldown> cooldownFunction) {
        this.id = id;
        this.instanceFunction = instance;
        this.cooldownFunction = cooldownFunction;
    }

    public SpellHolder(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getIconLocation() {
        return new ResourceLocation("fufo", "textures/spells/" + id + ".png");
    }

    public ResourceLocation getBackgroundLocation() {
        return new ResourceLocation("fufo", "textures/spells/" + id + "_background.png");
    }
}