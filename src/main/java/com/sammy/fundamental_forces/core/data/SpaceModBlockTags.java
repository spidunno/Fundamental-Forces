package com.sammy.fundamental_forces.core.data;

import com.sammy.fundamental_forces.FundamentalForcesMod;
import com.sammy.fundamental_forces.core.setup.content.block.BlockRegistry;
import com.sammy.fundamental_forces.core.setup.content.block.BlockTagRegistry;
import com.sammy.fundamental_forces.core.systems.block.SimpleBlockProperties;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static net.minecraft.tags.BlockTags.*;

public class SpaceModBlockTags extends BlockTagsProvider {
    public SpaceModBlockTags(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, FundamentalForcesMod.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Space Mod Block Tags";
    }

    @Override
    protected void addTags() {
        m_206424_(BlockTags.SLABS).add(getModBlocks(b -> b instanceof SlabBlock));
        m_206424_(BlockTags.STAIRS).add(getModBlocks(b -> b instanceof StairBlock));
        m_206424_(BlockTags.WALLS).add(getModBlocks(b -> b instanceof WallBlock));
        m_206424_(BlockTags.FENCES).add(getModBlocks(b -> b instanceof FenceBlock));
        m_206424_(BlockTags.FENCE_GATES).add(getModBlocks(b -> b instanceof FenceGateBlock));
        m_206424_(BlockTags.LEAVES).add(getModBlocks(b -> b instanceof LeavesBlock));
        m_206424_(DOORS).add(getModBlocks(b -> b instanceof DoorBlock));
        m_206424_(TRAPDOORS).add(getModBlocks(b -> b instanceof TrapDoorBlock));
        m_206424_(BUTTONS).add(getModBlocks(b -> b instanceof ButtonBlock));
        m_206424_(PRESSURE_PLATES).add(getModBlocks(b -> b instanceof PressurePlateBlock));
        m_206424_(DIRT).add(getModBlocks(b -> b instanceof GrassBlock || b instanceof FarmBlock));
        m_206424_(SAPLINGS).add(getModBlocks(b -> b instanceof SaplingBlock));

        m_206424_(LOGS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_log") || b.getRegistryName().getPath().endsWith("wood")));
        m_206424_(PLANKS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks")));
        m_206424_(WOODEN_FENCES).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_fence")));
        m_206424_(WOODEN_DOORS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_door")));
        m_206424_(WOODEN_STAIRS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks_stairs")));
        m_206424_(WOODEN_SLABS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks_slab")));
        m_206424_(WOODEN_TRAPDOORS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_trapdoor")));
        m_206424_(WOODEN_PRESSURE_PLATES).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks_pressure_plate")));

        for (Block block : getModBlocks(b -> b.properties instanceof SimpleBlockProperties)) {
            SimpleBlockProperties properties = (SimpleBlockProperties) block.properties;
            if (properties.needsPickaxe) {
                m_206424_(MINEABLE_WITH_PICKAXE).add(block);
            }
            if (properties.needsShovel) {
                m_206424_(MINEABLE_WITH_SHOVEL).add(block);
            }
            if (properties.needsAxe) {
                m_206424_(MINEABLE_WITH_AXE).add(block);
            }
            if (properties.needsHoe) {
                m_206424_(MINEABLE_WITH_HOE).add(block);
            }
            if (properties.needsStone) {
                m_206424_(NEEDS_STONE_TOOL).add(block);
            }
            if (properties.needsIron) {
                m_206424_(NEEDS_IRON_TOOL).add(block);
            }
            if (properties.needsDiamond) {
                m_206424_(NEEDS_DIAMOND_TOOL).add(block);
            }
        }
        m_206424_(BlockTagRegistry.TERRACOTTA).add(Registry.BLOCK.stream().filter(b -> b.getRegistryName().getPath().endsWith("terracotta")).toArray(Block[]::new));
        m_206424_(BlockTagRegistry.STARFALL_ALLOWED).add(Blocks.DIAMOND_BLOCK);
    }

    @Nonnull
    private Block[] getModBlocks(Predicate<Block> predicate) {
        List<Block> ret = new ArrayList<>(Collections.emptyList());
        BlockRegistry.BLOCKS.getEntries().stream()
                .filter(b -> predicate.test(b.get())).forEach(b -> ret.add(b.get()));
        return ret.toArray(new Block[0]);
    }
}