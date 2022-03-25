package com.sammy.fundamental_forces.core.systems.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class WorldFiller
{
    public ArrayList<FillerEntry> entries = new ArrayList<>();
    public final boolean careful;
    public WorldFiller(boolean careful)
    {
        this.careful = careful;
    }
    public void fill(WorldGenLevel level)
    {
        for (FillerEntry entry : entries)
        {
            if (careful && !entry.canPlace(level))
            {
                continue;
            }
            entry.place(level);
        }
    }
    public void replaceAt(int index, FillerEntry entry)
    {
        entries.set(index, entry);
    }

    public record FillerEntry(BlockState state, BlockPos pos) {

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
}