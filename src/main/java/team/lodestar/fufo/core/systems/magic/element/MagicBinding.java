package team.lodestar.fufo.core.systems.magic.element;

import net.minecraft.resources.ResourceLocation;

public class MagicBinding {
    public final ResourceLocation id;

    public MagicBinding(ResourceLocation id) {
        this.id = id;
    }

    public MagicBinding(MagicElement element) {
        this.id = element.id;
    }
}