package com.project_esoterica.esoterica.core.systems.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FillerEntry {
    public final BlockState state;
    public final BlockPos pos;

    public FillerEntry(BlockState state, BlockPos pos) {
        this.state = state;
        this.pos = pos;
    }

    public boolean canPlace(WorldGenLevel level) {
        return canPlace(level, pos);
    }

    public boolean canPlace(WorldGenLevel level, BlockPos pos) {
        if (level.isOutsideBuildHeight(pos)) {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        return level.isEmptyBlock(pos) || state.getMaterial().isReplaceable();
    }

    public void place(WorldGenLevel level) {
        level.setBlock(pos, state, 3);
    }
}
