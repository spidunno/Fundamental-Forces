package com.sammy.fufo.core.systems.magic.element;

import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class MagicElement {
    public final ResourceLocation id;

    public void castCommonEffect(SpellInstance instance, ServerPlayer player){

    }

    public MagicElement(ResourceLocation id) {
        this.id = id;
    }
}
