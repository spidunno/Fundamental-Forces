package com.space_mod_group.space_mod.core.data;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.space_mod_group.space_mod.core.registry.BlockRegistry;
import net.minecraft.advancements.criterion.*;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.*;
import net.minecraft.state.Property;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.space_mod_group.space_mod.SpaceHelper.takeAll;

public class SpaceModBlockLootTables extends LootTableProvider
{
    private static final ILootCondition.IBuilder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private static final ILootCondition.IBuilder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    private static final ILootCondition.IBuilder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final ILootCondition.IBuilder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final ILootCondition.IBuilder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(IItemProvider::asItem).collect(ImmutableSet.toImmutableSet());
    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    private static final float[] JUNGLE_LEAVES_SAPLING_CHANGES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = new ArrayList<>();

    public SpaceModBlockLootTables(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
    }

    @Override
    public String getName()
    {
        return "Space Mod Loot Tables";
    }


    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BlockRegistry.BLOCKS.getEntries());

        takeAll(blocks, b -> b.get() instanceof WallTorchBlock);
        takeAll(blocks, b -> b.get() instanceof LeavesBlock);
        takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(b -> registerLootTable(b.get(), createSingleItemTable(b.get().asItem())));
        takeAll(blocks, b -> b.get() instanceof DoublePlantBlock).forEach(b -> registerLootTable(b.get(), createSilkTouchOrShearsTable(b.get().asItem())));
        takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(b -> registerLootTable(b.get(), createSilkTouchOrShearsTable(b.get().asItem())));

        takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(b -> registerLootTable(b.get(), createSingleItemTableWithSilkTouch(b.get(), Items.DIRT)));
        ;
        takeAll(blocks, b -> b.get() instanceof SlabBlock).forEach(b -> registerLootTable(b.get(), createSlabItemTable(b.get())));
        takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(b -> registerLootTable(b.get(), createDoorTable(b.get())));

        takeAll(blocks, b -> true).forEach(b -> registerLootTable(b.get(), createSingleItemTable(b.get().asItem())));

        return tables;
    }

    //everything below sucks a lot, don't go down there.

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker)
    {
        map.forEach((loc, table) -> LootTableManager.validate(validationtracker, loc, table));
    }

    protected static <T> T applyExplosionDecay(IItemProvider p_218552_0_, ILootFunctionConsumer<T> p_218552_1_) {
        return (T)(!EXPLOSION_RESISTANT.contains(p_218552_0_.asItem()) ? p_218552_1_.apply(ExplosionDecay.explosionDecay()) : p_218552_1_.unwrap());
    }

    protected static <T> T applyExplosionCondition(IItemProvider p_218560_0_, ILootConditionConsumer<T> p_218560_1_) {
        return (T)(!EXPLOSION_RESISTANT.contains(p_218560_0_.asItem()) ? p_218560_1_.when(SurvivesExplosion.survivesExplosion()) : p_218560_1_.unwrap());
    }

    protected static LootTable.Builder createSingleItemTable(IItemProvider p_218546_0_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_218546_0_, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_218546_0_))));
    }

    protected static LootTable.Builder createSelfDropDispatchTable(Block p_218494_0_, ILootCondition.IBuilder p_218494_1_, LootEntry.Builder<?> p_218494_2_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_218494_0_).when(p_218494_1_).otherwise(p_218494_2_)));
    }

    protected static LootTable.Builder createSilkTouchDispatchTable(Block p_218519_0_, LootEntry.Builder<?> p_218519_1_) {
        return createSelfDropDispatchTable(p_218519_0_, HAS_SILK_TOUCH, p_218519_1_);
    }

    protected static LootTable.Builder createShearsDispatchTable(Block p_218511_0_, LootEntry.Builder<?> p_218511_1_) {
        return createSelfDropDispatchTable(p_218511_0_, HAS_SHEARS, p_218511_1_);
    }

    protected static LootTable.Builder createSilkTouchOrShearsDispatchTable(Block p_218535_0_, LootEntry.Builder<?> p_218535_1_) {
        return createSelfDropDispatchTable(p_218535_0_, HAS_SHEARS_OR_SILK_TOUCH, p_218535_1_);
    }

    protected static LootTable.Builder createSingleItemTableWithSilkTouch(Block p_218515_0_, IItemProvider p_218515_1_) {
        return createSilkTouchDispatchTable(p_218515_0_, applyExplosionCondition(p_218515_0_, ItemLootEntry.lootTableItem(p_218515_1_)));
    }

    protected static LootTable.Builder createSingleItemTable(IItemProvider p_218463_0_, IRandomRange p_218463_1_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(applyExplosionDecay(p_218463_0_, ItemLootEntry.lootTableItem(p_218463_0_).apply(SetCount.setCount(p_218463_1_)))));
    }

    protected static LootTable.Builder createSingleItemTableWithSilkTouch(Block p_218530_0_, IItemProvider p_218530_1_, IRandomRange p_218530_2_) {
        return createSilkTouchDispatchTable(p_218530_0_, applyExplosionDecay(p_218530_0_, ItemLootEntry.lootTableItem(p_218530_1_).apply(SetCount.setCount(p_218530_2_))));
    }

    protected static LootTable.Builder createSilkTouchOnlyTable(IItemProvider p_218561_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SILK_TOUCH).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_218561_0_)));
    }

    protected static LootTable.Builder createSilkTouchOrShearsTable(IItemProvider p_218561_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SHEARS_OR_SILK_TOUCH).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_218561_0_)));
    }

    protected static LootTable.Builder createPotFlowerItemTable(IItemProvider p_218523_0_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(Blocks.FLOWER_POT, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(Blocks.FLOWER_POT)))).withPool(applyExplosionCondition(p_218523_0_, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_218523_0_))));
    }

    protected static LootTable.Builder createSlabItemTable(Block p_218513_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(applyExplosionDecay(p_218513_0_, ItemLootEntry.lootTableItem(p_218513_0_).apply(SetCount.setCount(ConstantRange.exactly(2)).when(BlockStateProperty.hasBlockStateProperties(p_218513_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }

    protected static <T extends Comparable<T> & IStringSerializable> LootTable.Builder createSinglePropConditionTable(Block p_218562_0_, Property<T> p_218562_1_, T p_218562_2_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_218562_0_, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_218562_0_).when(BlockStateProperty.hasBlockStateProperties(p_218562_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(p_218562_1_, p_218562_2_))))));
    }

    protected static LootTable.Builder createNameableBlockEntityTable(Block p_218481_0_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_218481_0_, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_218481_0_).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)))));
    }

    protected static LootTable.Builder createShulkerBoxDrop(Block p_218544_0_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_218544_0_, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_218544_0_).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Lock", "BlockEntityTag.Lock").copy("LootTable", "BlockEntityTag.LootTable").copy("LootTableSeed", "BlockEntityTag.LootTableSeed")).apply(SetContents.setContents().withEntry(DynamicLootEntry.dynamicEntry(ShulkerBoxBlock.CONTENTS))))));
    }

    protected static LootTable.Builder createBannerDrop(Block p_218559_0_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_218559_0_, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_218559_0_).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Patterns", "BlockEntityTag.Patterns")))));
    }

    private static LootTable.Builder createBeeNestDrop(Block p_229436_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SILK_TOUCH).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_229436_0_).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Bees", "BlockEntityTag.Bees")).apply(CopyBlockState.copyState(p_229436_0_).copy(BeehiveBlock.HONEY_LEVEL))));
    }

    private static LootTable.Builder createBeeHiveDrop(Block p_229437_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_229437_0_).when(HAS_SILK_TOUCH).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Bees", "BlockEntityTag.Bees")).apply(CopyBlockState.copyState(p_229437_0_).copy(BeehiveBlock.HONEY_LEVEL)).otherwise(ItemLootEntry.lootTableItem(p_229437_0_))));
    }

    protected static LootTable.Builder createOreDrop(Block p_218476_0_, Item p_218476_1_) {
        return createSilkTouchDispatchTable(p_218476_0_, applyExplosionDecay(p_218476_0_, ItemLootEntry.lootTableItem(p_218476_1_).apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected static LootTable.Builder createMushroomBlockDrop(Block p_218491_0_, IItemProvider p_218491_1_) {
        return createSilkTouchDispatchTable(p_218491_0_, applyExplosionDecay(p_218491_0_, ItemLootEntry.lootTableItem(p_218491_1_).apply(SetCount.setCount(RandomValueRange.between(-6.0F, 2.0F))).apply(LimitCount.limitCount(IntClamper.lowerBound(0)))));
    }

    protected static LootTable.Builder createGrassDrops(Block p_218570_0_) {
        return createShearsDispatchTable(p_218570_0_, applyExplosionDecay(p_218570_0_, ItemLootEntry.lootTableItem(Items.WHEAT_SEEDS).when(RandomChance.randomChance(0.125F)).apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))));
    }

    protected static LootTable.Builder createStemDrops(Block p_218475_0_, Item p_218475_1_) {
        return LootTable.lootTable().withPool(applyExplosionDecay(p_218475_0_, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_218475_1_).apply(SetCount.setCount(BinomialRange.binomial(3, 0.06666667F)).when(BlockStateProperty.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 0)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.13333334F)).when(BlockStateProperty.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 1)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.2F)).when(BlockStateProperty.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 2)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.26666668F)).when(BlockStateProperty.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 3)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.33333334F)).when(BlockStateProperty.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 4)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.4F)).when(BlockStateProperty.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 5)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.46666667F)).when(BlockStateProperty.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 6)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.53333336F)).when(BlockStateProperty.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 7)))))));
    }

    private static LootTable.Builder createAttachedStemDrops(Block p_229435_0_, Item p_229435_1_) {
        return LootTable.lootTable().withPool(applyExplosionDecay(p_229435_0_, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_229435_1_).apply(SetCount.setCount(BinomialRange.binomial(3, 0.53333336F))))));
    }

    protected static LootTable.Builder createShearsOnlyDrop(IItemProvider p_218486_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(HAS_SHEARS).add(ItemLootEntry.lootTableItem(p_218486_0_)));
    }

    protected static LootTable.Builder createLeavesDrops(Block p_218540_0_, Block p_218540_1_, float... p_218540_2_) {
        return createSilkTouchOrShearsDispatchTable(p_218540_0_, applyExplosionCondition(p_218540_0_, ItemLootEntry.lootTableItem(p_218540_1_)).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, p_218540_2_))).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionDecay(p_218540_0_, ItemLootEntry.lootTableItem(Items.STICK).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)))).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }

    protected static LootTable.Builder createOakLeavesDrops(Block p_218526_0_, Block p_218526_1_, float... p_218526_2_) {
        return createLeavesDrops(p_218526_0_, p_218526_1_, p_218526_2_).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionCondition(p_218526_0_, ItemLootEntry.lootTableItem(Items.APPLE)).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }

    protected static LootTable.Builder createCropDrops(Block p_218541_0_, Item p_218541_1_, Item p_218541_2_, ILootCondition.IBuilder p_218541_3_) {
        return applyExplosionDecay(p_218541_0_, LootTable.lootTable().withPool(LootPool.lootPool().add(ItemLootEntry.lootTableItem(p_218541_1_).when(p_218541_3_).otherwise(ItemLootEntry.lootTableItem(p_218541_2_)))).withPool(LootPool.lootPool().when(p_218541_3_).add(ItemLootEntry.lootTableItem(p_218541_2_).apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
    }

    private static LootTable.Builder createDoublePlantShearsDrop(Block p_241750_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SHEARS).add(ItemLootEntry.lootTableItem(p_241750_0_).apply(SetCount.setCount(ConstantRange.exactly(2)))));
    }

    private static LootTable.Builder createDoublePlantWithSeedDrops(Block p_241749_0_, Block p_241749_1_) {
        LootEntry.Builder<?> builder = ItemLootEntry.lootTableItem(p_241749_1_).apply(SetCount.setCount(ConstantRange.exactly(2))).when(HAS_SHEARS).otherwise(applyExplosionCondition(p_241749_0_, ItemLootEntry.lootTableItem(Items.WHEAT_SEEDS)).when(RandomChance.randomChance(0.125F)));
        return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(BlockStateProperty.hasBlockStateProperties(p_241749_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(p_241749_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(BlockStateProperty.hasBlockStateProperties(p_241749_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(p_241749_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    }
    public static LootTable.Builder createDoorTable(Block p_239829_0_) {
        return createSinglePropConditionTable(p_239829_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
    }
    public static LootTable.Builder noDrop() {
        return LootTable.lootTable();
    }

    protected void registerLootTable(Block blockIn, LootTable.Builder table)
    {
        addTable(blockIn.getLootTable(), table);
    }

    protected void addTable(ResourceLocation path, LootTable.Builder lootTable)
    {
        tables.add(Pair.of(() -> (lootBuilder) -> lootBuilder.accept(path, lootTable), LootParameterSets.BLOCK));
    }
}