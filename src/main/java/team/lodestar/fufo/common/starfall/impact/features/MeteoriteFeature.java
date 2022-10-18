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
import team.lodestar.fufo.registry.common.FufoBlocks;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.*;

import static net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;

public class MeteoriteFeature extends Feature<NoneFeatureConfiguration> {
    public MeteoriteFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        return generateCrater(context.level(), context.chunkGenerator(), context.origin(), context.random());
    }

    private static final PerlinSimplexNoise NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(1234L)), ImmutableList.of(0));

    public static boolean generateCrater(WorldGenLevel level, ChunkGenerator generator, BlockPos pos, RandomSource random) {
        LodestoneBlockFiller filler = new LodestoneBlockFiller(false);
        Map<Integer, Double> noiseValues = new HashMap<>();
        for (int i = 0; i <= 360; i++) {
            noiseValues.put(i, NOISE.getValue(pos.getX() + pos.getZ() + i * 0.025f, pos.getY() / 0.05f, true) * 3.5f);
        }
        int radius = 32;
        int effectiveRadius = 16;
        int depth = 6;
        float step = 1f / depth;
        for (int i = -4; i < depth; i++) {
            BlockPos layerCenter = pos.below(i);
            float radiusMultiplier = (depth - i) * step;
            generateCraterLayer(level, filler, layerCenter, (int)(radius*radiusMultiplier), (int) (effectiveRadius*radiusMultiplier), radiusMultiplier, noiseValues);
        }
        filler.fill(level);

        return true;
    }

    public static void generateCraterLayer(WorldGenLevel level, LodestoneBlockFiller filler, BlockPos center, int radius, int effectiveRadius, float noiseIntensity, Map<Integer, Double> noiseValues) {
        int x = center.getX();
        int z = center.getZ();
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        for (int i = 0; i < radius * 2 + 1; i++) {
            for (int j = 0; j < radius * 2 + 1; j++) {
                int xp = x + i - radius;
                int zp = z + j - radius;
                blockPos.set(xp, center.getY(), zp);
                if (level.getBlockState(blockPos).isAir()) {
                    continue;
                }
                double theta = 180 + 180 / Math.PI * Math.atan2(x - xp, z - zp);
                double naturalNoiseValue = noiseValues.get(Mth.floor(theta)) * noiseIntensity;
                if (naturalNoiseValue > 1f) {
                    naturalNoiseValue *= naturalNoiseValue;
                }
                int floor = (int) Math.floor(pointDistancePlane(xp, zp, x, z));
                if (floor <= (effectiveRadius + Math.floor(naturalNoiseValue) - 1)) {
                    filler.entries.add(new LodestoneBlockFiller.BlockStateEntry(Blocks.AIR.defaultBlockState(), blockPos.immutable()));
                }
            }
        }
    }

    public static float pointDistancePlane(double x1, double z1, double x2, double z2) {
        return (float) Math.hypot(x1 - x2, z1 - z2);
    }
}