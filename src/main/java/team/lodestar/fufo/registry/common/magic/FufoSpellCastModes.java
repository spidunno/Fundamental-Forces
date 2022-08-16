package team.lodestar.fufo.registry.common.magic;

import net.minecraft.resources.ResourceLocation;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.core.spell.attributes.cast.InstantCastMode;
import team.lodestar.fufo.core.spell.attributes.cast.SpellCastMode;

import java.util.HashMap;
import java.util.function.Function;

public class FufoSpellCastModes {
    public static final HashMap<ResourceLocation, SpellCastMode> CAST_MODES = new HashMap<>();
    public static final SpellCastMode INSTANT = registerCastMode(FufoMod.fufoPath("instant"), InstantCastMode::new);

    public static SpellCastMode registerCastMode(ResourceLocation location, Function<ResourceLocation, SpellCastMode> function) {
        SpellCastMode castMode = function.apply(location);
        CAST_MODES.put(location, castMode);
        return castMode;
    }
}
