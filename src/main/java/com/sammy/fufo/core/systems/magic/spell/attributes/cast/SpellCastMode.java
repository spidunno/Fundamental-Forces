package com.sammy.fufo.core.systems.magic.spell.attributes.cast;

import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;

public abstract class SpellCastMode{
    public final ResourceLocation id;
    public SpellCastMode(ResourceLocation id){
        this.id = id;
    }
    public abstract boolean canCast(SpellInstance spell, ServerPlayer player, BlockPos pos, BlockHitResult hitVec);
    public abstract boolean canCast(SpellInstance spell, ServerPlayer player);
}
