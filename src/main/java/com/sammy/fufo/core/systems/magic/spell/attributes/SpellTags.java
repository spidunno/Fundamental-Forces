package com.sammy.fufo.core.systems.magic.spell.attributes;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.core.systems.magic.spell.attributes.cast.SpellCastMode;
import com.sammy.fufo.core.systems.magic.spell.attributes.cast.InstantCastMode;
import com.sammy.fufo.core.systems.magic.spell.attributes.effect.ProjectileEffect;
import com.sammy.fufo.core.systems.magic.spell.attributes.effect.SpellEffect;

public class SpellTags {
    public static class CastModes {
        public static final InstantCastMode INSTANT = new InstantCastMode(FufoMod.fufoPath("instant"));
//        public static final SpellCastMode CHANNEL = new SpellCastMode("channel");
//        public static final SpellCastMode CHARGE = new SpellCastMode("charge");
//        public static final SpellCastMode TOGGLE = new SpellCastMode("toggle");
    }
}
