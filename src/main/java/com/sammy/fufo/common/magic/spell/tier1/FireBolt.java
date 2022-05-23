package com.sammy.fufo.common.magic.spell.tier1;

import com.sammy.fufo.common.magic.ProjectileSpell;
import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import com.sammy.fufo.core.setup.content.magic.ElementTypeRegistry;

import java.awt.*;

public class FireBolt extends ProjectileSpell {
    public FireBolt() {
        super("fire_bolt", ElementTypeRegistry.FIRE, EntityRegistry.SPELL_MISSILE.get());
        setFirstColor(new Color(15427360));
        setSecondColor(new Color(15575607));
        this.element = ElementTypeRegistry.FIRE;
        this.duration = 200;
    }
}
