package com.project_esoterica.esoterica.common.magic;

import com.project_esoterica.esoterica.core.systems.magic.spell.SpellCooldownData;
import com.project_esoterica.esoterica.core.systems.magic.spell.SpellInstance;
import com.project_esoterica.esoterica.core.systems.magic.spell.SpellType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
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
    public void castBlock(SpellInstance instance, ServerPlayer player, BlockPos pos, BlockHitResult hitVec) {
        instance.cooldown = new SpellCooldownData(100);
//        PlayerDataCapability.getCapability(player).ifPresent(c -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new ResetRightClickDelayPacket()));
        player.level.setBlockAndUpdate(pos.relative(hitVec.getDirection()), blockSupplier.get().defaultBlockState());
        player.swing(InteractionHand.MAIN_HAND, true);
    }
}