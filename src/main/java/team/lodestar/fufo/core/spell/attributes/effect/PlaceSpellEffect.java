package team.lodestar.fufo.core.spell.attributes.effect;

import team.lodestar.fufo.core.spell.SpellInstance;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class PlaceSpellEffect extends SpellEffect{
    BlockEntry<?> entry;
    public PlaceSpellEffect(BlockEntry<?> entry){
        super(CastLogicHandler.ONLY_BLOCK);
        this.entry = entry;
    }
    @Override
    public void effect(SpellInstance spell, ServerPlayer player) {

    }

    @Override
    public void effect(SpellInstance spell, ServerPlayer player, BlockHitResult result) {
        BlockState state = entry.getDefaultState();
        Level level = player.level;
        BlockPos pos = result.getBlockPos();
        pos = level.getBlockState(pos).getMaterial().isReplaceable() ? pos :pos.relative(result.getDirection());
        if(level.getBlockState(pos).getMaterial().isReplaceable()){
            level.setBlock(pos,state,3);
        }
    }
}
