package com.project_esoterica.esoterica.common.worldevents.starfall.actors;

import com.project_esoterica.esoterica.common.entity.falling.FallingCrashpodEntity;
import com.project_esoterica.esoterica.common.worldevents.starfall.StarfallActor;
import com.project_esoterica.esoterica.core.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class AsteroidStarfallActor extends StarfallActor {
    public AsteroidStarfallActor() {
        super("asteroid", CommonConfig.NATURAL_SPACE_DEBRIS_COUNTDOWN.get());
    }

    @Override
    public int randomizedCountdown(Random random, int parentCountdown) {
        double min = CommonConfig.MINIMUM_ASTEROID_COUNTDOWN_MULTIPLIER.get();
        double max = CommonConfig.MAXIMUM_ASTEROID_COUNTDOWN_MULTIPLIER.get();
        return (int) (Mth.nextDouble(random, min, max) * parentCountdown);
    }

    @Override
    public void fall(ServerLevel level, BlockPos targetBlockPos) {
        Vec3 target = new Vec3(targetBlockPos.getX(), targetBlockPos.getY(), targetBlockPos.getZ());
        Vec3 startPos = target.add(-100 + level.random.nextInt(200), 100, -100 + level.random.nextInt(200));
        Vec3 velocity = startPos.vectorTo(target).normalize().multiply(2.0, 2.0, 2.0);

        FallingCrashpodEntity crashpod = new FallingCrashpodEntity(level, targetBlockPos);
        crashpod.setPos(startPos);
        crashpod.setDeltaMovement(velocity);
        level.addFreshEntity(crashpod);
        super.fall(level, targetBlockPos);
    }
}
