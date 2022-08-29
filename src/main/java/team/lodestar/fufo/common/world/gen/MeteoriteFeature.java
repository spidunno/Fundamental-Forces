package team.lodestar.fufo.common.world.gen;

import com.google.common.math.StatsAccumulator;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;
import team.lodestar.fufo.registry.common.FufoBlocks;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.helpers.DataHelper;

import java.util.ArrayList;
import java.util.Collection;

import static net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;

@SuppressWarnings("all")
public class MeteoriteFeature extends Feature<NoneFeatureConfiguration> {
    public MeteoriteFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        return generateMeteorite(context.level(), context.chunkGenerator(), context.origin(), context.random());
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