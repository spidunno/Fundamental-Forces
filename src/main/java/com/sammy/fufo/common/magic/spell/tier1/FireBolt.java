package com.sammy.fufo.common.magic.spell.tier1;

import com.sammy.fufo.common.magic.ProjectileSpell;
import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import com.sammy.fufo.core.setup.content.magic.SpellRegistry;

import java.awt.*;

public class FireBolt extends ProjectileSpell {
    public FireBolt() {
        super("fire_bolt", SpellRegistry.FIRE, EntityRegistry.SPELL_BOLT.get());
        setFirstColor(new Color(15427360));
        setSecondColor(new Color(15575607));
        this.element = SpellRegistry.FIRE;
        this.duration = 200;
    }
}
