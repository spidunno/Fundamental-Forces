package com.sammy.fufo.core.registratation;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.block.*;
import com.sammy.fufo.common.blockentity.*;
import com.sammy.fufo.common.item.FluidTankItem;
import com.sammy.fufo.common.logistics.fluid_tank.FluidTankBlock;
import com.sammy.fufo.common.logistics.fluid_tank.FluidTankBlockEntity;
import com.sammy.fufo.core.setup.content.item.tabs.ContentTab;
import com.sammy.ortus.systems.block.OrtusBlockProperties;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Function;

import static com.sammy.fufo.FufoMod.fufoPath;
import static com.sammy.fufo.core.setup.content.block.BlockPropertiesRegistry.*;

@SuppressWarnings("ConstantConditions")
public class BlockRegistrate {
    private static final Registrate REGISTRATE = FufoMod.registrate().creativeModeTab(ContentTab::get);

    //TODO: figure out why generic arguments aren't inferred in the second generic in block registration, called prior to .setBlockEntity


    //fluid management
    public static final BlockEntry<PipeNodeBlock<PipeNodeBlockEntity>> PIPE_ANCHOR = setupItemBlock("anchor",
        (p) -> new PipeNodeBlock<>(p).<PipeNodeBlock<PipeNodeBlockEntity>>setBlockEntity(BlockEntityRegistrate.ANCHOR), CRUDE_PROPERTIES())
        .blockstate(predefinedState())
        .register();

    public static final BlockEntry<PipeNodeBlock<PumpBlockEntity>> PUMP = setupItemBlock("pump",
        (p) -> new PipeNodeBlock<PumpBlockEntity>(p).<PipeNodeBlock<PumpBlockEntity>>setBlockEntity(BlockEntityRegistrate.PUMP), CRUDE_PROPERTIES())
        .blockstate(predefinedState())
        .register();

    public static final BlockEntry<PipeNodeBlock<ValveBlockEntity>> VALVE = setupItemBlock("valve",
        (p) -> new PipeNodeBlock<ValveBlockEntity>(p).<PipeNodeBlock<ValveBlockEntity>>setBlockEntity(BlockEntityRegistrate.VALVE), CRUDE_PROPERTIES())
        .blockstate(predefinedState())
        .register();

    public static final BlockEntry<FluidTankBlock<FluidTankBlockEntity>> FLUID_TANK = setupBlock("fluid_tank", (p) -> new FluidTankBlock<>(p).<FluidTankBlock<FluidTankBlockEntity>>setBlockEntity(BlockEntityRegistrate.FLUID_TANK), CRUDE_PROPERTIES())
        .blockstate((ctx, p) -> {
            ResourceLocation registryName = ctx.get().getRegistryName();
            Function<String, ModelFile> modelFunction = (s) -> (p.models().getExistingFile(fufoPath("block/logistics/sealed_tank/" + s)));
            p.getVariantBuilder(ctx.get())
                .partialState().with(FluidTankBlock.TOP, true).with(FluidTankBlock.BOTTOM, true).modelForState().modelFile(modelFunction.apply("single_unit")).addModel()
                .partialState().with(FluidTankBlock.TOP, true).with(FluidTankBlock.BOTTOM, false).modelForState().modelFile(modelFunction.apply("tall_unit_top")).addModel()
                .partialState().with(FluidTankBlock.TOP, false).with(FluidTankBlock.BOTTOM, false).modelForState().modelFile(modelFunction.apply("tall_unit_middle")).addModel()
                .partialState().with(FluidTankBlock.TOP, false).with(FluidTankBlock.BOTTOM, true).modelForState().modelFile(modelFunction.apply("tall_unit_bottom")).addModel();
        })
        .item(FluidTankItem::new)
        .build()
        .register();

    //machines
    public static final BlockEntry<BurnerExtractorBlock<BurnerExtractorBlockEntity>> BURNER_EXTRACTOR = setupItemBlock("burner_extractor", (p) -> new BurnerExtractorBlock<>(p).<BurnerExtractorBlock<BurnerExtractorBlockEntity>>setBlockEntity(BlockEntityRegistrate.BURNER_EXTRACTOR), CRUDE_PROPERTIES())
        .blockstate(invisibleState())
        .register();


