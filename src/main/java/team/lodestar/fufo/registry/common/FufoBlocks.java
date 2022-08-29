package team.lodestar.fufo.registry.common;

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
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
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
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.block.*;
import team.lodestar.fufo.common.blockentity.*;
import team.lodestar.fufo.common.fluid.PipeNodeBlock;
import team.lodestar.fufo.common.fluid.PipeNodeBlockEntity;
import team.lodestar.fufo.common.fluid.PipeNodeBlockItem;
import team.lodestar.fufo.common.fluid.fluid_tank.FluidTankItem;
import team.lodestar.fufo.common.fluid.fluid_tank.FluidTankBlock;
import team.lodestar.fufo.common.fluid.fluid_tank.FluidTankBlockEntity;
import team.lodestar.fufo.common.fluid.pump.PumpBlock;
import team.lodestar.fufo.common.fluid.pump.PumpBlockEntity;
import team.lodestar.fufo.common.fluid.sealed_barrel.SealedBarrelBlock;
import team.lodestar.fufo.common.fluid.sealed_barrel.SealedBarrelBlockEntity;
import team.lodestar.fufo.common.fluid.valve.ValveBlock;
import team.lodestar.fufo.common.fluid.valve.ValveBlockEntity;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;

import java.util.function.Function;

import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;
import static team.lodestar.fufo.FufoMod.fufoPath;

@SuppressWarnings("ConstantConditions")
public class FufoBlocks {
    private static final Registrate REGISTRATE = FufoMod.registrate().creativeModeTab(FufoCreativeTabs.FufoContentTab::get);

    //TODO: figure out why generic arguments aren't inferred in the second generic in block registration, called prior to .setBlockEntity


    //fluid management
    public static final BlockEntry<PipeNodeBlock<PipeNodeBlockEntity>> PIPE_ANCHOR = setupBlock("anchor",
            (p) -> new PipeNodeBlock<>(p).<PipeNodeBlock<PipeNodeBlockEntity>>setBlockEntity(FufoBlockEntities.ANCHOR), FufoBlockProperties.CRUDE_COPPER_PROPERTIES())
            .item(PipeNodeBlockItem::new)
            .build()
            .blockstate(predefinedState())
            .register();

    public static final BlockEntry<SealedBarrelBlock<SealedBarrelBlockEntity>> SEALED_BARREL = setupBlock("sealed_barrel", (p) -> new SealedBarrelBlock<>(p).<SealedBarrelBlock<SealedBarrelBlockEntity>>setBlockEntity(FufoBlockEntities.SEALED_BARREL), FufoBlockProperties.CRUDE_COPPER_PROPERTIES().isCutoutLayer())
            .blockstate((ctx, p) -> {
                Function<String, ModelFile> modelFunction = (s) -> (p.models().getExistingFile(fufoPath("block/logistics/sealed_barrel/" + s)));
                p.getVariantBuilder(ctx.get())
                        .partialState().with(SealedBarrelBlock.SHAPE, SealedBarrelBlock.Shape.NORMAL).modelForState().modelFile(modelFunction.apply("default")).addModel()
                        .partialState().with(SealedBarrelBlock.SHAPE, SealedBarrelBlock.Shape.NO_WINDOW).modelForState().modelFile(modelFunction.apply("no_window")).addModel();
            })
            .item(PipeNodeBlockItem::new)
            .recipe((ctx, p) -> {
                ShapedRecipeBuilder.shaped(ctx.get()).pattern("XZX").pattern("XYX").pattern("XZX").define('X', ItemTags.PLANKS).define('Z', ItemTags.WOODEN_SLABS).define('Y', Tags.Items.INGOTS_COPPER).group("sealed_barrel").unlockedBy("has_" + p.safeName(ctx.get()), has(Items.COPPER_INGOT)).save(p, p.safeId(ctx.get()));
                ShapelessRecipeBuilder.shapeless(ctx.get()).requires(Tags.Items.BARRELS_WOODEN).requires(Tags.Items.INGOTS_COPPER).group("sealed_barrel").unlockedBy("has_" + p.safeName(ctx.get()), has(Items.COPPER_INGOT)).save(p, fufoPath("sealed_barrel_simple"));
            })
            .model((ctx, p) -> ConfiguredModel.builder().modelFile(p.withExistingParent(p.name(ctx::getEntry), fufoPath("block/logistics/sealed_barrel/default"))).build())
            .build()
            .register();

