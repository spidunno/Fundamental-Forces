package team.lodestar.fufo.core.spell;

import net.minecraft.resources.ResourceLocation;
import team.lodestar.fufo.FufoMod;

import java.util.function.Function;

public class EmptySpellType extends SpellType{
    public EmptySpellType() {
        super(FufoMod.fufoPath("empty"), null, null, null);
    }
}
