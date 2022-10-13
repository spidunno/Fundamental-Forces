package team.lodestar.fufo.core.spell;

import team.lodestar.fufo.FufoMod;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class SpellType {
    public final ResourceLocation id;
    public Function<SpellType, SpellInstance> defaultInstanceSupplier;
    public Function<SpellInstance, SpellCooldown> defaultCooldownSupplier;
    public SpellEffect effect;

    //TODO: we wouldn't want to have to check != null everywhere for all these parameters, at least mainly SpellEffect
    public SpellType(ResourceLocation id, Function<SpellType, SpellInstance> defaultInstanceSupplier, Function<SpellInstance, SpellCooldown> defaultCooldownSupplier, SpellEffect effect) {
        this.id = id;
        this.defaultInstanceSupplier = defaultInstanceSupplier;
        this.defaultCooldownSupplier = defaultCooldownSupplier;
        this.effect = effect;
    }

    public ResourceLocation getIconLocation() {
        return FufoMod.fufoPath("textures/spell/icon/" + id.getPath() + ".png");
    }

    public ResourceLocation getBackgroundLocation() {
        return FufoMod.fufoPath("textures/spell/background/" + id.getPath() + "_background.png");
    }
}