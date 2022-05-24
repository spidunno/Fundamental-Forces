package com.sammy.fufo.core.systems.magic.spell.attributes.cast;

import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;

public class SpellCastMode{
    public SpellCastMode(String channel) {
    }
    public void cast(SpellInstance spell, ServerPlayer player, BlockPos pos, BlockHitResult hitVec){
        spell.effect.cast(spell, player, pos, hitVec);
    }
    public void cast(SpellInstance spell, ServerPlayer player){
        spell.effect.cast(spell, player);
    }
}
