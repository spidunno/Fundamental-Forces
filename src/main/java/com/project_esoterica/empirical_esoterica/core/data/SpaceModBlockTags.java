package com.project_esoterica.empirical_esoterica.core.data;

import com.project_esoterica.empirical_esoterica.EmpiricalEsoterica;
import com.project_esoterica.empirical_esoterica.core.registry.block.BlockTagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.project_esoterica.empirical_esoterica.EsotericHelper.getModBlocks;
import static net.minecraft.tags.BlockTags.*;
import static net.minecraftforge.common.Tags.Blocks.DIRT;

public class SpaceModBlockTags extends BlockTagsProvider {
    public SpaceModBlockTags(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, EmpiricalEsoterica.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Space Mod Block Tags";
    }

    @Override
    protected void addTags() {
        tag(BlockTags.SLABS).add(getModBlocks(b -> b instanceof SlabBlock));
        tag(BlockTags.STAIRS).add(getModBlocks(b -> b instanceof StairBlock));
        tag(BlockTags.WALLS).add(getModBlocks(b -> b instanceof WallBlock));
        tag(BlockTags.FENCES).add(getModBlocks(b -> b instanceof FenceBlock));
        tag(BlockTags.FENCE_GATES).add(getModBlocks(b -> b instanceof FenceGateBlock));
        tag(BlockTags.LEAVES).add(getModBlocks(b -> b instanceof LeavesBlock));
        tag(DOORS).add(getModBlocks(b -> b instanceof DoorBlock));
        tag(TRAPDOORS).add(getModBlocks(b -> b instanceof TrapDoorBlock));
        tag(BUTTONS).add(getModBlocks(b -> b instanceof ButtonBlock));
        tag(WOODEN_BUTTONS).add(getModBlocks(b -> b instanceof WoodButtonBlock));
        tag(PRESSURE_PLATES).add(getModBlocks(b -> b instanceof BasePressurePlateBlock));
        tag(DIRT).add(getModBlocks(b -> b instanceof GrassBlock || b instanceof FarmBlock));
        tag(SAPLINGS).add(getModBlocks(b -> b instanceof SaplingBlock));

        tag(LOGS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_log") || b.getRegistryName().getPath().endsWith("wood")));
        tag(PLANKS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks")));
        tag(WOODEN_FENCES).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_fence")));
        tag(WOODEN_DOORS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_door")));
        tag(WOODEN_STAIRS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks_stairs")));
        tag(WOODEN_SLABS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks_slab")));
        tag(WOODEN_TRAPDOORS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_trapdoor")));
        tag(WOODEN_PRESSURE_PLATES).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks_pressure_plate")));

        tag(BlockTagRegistry.STARFALL_ALLOWED).add(Blocks.DIAMOND_BLOCK);

    }
}