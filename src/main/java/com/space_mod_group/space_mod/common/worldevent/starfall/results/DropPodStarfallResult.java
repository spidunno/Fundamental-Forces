package com.space_mod_group.space_mod.common.worldevent.starfall.results;

import com.space_mod_group.space_mod.common.worldevent.starfall.StarfallResult;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.block.Blocks;

public class DropPodStarfallResult extends StarfallResult {
    protected DropPodStarfallResult(int startingCountdown) {
        super(startingCountdown);
    }

    public DropPodStarfallResult() {
        super(StarfallResult.DEFAULT_STARTING_COUNTDOWN);
    }
    @Override
    public void fall(ServerLevel level, BlockPos pos) {
        Chicken chicken = EntityType.CHICKEN.create(level);
        chicken.setPos(pos.getX(),pos.getY(), pos.getZ());
        level.addFreshEntity(chicken);
        super.fall(level, pos);
    }
}
