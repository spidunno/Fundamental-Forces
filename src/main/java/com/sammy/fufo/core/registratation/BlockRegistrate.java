package com.sammy.fufo.core.registratation;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.block.*;
import com.sammy.fufo.common.blockentity.*;
import com.sammy.fufo.core.setup.content.item.tabs.ContentTab;
import com.sammy.ortus.systems.block.OrtusBlockProperties;
import com.sammy.ortus.systems.block.OrtusEntityBlock;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

import static com.sammy.fufo.core.setup.content.block.BlockPropertiesRegistry.*;

public class BlockRegistrate {
    private static final Registrate REGISTRATE = FufoMod.registrate().creativeModeTab(ContentTab::get);

    //TODO: figure out why generic arguments aren't inferred in the second generic in block registration, called prior to .setBlockEntity

    //region logistics

    //pipes
    public static final BlockEntry<PipeAnchorBlock<AnchorBlockEntity>> PIPE_ANCHOR = setupEntityBlockWithItem("anchor",
            PipeAnchorBlock::new, BlockEntityRegistrate.ANCHOR, CRUDE_PROPERTIES()).register();

    //machines
    public static final BlockEntry<BurnerExtractorBlock<BurnerExtractorBlockEntity>> BURNER_EXTRACTOR = setupEntityBlockWithItem("burner_extractor",
            BurnerExtractorBlock::new, BlockEntityRegistrate.BURNER_EXTRACTOR, CRUDE_PROPERTIES()).register();

    //weaving
    public static final BlockEntry<ArrayBlock<ArrayBlockEntity>> CRUDE_ARRAY = setupEntityBlockWithItem("crude_array",
            ArrayBlock::new, BlockEntityRegistrate.CRUDE_ARRAY, CRUDE_PROPERTIES()).register();
    public static final BlockEntry<CrudePrimerBlock<CrudePrimerBlockEntity>> CRUDE_PRIMER = setupEntityBlockWithItem("crude_primer",
            CrudePrimerBlock::new, BlockEntityRegistrate.CRUDE_PRIMER, CRUDE_PROPERTIES()).register();
    public static final BlockEntry<CrudeNeedleBlock<CrudeNeedleBlockEntity>> CRUDE_NEEDLE = setupEntityBlockWithItem("crude_needle",
            CrudeNeedleBlock::new, BlockEntityRegistrate.CRUDE_NEEDLE, CRUDE_PROPERTIES()).register();
    public static final BlockEntry<UITestBlock<UITestBlockEntity>> UI_TEST = setupEntityBlockWithItem("ui_test",
            UITestBlock::new, BlockEntityRegistrate.UI_TEST, CRUDE_PROPERTIES()).register();
    //endregion

    //region esoterics
    public static final BlockEntry<MeteorFlameBlock<MeteorFlameBlockEntity>> METEOR_FIRE = setupEntityBlock("meteor_fire", MeteorFlameBlock::new, BlockEntityRegistrate.METEOR_FLAME, METEOR_FIRE_PROPERTIES()).register();

    public static final BlockEntry<FlammableMeteoriteBlock> ORTUSITE =
            setupBlockWithItem("ortusite", (p) -> new FlammableMeteoriteBlock(p, (s, b) -> METEOR_FIRE.getDefaultState()), METEOR_FIRE_PROPERTIES()).register();

    public static final BlockEntry<Block> DEPLETED_ORTUSITE = setupBlockWithItem("depleted_ortusite", Block::new, ASTEROID_PROPERTIES()).register();

    public static final BlockEntry<OrbBlock<OrbBlockEntity>> FORCE_ORB = setupEntityBlock("force_orb",
           OrbBlock::new, BlockEntityRegistrate.ORB, ORB_PROPERTIES()).register();
    //endregion

    //region components
    public static final BlockEntry<Block> BLOCK_OF_CRACK = setupBlockWithItem("block_of_crack", Block::new, CRACK_PROPERTIES()).register();
    //endregion

