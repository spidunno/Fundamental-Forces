package com.sammy.fufo.core.systems.magic.spell.attributes;

import com.sammy.fufo.core.systems.magic.spell.attributes.cast.SpellCastMode;
import com.sammy.fufo.core.systems.magic.spell.attributes.cast.InstantCastMode;
import com.sammy.fufo.core.systems.magic.spell.attributes.effect.SpellEffect;

public class SpellTags {
    public static class EffectModes {
        public static final SpellEffect BOLT = new SpellEffect("bolt");
        public static final SpellEffect WARD = new SpellEffect("ward");
        public static final SpellEffect AURA = new SpellEffect("aura");
        public static final SpellEffect WALL = new SpellEffect("wall");
    }
    public static class CastModes {
        public static final InstantCastMode INSTANT = new InstantCastMode("instant");
        public static final SpellCastMode CHANNEL = new SpellCastMode("channel");
        public static final SpellCastMode CHARGE = new SpellCastMode("charge");
        public static final SpellCastMode TOGGLE = new SpellCastMode("toggle");
    }
}