    public static final BlockEntry<FluidTankBlock<FluidTankBlockEntity>> SEALED_TANK = setupBlock("sealed_tank", (p) -> new FluidTankBlock<>(p).<FluidTankBlock<FluidTankBlockEntity>>setBlockEntity(FufoBlockEntities.FLUID_TANK), FufoBlockProperties.CRUDE_IRON_PROPERTIES().isCutoutLayer())
            .blockstate((ctx, p) -> {
                Function<String, ModelFile> modelFunction = (s) -> (p.models().getExistingFile(fufoPath("block/logistics/sealed_tank/" + s)));
                p.getVariantBuilder(ctx.get())
                        .partialState().with(FluidTankBlock.TOP, true).with(FluidTankBlock.BOTTOM, true).modelForState().modelFile(modelFunction.apply("single_unit")).addModel()
                        .partialState().with(FluidTankBlock.TOP, true).with(FluidTankBlock.BOTTOM, false).modelForState().modelFile(modelFunction.apply("tall_unit_top")).addModel()
                        .partialState().with(FluidTankBlock.TOP, false).with(FluidTankBlock.BOTTOM, false).modelForState().modelFile(modelFunction.apply("tall_unit_middle")).addModel()
                        .partialState().with(FluidTankBlock.TOP, false).with(FluidTankBlock.BOTTOM, true).modelForState().modelFile(modelFunction.apply("tall_unit_bottom")).addModel();
            })
            .item(FluidTankItem::new)
            .model((ctx, p) -> ConfiguredModel.builder().modelFile(p.withExistingParent(p.name(ctx::getEntry), fufoPath("block/logistics/sealed_tank/single_unit"))).build())
            .build()
            .register();

    public static final BlockEntry<ValveBlock<ValveBlockEntity>> VALVE = setupBlock("valve",
            (p) -> new ValveBlock<>(p).<ValveBlock<ValveBlockEntity>>setBlockEntity(FufoBlockEntities.VALVE), FufoBlockProperties.CRUDE_PROPERTIES())
            .blockstate((ctx, p) -> {
                ModelFile model = p.models().getExistingFile(fufoPath("block/logistics/valve"));
                p.getVariantBuilder(ctx.get())
                        .partialState().with(PipeNodeBlock.AXIS, Direction.Axis.Y).modelForState().modelFile(model).addModel()
                        .partialState().with(PipeNodeBlock.AXIS, Direction.Axis.Z).modelForState().modelFile(model).rotationX(90).addModel()
                        .partialState().with(PipeNodeBlock.AXIS, Direction.Axis.X).modelForState().modelFile(model).rotationX(90).rotationY(90).addModel();
            })
            .item(PipeNodeBlockItem::new)
            .model((ctx, p) -> ConfiguredModel.builder().modelFile(p.withExistingParent(p.name(ctx::getEntry), fufoPath("block/logistics/valve"))).build())
            .build()
            .register();

    public static final BlockEntry<PumpBlock<PumpBlockEntity>> PUMP = setupBlock("pump",
            (p) -> new PumpBlock<>(p).<PumpBlock<PumpBlockEntity>>setBlockEntity(FufoBlockEntities.PUMP), FufoBlockProperties.CRUDE_PROPERTIES())
            .blockstate(invisibleState())
            .item(PipeNodeBlockItem::new)
            .model((ctx, p) -> ConfiguredModel.builder().modelFile(p.withExistingParent(p.name(ctx::getEntry), fufoPath("block/logistics/valve"))).build())
            //.model((ctx, p) -> ConfiguredModel.builder().modelFile(p.withExistingParent(p.name(ctx::getEntry), fufoPath("block/logistics/pump"))).build())
            .build()
            .register();

    //machines
//    public static final BlockEntry<BurnerExtractorBlock<BurnerExtractorBlockEntity>> BURNER_EXTRACTOR = setupItemBlock("burner_extractor", (p) -> new BurnerExtractorBlock<>(p).<BurnerExtractorBlock<BurnerExtractorBlockEntity>>setBlockEntity(BlockEntityRegistrate.BURNER_EXTRACTOR), CRUDE_PROPERTIES())
//        .blockstate(invisibleState())
//        .register();

