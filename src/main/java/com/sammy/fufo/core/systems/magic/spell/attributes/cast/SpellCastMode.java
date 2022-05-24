package com.sammy.fufo.core.systems.magic.spell.attributes.cast;

import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import com.sammy.fufo.core.systems.magic.spell.attributes.SpellTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;

public abstract class SpellCastMode {
    public final ResourceLocation id;

    public SpellCastMode(ResourceLocation id) {
        this.id = id;
    }

    public abstract boolean canCast(SpellInstance spell, ServerPlayer player, BlockPos pos, BlockHitResult hitVec);

    public abstract boolean canCast(SpellInstance spell, ServerPlayer player);

    public CompoundTag serializeNBT() {
        CompoundTag castTag = new CompoundTag();
        castTag.putString("id", id.toString());
        return castTag;
    }

    public static SpellCastMode deserializeNBT(CompoundTag tag) {
        return SpellTags.CastModes.CAST_MODES.get(new ResourceLocation(tag.getString("id")));
    }
}