package com.sammy.fufo.common.magic.spell.tier1;

import com.sammy.fufo.common.magic.ProjectileSpell;
import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import com.sammy.fufo.core.setup.content.magic.ElementTypeRegistry;

import java.awt.*;

public class FireBolt extends ProjectileSpell {
    public FireBolt() {
        super("fire_bolt", ElementTypeRegistry.FIRE, EntityRegistry.SPELL_MISSILE.get());
        this.element = ElementTypeRegistry.FIRE;
        this.duration = 200;
        this.firstColor = new Color(235,103,32);
        this.secondColor = new Color(237,170,55);
    }
}