    //region blocks
    public static final BlockEntry<GlassBlock> VOLCANIC_GLASS = setupBlockWithItem("volcanic_glass", GlassBlock::new, VOLCANIC_GLASS_PROPERTIES()).register();
    public static final BlockEntry<ScorchedEarthBlock> SCORCHED_EARTH = setupBlockWithItem("scorched_earth", ScorchedEarthBlock::new, SCORCHED_EARTH_PROPERTIES()).register();
    public static final BlockEntry<Block> CHARRED_ROCK = setupBlockWithItem("charred_rock", Block::new, CHARRED_ROCK_PROPERTIES()).register();
    public static final BlockEntry<SlabBlock> CHARRED_ROCK_SLAB = setupSlabBlock("charred_rock_slab", SlabBlock::new, CHARRED_ROCK_PROPERTIES(), CHARRED_ROCK).register();
    public static final BlockEntry<StairBlock> CHARRED_ROCK_STAIRS = setupStairsBlock("charred_rock_stairs", (p) -> new StairBlock(CHARRED_ROCK::getDefaultState, p), CHARRED_ROCK_PROPERTIES(), CHARRED_ROCK).register();
    public static final BlockEntry<Block> POLISHED_CHARRED_ROCK = setupBlockWithItem("polished_charred_rock", Block::new, CHARRED_ROCK_PROPERTIES()).register();
    public static final BlockEntry<SlabBlock> POLISHED_CHARRED_ROCK_SLAB = setupSlabBlock("polished_charred_rock_slab", SlabBlock::new, CHARRED_ROCK_PROPERTIES(), POLISHED_CHARRED_ROCK).register();
    public static final BlockEntry<StairBlock> POLISHED_CHARRED_ROCK_STAIRS = setupStairsBlock("polished_charred_rock_stairs", (p) -> new StairBlock(CHARRED_ROCK::getDefaultState, p), CHARRED_ROCK_PROPERTIES(), POLISHED_CHARRED_ROCK).register();
    //endregion

    public static <Y extends OrtusBlockEntity, T extends OrtusEntityBlock<Y>> BlockBuilder<T, Registrate> setupEntityBlockWithItem(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, Supplier<BlockEntityType<Y>> blockEntity, OrtusBlockProperties properties) {
        return setupEntityBlock(name, factory, blockEntity, properties).simpleItem();
    }

    public static <T extends Block> BlockBuilder<T, Registrate> setupBlockWithItem(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, OrtusBlockProperties properties) {
        return setupBlock(name, factory, properties).simpleItem();
    }

    public static <Y extends OrtusBlockEntity, T extends OrtusEntityBlock<Y>> BlockBuilder<T, Registrate> setupEntityBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, Supplier<BlockEntityType<Y>> blockEntity, OrtusBlockProperties properties) {
        return REGISTRATE.block(name, factory.andThen(b -> b.<T>setBlockEntity(blockEntity)))
                .properties((x) -> properties);
    }

    public static <T extends Block> BlockBuilder<T, Registrate> setupBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, OrtusBlockProperties properties) {
        return REGISTRATE.block(name, factory)
                .properties((x) -> properties);
    }

    public static <T extends StairBlock> BlockBuilder<T, Registrate> setupStairsBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, OrtusBlockProperties properties, RegistryEntry<? extends Block> parent) {
        return setupBlockWithItem(name, factory, properties).blockstate(stairState(parent));
    }

    public static <T extends SlabBlock> BlockBuilder<T, Registrate> setupSlabBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, OrtusBlockProperties properties, RegistryEntry<? extends Block> parent) {
        return setupBlockWithItem(name, factory, properties).blockstate(slabState(parent));
    }

    @SuppressWarnings("unchecked")
    public static <T extends SlabBlock> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> slabState(RegistryEntry<? extends Block> parent) {
        return (ctx, p) -> p.slabBlock(ctx.getEntry(), p.blockTexture(parent.get()), p.blockTexture(parent.get()));
    }

    @SuppressWarnings("unchecked")
    public static <T extends StairBlock> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> stairState(RegistryEntry<? extends Block> parent) {
        return (ctx, p) -> p.stairsBlock(ctx.getEntry(), p.blockTexture(parent.get()));
    }

    public static void register() {
    }
}