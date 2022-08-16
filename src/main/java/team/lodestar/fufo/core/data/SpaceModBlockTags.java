/*package team.lodestar.fufo.core.data;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.core.setup.content.block.BlockTagRegistry;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;
import team.lodestar.lodestone.systems.block.LodestoneThrowawayBlockData;
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
        super(generatorIn, FufoMod.FUFO, existingFileHelper);
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
        tag(PRESSURE_PLATES).add(getModBlocks(b -> b instanceof PressurePlateBlock));
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

        for (Block block : getModBlocks(b -> b.properties instanceof LodestoneBlockProperties)) {
            LodestoneBlockProperties properties = (LodestoneBlockProperties) block.properties;
            LodestoneThrowawayBlockData data = properties.getThrowawayData();
            if (data.needsPickaxe) {
                tag(MINEABLE_WITH_PICKAXE).add(block);
            }
            if (data.needsShovel) {
                tag(MINEABLE_WITH_SHOVEL).add(block);
            }
            if (data.needsAxe) {
                tag(MINEABLE_WITH_AXE).add(block);
            }
            if (data.needsHoe) {
                tag(MINEABLE_WITH_HOE).add(block);
            }
            if (data.needsStone) {
                tag(NEEDS_STONE_TOOL).add(block);
            }
            if (data.needsIron) {
                tag(NEEDS_IRON_TOOL).add(block);
            }
            if (data.needsDiamond) {
                tag(NEEDS_DIAMOND_TOOL).add(block);
            }
        }
        tag(BlockTagRegistry.STARFALL_ALLOWED).add(Blocks.DIAMOND_BLOCK);
    }

    @Nonnull
    private Block[] getModBlocks(Predicate<Block> predicate)
    {
        List<Block> ret = new ArrayList<>(Collections.emptyList());
        AllBlocks.BLOCKS.getEntries().stream()
                .filter(b -> predicate.test(b.get())).forEach(b -> ret.add(b.get()));
        return ret.toArray(new Block[0]);
    }
}*/