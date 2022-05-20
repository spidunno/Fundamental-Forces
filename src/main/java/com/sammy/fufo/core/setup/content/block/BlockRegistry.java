package com.sammy.fufo.core.setup.content.block;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.sammy.fufo.core.setup.content.block.BlockPropertiesRegistry.*;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FufoMod.FUFO);

    public static final RegistryObject<Block> BLOCK_OF_CRACK = BLOCKS.register("block_of_crack", () -> new Block(CRACK_PROPERTIES()));

    public static final RegistryObject<Block> FORCE_ORB = BLOCKS.register("force_orb", () -> new OrbBlock<>(ORB_PROPERTIES()).setBlockEntity(BlockEntityRegistry.ORB));
    public static final RegistryObject<Block> ANCHOR = BLOCKS.register("anchor", () -> new AnchorBlock<>(ANCHOR_PROPERTIES()).setBlockEntity(BlockEntityRegistry.ANCHOR));
    public static final RegistryObject<Block> BURNER_EXTRACTOR = BLOCKS.register("burner_extractor", () -> new BurnerExtractorBlock<>(BURNER_EXTRACTOR_PROPERTIES()).setBlockEntity(BlockEntityRegistry.BURNER_EXTRACTOR));

    public static final RegistryObject<Block> METEOR_FIRE = BLOCKS.register("meteor_fire", () -> new MeteorFlameBlock<>(METEOR_FIRE_PROPERTIES()).setBlockEntity(BlockEntityRegistry.METEOR_FLAME));
    public static final RegistryObject<Block> ORTUSITE = BLOCKS.register("ortusite", () -> new FlammableMeteoriteBlock(ASTEROID_ROCK_PROPERTIES(), (s, p) -> METEOR_FIRE.get().defaultBlockState()));
    public static final RegistryObject<Block> DEPLETED_ORTUSITE = BLOCKS.register("depleted_ortusite", () -> new Block(ASTEROID_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CHARRED_ROCK = BLOCKS.register("charred_rock", () -> new Block(CHARRED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_CHARRED_ROCK = BLOCKS.register("polished_charred_rock", () -> new Block(CHARRED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CHARRED_ROCK_SLAB = BLOCKS.register("charred_rock_slab", () -> new SlabBlock(CHARRED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_CHARRED_ROCK_SLAB = BLOCKS.register("polished_charred_rock_slab", () -> new SlabBlock(CHARRED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CHARRED_ROCK_STAIRS = BLOCKS.register("charred_rock_stairs", () -> new StairBlock(() -> CHARRED_ROCK.get().defaultBlockState(), CHARRED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_CHARRED_ROCK_STAIRS = BLOCKS.register("polished_charred_rock_stairs", () -> new StairBlock(() -> POLISHED_CHARRED_ROCK.get().defaultBlockState(), CHARRED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> VOLCANIC_GLASS = BLOCKS.register("volcanic_glass", () -> new GlassBlock(VOLCANIC_GLASS_PROPERTIES()));
    public static final RegistryObject<Block> SCORCHED_EARTH = BLOCKS.register("scorched_earth", () -> new ScorchedEarthBlock(SCORCHED_EARTH_PROPERTIES()));

    public static final RegistryObject<Block> UI_TEST_BLOCK = BLOCKS.register("ui_test_block" , () -> new UITestBlock(UI_TEST_BLOCK_PROPERTIES()).setBlockEntity(BlockEntityRegistry.UI_TEST_BLOCK));
}