    public static final BlockEntry<ArrayBlock<ArrayBlockEntity>> CRUDE_ARRAY = setupItemBlock("crude_array", (p) -> new ArrayBlock<>(p).<ArrayBlock<ArrayBlockEntity>>setBlockEntity(FufoBlockEntities.CRUDE_ARRAY), FufoBlockProperties.CRUDE_PROPERTIES())
            .blockstate((ctx, p) -> p.horizontalBlock(ctx.get(), p.models().getExistingFile(fufoPath("block/crude_array"))))
            .register();
    public static final BlockEntry<CrudePrimerBlock<CrudePrimerBlockEntity>> CRUDE_PRIMER = setupItemBlock("crude_primer", (p) -> new CrudePrimerBlock<>(p).<CrudePrimerBlock<CrudePrimerBlockEntity>>setBlockEntity(FufoBlockEntities.CRUDE_PRIMER), FufoBlockProperties.CRUDE_PROPERTIES())
            .blockstate((ctx, p) -> p.horizontalBlock(ctx.get(), p.models().getExistingFile(fufoPath("block/crude_primer"))))
            .register();
    public static final BlockEntry<CrudeNeedleBlock<CrudeNeedleBlockEntity>> CRUDE_NEEDLE = setupItemBlock("crude_needle", (p) -> new CrudeNeedleBlock<>(p).<CrudeNeedleBlock<CrudeNeedleBlockEntity>>setBlockEntity(FufoBlockEntities.CRUDE_NEEDLE), FufoBlockProperties.CRUDE_PROPERTIES()).blockstate(invisibleState()).register();
    public static final BlockEntry<UITestBlock<UITestBlockEntity>> UI_TEST = setupItemBlock("ui_test", (p) -> new UITestBlock<>(p).<UITestBlock<UITestBlockEntity>>setBlockEntity(FufoBlockEntities.UI_TEST_BLOCK), FufoBlockProperties.CRUDE_PROPERTIES()).blockstate(invisibleState()).register();

    public static final BlockEntry<MeteorFlameBlock<MeteorFlameBlockEntity>> METEOR_FIRE = setupBlock("meteor_fire", (p) -> new MeteorFlameBlock<>(p).<MeteorFlameBlock<MeteorFlameBlockEntity>>setBlockEntity(FufoBlockEntities.METEOR_FLAME), FufoBlockProperties.METEOR_FIRE_PROPERTIES()).blockstate(predefinedState()).register();
    public static final BlockEntry<FlammableMeteoriteBlock> ORTUSITE = setupItemBlock("ortusite", (p) -> new FlammableMeteoriteBlock(p, (s, b) -> METEOR_FIRE.getDefaultState()), FufoBlockProperties.ASTEROID_PROPERTIES())
            .blockstate((ctx, p) -> p.getVariantBuilder(ctx.getEntry()).forAllStates(s -> {
                ResourceLocation registryName = ForgeRegistries.BLOCKS.getKey(ctx.get());
                String newPath = "block/" + registryName.getPath() + "_" + s.getValue(FlammableMeteoriteBlock.DEPLETION_STATE);
                return ConfiguredModel.builder().modelFile(p.models().cubeAll(newPath, new ResourceLocation(registryName.getNamespace(), newPath))).build();
            }))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .loot(depletedShardLootTable())
            .item()
            .model((ctx, p) -> ConfiguredModel.builder().modelFile(p.withExistingParent(p.name(ctx::getEntry), fufoPath("block/ortusite_0"))).build())
            .build()
            .register();

    public static final BlockEntry<OrbBlock<OrbBlockEntity>> FORCE_ORB = setupBlock("force_orb", (p) -> new OrbBlock<>(p).<OrbBlock<OrbBlockEntity>>setBlockEntity(FufoBlockEntities.ORB), FufoBlockProperties.ORB_PROPERTIES()).blockstate(invisibleState()).register();

    public static final BlockEntry<Block> BLOCK_OF_CRACK = setupItemBlock("block_of_crack", Block::new, FufoBlockProperties.CRACK_PROPERTIES()).register();

    public static <T extends StairBlock> BlockBuilder<T, Registrate> setupStairsBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, LodestoneBlockProperties properties, RegistryEntry<? extends Block> parent) {
        return setupItemBlock(name, factory, properties).blockstate(stairState(parent));
    }

    public static <T extends SlabBlock> BlockBuilder<T, Registrate> setupSlabBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, LodestoneBlockProperties properties, RegistryEntry<? extends Block> parent) {
        return setupItemBlock(name, factory, properties).blockstate(slabState(parent));
    }

    public static <T extends Block> BlockBuilder<T, Registrate> setupItemBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, LodestoneBlockProperties properties) {
        return setupBlock(name, factory, properties).simpleItem();
    }

    public static <T extends Block> BlockBuilder<T, Registrate> setupBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> factory, LodestoneBlockProperties properties) {
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
        return (ctx, p) -> {
        };
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
                    depletedShards.add(LootItem.lootTableItem(FufoItems.DEPLETED_ORTUSITE_CHUNK.get())
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(b).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FlammableMeteoriteBlock.DEPLETION_STATE, i)))
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(i))));
                }
                if (fullShards != 0) {
                    normalShards.add(LootItem.lootTableItem(FufoItems.ORTUSITE_CHUNK.get())
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