package team.lodestar.fufo.core.element;

import team.lodestar.fufo.core.spell.SpellInstance;
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
