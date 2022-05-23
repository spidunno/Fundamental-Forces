package com.sammy.fufo.core.systems.magic.spell.attributes;

import com.sammy.fufo.core.systems.magic.spell.attributes.cast.SpellCastMode;
import com.sammy.fufo.core.systems.magic.spell.attributes.cast.InstantCastMode;
import com.sammy.fufo.core.systems.magic.spell.attributes.effect.SpellEffectMode;

public class SpellTags {
    public class EffectModes {
        public static final SpellEffectMode BOLT = new SpellEffectMode("bolt");
        public static final SpellEffectMode WARD = new SpellEffectMode("ward");
        public static final SpellEffectMode AURA = new SpellEffectMode("aura");
    }
    public class CastModes {
        public static final InstantCastMode INSTANT = new InstantCastMode("instant");
        public static final SpellCastMode CHANNEL = new SpellCastMode("channel");
        public static final SpellCastMode CHARGE = new SpellCastMode("charge");
        public static final SpellCastMode TOGGLE = new SpellCastMode("toggle");
    }
}
