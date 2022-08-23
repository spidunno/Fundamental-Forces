package team.lodestar.fufo.common.worldevents.starfall.actors;

import net.minecraft.util.RandomSource;
import team.lodestar.fufo.common.worldevents.starfall.StarfallActor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class BlankStarfallActor extends StarfallActor {
    public BlankStarfallActor() {
        super("blank", 0);
    }

    @Override
    public int randomizedCountdown(RandomSource random, int parentCountdown) {
        return 0;
    }

    @Override
    public void act(ServerLevel level, BlockPos targetBlockPos) {
    }
}
