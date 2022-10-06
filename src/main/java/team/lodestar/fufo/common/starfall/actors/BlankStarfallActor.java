package team.lodestar.fufo.common.starfall.actors;

import net.minecraft.util.RandomSource;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class BlankStarfallActor extends AbstractStarfallActor {
    public BlankStarfallActor() {
        super("blank", 0);
    }

    @Override
    public int randomizeStartingCountdown(RandomSource random, int parentCountdown) {
        return 0;
    }

    @Override
    public void act(ServerLevel level, BlockPos targetBlockPos) {
    }
}
