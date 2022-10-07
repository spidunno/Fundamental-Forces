package team.lodestar.fufo.common.magic.spell.castmodes;


import team.lodestar.fufo.core.spell.SpellCastMode;
import team.lodestar.fufo.core.spell.SpellInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;

public class InstantCastMode extends SpellCastMode {
    public InstantCastMode(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean canCast(SpellInstance spell, ServerPlayer player, BlockPos pos, BlockHitResult hitVec) {
        return true;
    }

    @Override
    public boolean canCast(SpellInstance spell, ServerPlayer player) {
        return true;
    }
}
