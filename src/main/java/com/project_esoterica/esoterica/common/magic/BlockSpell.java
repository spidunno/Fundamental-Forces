package com.project_esoterica.esoterica.common.magic;

import com.project_esoterica.esoterica.core.systems.magic.spell.SpellInstance;
import com.project_esoterica.esoterica.core.systems.magic.spell.SpellType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class BlockSpell extends SpellType {
    public final Supplier<Block> blockSupplier;
    public BlockSpell(String id, Supplier<Block> blockSupplier) {
        super(id);
        this.blockSupplier = blockSupplier;
    }

    @Override
    public void castBlock(SpellInstance instance, Player player, BlockPos pos, BlockHitResult hitVec) {
        player.level.setBlockAndUpdate(pos.relative(hitVec.getDirection()), blockSupplier.get().defaultBlockState());
        player.swing(InteractionHand.MAIN_HAND,true);
    }
}