    public static final BlockEntry<ArrayBlock<ArrayBlockEntity>> CRUDE_ARRAY = setupItemBlock("crude_array", (p) -> new ArrayBlock<>(p).<ArrayBlock<ArrayBlockEntity>>setBlockEntity(BlockEntityRegistrate.CRUDE_ARRAY), CRUDE_PROPERTIES()).blockstate(predefinedState()).register();
    public static final BlockEntry<CrudePrimerBlock<CrudePrimerBlockEntity>> CRUDE_PRIMER = setupItemBlock("crude_primer", (p) -> new CrudePrimerBlock<>(p).<CrudePrimerBlock<CrudePrimerBlockEntity>>setBlockEntity(BlockEntityRegistrate.CRUDE_PRIMER), CRUDE_PROPERTIES()).blockstate(predefinedState()).register();
    public static final BlockEntry<CrudeNeedleBlock<CrudeNeedleBlockEntity>> CRUDE_NEEDLE = setupItemBlock("crude_needle", (p) -> new CrudeNeedleBlock<>(p).<CrudeNeedleBlock<CrudeNeedleBlockEntity>>setBlockEntity(BlockEntityRegistrate.CRUDE_NEEDLE), CRUDE_PROPERTIES()).blockstate(invisibleState()).register();
    public static final BlockEntry<UITestBlock<UITestBlockEntity>> UI_TEST = setupItemBlock("ui_test", (p) -> new UITestBlock<>(p).<UITestBlock<UITestBlockEntity>>setBlockEntity(BlockEntityRegistrate.UI_TEST_BLOCK), CRUDE_PROPERTIES()).blockstate(invisibleState()).register();

    public static final BlockEntry<MeteorFlameBlock<MeteorFlameBlockEntity>> METEOR_FIRE = setupBlock("meteor_fire", (p) -> new MeteorFlameBlock<>(p).<MeteorFlameBlock<MeteorFlameBlockEntity>>setBlockEntity(BlockEntityRegistrate.METEOR_FLAME), METEOR_FIRE_PROPERTIES()).blockstate(predefinedState()).register();
    public static final BlockEntry<FlammableMeteoriteBlock> ORTUSITE = setupItemBlock("ortusite", (p) -> new FlammableMeteoriteBlock(p, (s, b) -> METEOR_FIRE.getDefaultState()), ASTEROID_PROPERTIES())
        .blockstate((ctx, p) -> p.getVariantBuilder(ctx.getEntry()).forAllStates(s -> {
            ResourceLocation registryName = ctx.get().getRegistryName();
            String newPath = "block/" + registryName.getPath() + "_" + s.getValue(FlammableMeteoriteBlock.DEPLETION_STATE);
            return ConfiguredModel.builder().modelFile(p.models().cubeAll(newPath, new ResourceLocation(registryName.getNamespace(), newPath))).build();
        }))
        .tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .loot(depletedShardLootTable())
        .register();

    public static final BlockEntry<OrbBlock<OrbBlockEntity>> FORCE_ORB = setupBlock("force_orb", (p) -> new OrbBlock<>(p).<OrbBlock<OrbBlockEntity>>setBlockEntity(BlockEntityRegistrate.ORB), ORB_PROPERTIES()).blockstate(invisibleState()).register();

    public static final BlockEntry<Block> BLOCK_OF_CRACK = setupItemBlock("block_of_crack", Block::new, CRACK_PROPERTIES()).register();

