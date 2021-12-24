package com.project_esoterica.esoterica.common.worldgen;

import com.project_esoterica.esoterica.core.helper.BlockHelper;
import com.project_esoterica.esoterica.core.registry.block.BlockRegistry;
import com.project_esoterica.esoterica.core.systems.worldgen.SimpleFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.GeodeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Random;

public class MeteoriteFeature extends SimpleFeature {
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        return generateMeteorite(context.level(), context.chunkGenerator(), context.origin(), context.random());
    }

    public static boolean generateMeteorite(WorldGenLevel level, ChunkGenerator generator, BlockPos pos, Random random) {
        int meteorSize = 4;
        int craterSize = 7;
        ArrayList<BlockPos> craterSphere = BlockHelper.getSphereOfBlocks(pos, craterSize, craterSize/2f, b -> !level.getBlockState(b).isAir());
        craterSphere.forEach(b -> {
            level.setBlock(b, Blocks.AIR.defaultBlockState(),3);
        });
        BlockPos craterBottom = pos.below(craterSize/2);
        ArrayList<BlockPos> meteoriteSphere = BlockHelper.getSphereOfBlocks(craterBottom, meteorSize);
        meteoriteSphere.forEach(b -> {
            level.setBlock(b, BlockRegistry.ASTEROID_ROCK.get().defaultBlockState(),3);
        });
        return true;
    }
}