package com.sammy.fufo.core.systems.magic.spell.attributes;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.core.systems.magic.spell.attributes.cast.InstantCastMode;
import com.sammy.fufo.core.systems.magic.spell.attributes.cast.SpellCastMode;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.function.Function;

public class SpellTags {
    public static class CastModes {
        public static final HashMap<ResourceLocation, SpellCastMode> CAST_MODES = new HashMap<>();
        public static final InstantCastMode INSTANT = registerMode(InstantCastMode.class, FufoMod.fufoPath("instant"), InstantCastMode::new);
//        public static final SpellCastMode CHANNEL = new SpellCastMode("channel");
//        public static final SpellCastMode CHARGE = new SpellCastMode("charge");
//        public static final SpellCastMode TOGGLE = new SpellCastMode("toggle");

        public static <T extends SpellCastMode> T registerMode(Class<T> clazz, ResourceLocation location, Function<ResourceLocation, T> function) {
            return clazz.cast(CAST_MODES.put(location, function.apply(location)));
        }
    }
}