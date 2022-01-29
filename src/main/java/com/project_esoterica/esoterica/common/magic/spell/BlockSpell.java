package com.project_esoterica.esoterica.common.magic.spell;

import com.project_esoterica.esoterica.core.systems.magic.spell.SpellType;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class BlockSpell extends SpellType {
    public final Supplier<Block> blockSupplier;
    public BlockSpell(String id, Supplier<Block> blockSupplier) {
        super(id);
        this.blockSupplier = blockSupplier;
    }

}