    public static final BlockEntry<GlassBlock> VOLCANIC_GLASS = setupItemBlock("volcanic_glass", GlassBlock::new, VOLCANIC_GLASS_PROPERTIES()).register();
    public static final BlockEntry<ScorchedEarthBlock> SCORCHED_EARTH = setupItemBlock("scorched_earth", ScorchedEarthBlock::new, SCORCHED_EARTH_PROPERTIES()).blockstate(invisibleState()).register();
    public static final BlockEntry<Block> CHARRED_ROCK = setupItemBlock("charred_rock", Block::new, CHARRED_ROCK_PROPERTIES()).register();
    public static final BlockEntry<SlabBlock> CHARRED_ROCK_SLAB = setupSlabBlock("charred_rock_slab", SlabBlock::new, CHARRED_ROCK_PROPERTIES(), CHARRED_ROCK).register();
    public static final BlockEntry<StairBlock> CHARRED_ROCK_STAIRS = setupStairsBlock("charred_rock_stairs", (p) -> new StairBlock(CHARRED_ROCK::getDefaultState, p), CHARRED_ROCK_PROPERTIES(), CHARRED_ROCK).register();
    public static final BlockEntry<Block> POLISHED_CHARRED_ROCK = setupItemBlock("polished_charred_rock", Block::new, CHARRED_ROCK_PROPERTIES()).register();
    public static final BlockEntry<SlabBlock> POLISHED_CHARRED_ROCK_SLAB = setupSlabBlock("polished_charred_rock_slab", SlabBlock::new, CHARRED_ROCK_PROPERTIES(), POLISHED_CHARRED_ROCK).register();
    public static final BlockEntry<StairBlock> POLISHED_CHARRED_ROCK_STAIRS = setupStairsBlock("polished_charred_rock_stairs", (p) -> new StairBlock(CHARRED_ROCK::getDefaultState, p), CHARRED_ROCK_PROPERTIES(), POLISHED_CHARRED_ROCK).register();

    public static <T extends StairBlock> BlockBuilder<T, Registrate> setupStairsBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, OrtusBlockProperties properties, RegistryEntry<? extends Block> parent) {
        return setupItemBlock(name, factory, properties).blockstate(stairState(parent));
    }

    public static <T extends SlabBlock> BlockBuilder<T, Registrate> setupSlabBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, OrtusBlockProperties properties, RegistryEntry<? extends Block> parent) {
        return setupItemBlock(name, factory, properties).blockstate(slabState(parent));
    }

    public static <T extends Block> BlockBuilder<T, Registrate> setupItemBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, OrtusBlockProperties properties) {
        return setupBlock(name, factory, properties).simpleItem();
    }

    public static <T extends Block> BlockBuilder<T, Registrate> setupBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, OrtusBlockProperties properties) {
        return REGISTRATE.block(name, factory).properties((x) -> properties);
    }

    @SuppressWarnings("unchecked")
    public static <T extends SlabBlock> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> slabState(RegistryEntry<? extends Block> parent) {
        return (ctx, p) -> p.slabBlock(ctx.getEntry(), p.blockTexture(parent.get()), p.blockTexture(parent.get()));
    }

    @SuppressWarnings("unchecked")
    public static <T extends StairBlock> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> stairState(RegistryEntry<? extends Block> parent) {
        return (ctx, p) -> p.stairsBlock(ctx.getEntry(), p.blockTexture(parent.get()));
    }

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> invisibleState() {
        return (ctx, p) -> p.getVariantBuilder(ctx.getEntry()).forAllStates(s -> ConfiguredModel.builder().modelFile(p.models().getExistingFile(new ResourceLocation("block/air"))).build());
    }

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> predefinedState() {
        return (ctx, p) -> {};
    }


    public static <T extends FlammableMeteoriteBlock> NonNullBiConsumer<RegistrateBlockLootTables, T> depletedShardLootTable() { //TODO: do something with this, it's kind of an eyesore.
        return (l, b) -> {
            LootTable.Builder builder = LootTable.lootTable();
            LootPool.Builder normalShards = LootPool.lootPool().when(ExplosionCondition.survivesExplosion());
            LootPool.Builder depletedShards = LootPool.lootPool().when(ExplosionCondition.survivesExplosion());
            int size = FlammableMeteoriteBlock.DEPLETION_STATE.getPossibleValues().size() - 1;
            for (int i = 0; i <= size; i++) {
                int fullShards = size - i;
                if (i != 0) {
                    depletedShards.add(LootItem.lootTableItem(ItemRegistrate.DEPLETED_ORTUSITE_CHUNK.get())
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(b).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FlammableMeteoriteBlock.DEPLETION_STATE, i)))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(i))));
                }
                if (fullShards != 0) {
                    normalShards.add(LootItem.lootTableItem(ItemRegistrate.ORTUSITE_CHUNK.get())
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(b).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FlammableMeteoriteBlock.DEPLETION_STATE, i)))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(fullShards))));
                }
            }
            l.add(b, builder.withPool(depletedShards).withPool(normalShards));
        };
    }

    public static void register() {
    }
}