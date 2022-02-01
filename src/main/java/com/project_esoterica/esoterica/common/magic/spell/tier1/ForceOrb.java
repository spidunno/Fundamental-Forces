package com.project_esoterica.esoterica.common.magic.spell.tier1;

import com.project_esoterica.esoterica.common.magic.BlockSpell;
import net.minecraft.world.level.block.Blocks;

public class ForceOrb extends BlockSpell {
    public ForceOrb() {
        super("force_orb", ()-> Blocks.DIRT);
    }
}
