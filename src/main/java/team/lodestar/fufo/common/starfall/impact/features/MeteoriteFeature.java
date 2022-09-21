package team.lodestar.fufo.common.starfall.impact.features;

import com.google.common.collect.ImmutableList;
import com.google.common.math.StatsAccumulator;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.phys.Vec3;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.registry.common.FufoBlocks;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.helpers.VecHelper;

import java.util.*;

import static net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;

public class MeteoriteFeature extends Feature<NoneFeatureConfiguration> {
    public MeteoriteFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        return perlinNoiseTest(context.level(), context.chunkGenerator(), context.origin(), context.random());
    }


    private static final PerlinSimplexNoise NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(1234L)), ImmutableList.of(0));

    public static boolean perlinNoiseTest(WorldGenLevel level, ChunkGenerator generator, BlockPos pos, RandomSource random) {
        //TODO: this is somewhat expensive, and also just a test. Here's what we need to work on:
        // Noise is expensive! At least when used so much. We should precompute some noise values for various angles our theta might be, and then just use the closest one depending on what theta we are at.
        // Y levels, our craters are 3d not 2d. If we precompute our noise then we can simply reduce it's strength as we delve into the lower levels of the crater.


        int x = pos.getX();
        int z = pos.getZ();
        int radius = 32;
        int effectiveRadius = 16;
        float noiseExponent = 5f;
        Set<BlockPos> set = new HashSet<>();
        Map<Integer, Double> noiseValues = new HashMap<>();
        for (int i = 0; i <= 360; i++) {
            noiseValues.put(i, NOISE.getValue(pos.getX() + pos.getZ() + i * 0.025f, pos.getY() / 0.05f, true) * noiseExponent);
        }
        double highest = 0;
        for (int i = 0; i < radius * 2 + 1; i++) {
            for (int j = 0; j < radius * 2 + 1; j++) {
                level.setBlock(pos.offset(i-radius, 10, j-radius), Blocks.GREEN_WOOL.defaultBlockState(), 3);
                int xp = x + i - radius;
                int zp = z + j - radius;
                double theta = 180 + 180 / Math.PI * Math.atan2(x - xp, z - zp);
                double naturalNoiseValue = noiseValues.get(Mth.floor(theta));
                if (naturalNoiseValue > 1f) {
                    naturalNoiseValue *= naturalNoiseValue;
                }
                if (highest < naturalNoiseValue) {
                    highest = naturalNoiseValue;
                }
                int floor = (int) Math.floor(pointDistancePlane(xp, zp, x, z));
                if (floor <= (effectiveRadius + Math.floor(naturalNoiseValue) - 1) && floor >= (effectiveRadius + Math.floor(naturalNoiseValue) - 3)) {
                    set.add(new BlockPos(xp, pos.getY() + 10, zp));
                }
            }
        }
        set.forEach(p -> {
            level.setBlock(p, Blocks.RED_WOOL.defaultBlockState(), 3);
        });
        return true;
    }

    public static float pointDistancePlane(double x1, double z1, double x2, double z2) {
        return (float) Math.hypot(x1 - x2, z1 - z2);
    }

    public static boolean generateMeteorite(WorldGenLevel level, ChunkGenerator generator, BlockPos pos, RandomSource random) {
        int meteorSize = 6;
        int craterSize = 8;
        StatsAccumulator stats = new StatsAccumulator();
        for (int x = -meteorSize * 2; x <= meteorSize * 2; x++) {
            for (int z = -meteorSize * 2; z <= meteorSize * 2; z++) {
                int h = level.getHeightmapPos(MOTION_BLOCKING_NO_LEAVES, pos.offset(x, 0, z)).getY();
                stats.add(h);
            }
        }
        int yLevel = (int) stats.mean() - meteorSize;
        BlockPos meteorCenter = new BlockPos(pos.getX(), pos.getY() - meteorSize, pos.getZ());
        Collection<BlockPos> craterSphere = BlockHelper.getSphereOfBlocks(pos.below(), craterSize, craterSize * 1.2f, b -> !level.getBlockState(b).isAir());
        craterSphere.forEach(b -> {
            if (level.getBlockState(b.above()).isAir()) {
                level.setBlock(b, Blocks.AIR.defaultBlockState(), 3);
            }
        });
        craterSphere = DataHelper.reverseOrder(new ArrayList<>(), BlockHelper.getSphereOfBlocks(pos.above(2), meteorSize * 1.5f, meteorSize * 0.9f, b -> !level.getBlockState(b).isAir()));
        craterSphere.forEach(b -> {
            level.setBlock(b, Blocks.AIR.defaultBlockState(), 3);
        });

        carveTrajectoryHole(level, generator, pos, random, Mth.nextFloat(random, 0, 6.28f), 5, 12);

        Collection<BlockPos> meteoriteSphere = BlockHelper.getSphereOfBlocks(meteorCenter.above(), meteorSize);
        meteoriteSphere.forEach(b -> {
            level.setBlock(b, FufoBlocks.ORTUSITE.get().defaultBlockState(), 3);
        });
        return true;
    }

    public static boolean carveTrajectoryHole(WorldGenLevel level, ChunkGenerator generator, BlockPos pos, RandomSource random, float rotation, float craterSize, float iterations) {
        float cachedCraterSize = craterSize;
        float decrease = craterSize / iterations;
        for (int i = 1; i < iterations; i++) {
            float offset = craterSize * i;
            Vec3 offsetDirection = new Vec3(offset, 0, 0).yRot(rotation);
            BlockPos craterCenter = pos.offset(offsetDirection.x, offsetDirection.y + i / iterations * 3f, offsetDirection.z);
            Collection<BlockPos> craterSphere = BlockHelper.getSphereOfBlocks(craterCenter, craterSize, cachedCraterSize * 0.8f, b -> !level.getBlockState(b).isAir());
            craterSphere.forEach(b -> {
                if (level.getBlockState(b.above()).isAir()) {
                    level.setBlock(b, Blocks.AIR.defaultBlockState(), 3);
                }
            });
            craterSize -= craterSize / iterations;
        }
        return true;
    }
}