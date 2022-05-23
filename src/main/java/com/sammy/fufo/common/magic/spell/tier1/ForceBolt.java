package com.sammy.fufo.common.magic.spell.tier1;

import com.sammy.fufo.common.magic.ProjectileSpell;
import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import com.sammy.fufo.core.setup.content.magic.ElementTypeRegistry;

import java.awt.*;

public class ForceBolt extends ProjectileSpell {
    public ForceBolt() {
        super("force_missile", ElementTypeRegistry.FORCE, EntityRegistry.SPELL_BOLT.get());
        this.firstColor = new Color(6220753);
        this.secondColor = new Color(11270133);
        this.duration = 100;
        this.element = ElementTypeRegistry.FORCE;
    }
}
