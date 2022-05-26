package com.sammy.fufo.core.systems.magic.spell.attributes.effect;

import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;

import java.util.function.Supplier;

public class PlaceSpellEffect extends SpellEffect{
    Supplier<BlockState> stateSupplier;
    public PlaceSpellEffect(Supplier<BlockState> stateSupplier){
        this.stateSupplier = stateSupplier;
    }
    @Override
    public void effect(SpellInstance spell, ServerPlayer player) {

    }

    @Override
    public void effect(SpellInstance spell, ServerPlayer player, BlockHitResult result) {
        BlockState state = stateSupplier.get();
        Level level = player.level;
        BlockPos pos = result.getBlockPos();
        pos = level.getBlockState(pos).getMaterial().isReplaceable() ? pos :pos.relative(result.getDirection());
        if(level.getBlockState(pos).getMaterial().isReplaceable()){
            level.setBlock(pos,state,3);
        }
    }
}
