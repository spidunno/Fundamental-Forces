package team.lodestar.fufo.core.spell;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.core.spell.attributes.effect.SpellEffect;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class SpellType {

    public final ResourceLocation id;
    public Function<SpellType, SpellInstance> instanceFunction; //TODO probably shove these all into it's own record class?
    public Function<SpellInstance, SpellCooldown> cooldownFunction;
    public SpellEffect effect;


    //TODO: we wouldn't want to have to check != null everywhere for all these parameters, at least mainly SpellEffect
    public SpellType(ResourceLocation id, Function<SpellType, SpellInstance> instance, Function<SpellInstance, SpellCooldown> cooldownFunction, SpellEffect effect) {
        this.id = id;
        this.instanceFunction = instance;
        this.cooldownFunction = cooldownFunction;
        this.effect = effect;
    }
    public SpellType(ResourceLocation id, Function<SpellType, SpellInstance> instance, Function<SpellInstance, SpellCooldown> cooldownFunction) {
        this.id = id;
        this.instanceFunction = instance;
        this.cooldownFunction = cooldownFunction;
    }

    public SpellType(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getIconLocation() {
        return FufoMod.fufoPath("textures/spell/icon/" + id.getPath() + ".png");
    }

    public ResourceLocation getBackgroundLocation() {
        return FufoMod.fufoPath("textures/spell/background/" + id.getPath() + "_background.png");
    }
}