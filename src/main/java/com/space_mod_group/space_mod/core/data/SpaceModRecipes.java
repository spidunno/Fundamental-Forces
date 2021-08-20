package com.space_mod_group.space_mod.core.data;

import net.minecraft.advancements.criterion.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static net.minecraft.data.CookingRecipeBuilder.blasting;
import static net.minecraft.data.CookingRecipeBuilder.smelting;
import static net.minecraft.data.ShapedRecipeBuilder.shaped;
import static net.minecraft.data.ShapelessRecipeBuilder.shapeless;

public class SpaceModRecipes extends RecipeProvider
{
    public SpaceModRecipes(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    public String getName()
    {
        return "Malum Recipe Provider";
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer)
    {

    }

    private static void netheriteSmithing(Consumer<IFinishedRecipe> p_240469_0_, Item p_240469_1_, Item p_240469_2_) {
        SmithingRecipeBuilder.smithing(Ingredient.of(p_240469_1_), Ingredient.of(Items.NETHERITE_INGOT), p_240469_2_).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(p_240469_0_, Registry.ITEM.getKey(p_240469_2_.asItem()).getPath() + "_smithing");
    }

    private static void planksFromLog(Consumer<IFinishedRecipe> p_240470_0_, IItemProvider p_240470_1_, ITag<Item> p_240470_2_) {
        ShapelessRecipeBuilder.shapeless(p_240470_1_, 4).requires(p_240470_2_).group("planks").unlockedBy("has_log", has(p_240470_2_)).save(p_240470_0_);
    }

    private static void planksFromLogs(Consumer<IFinishedRecipe> p_240472_0_, IItemProvider p_240472_1_, ITag<Item> p_240472_2_) {
        ShapelessRecipeBuilder.shapeless(p_240472_1_, 4).requires(p_240472_2_).group("planks").unlockedBy("has_logs", has(p_240472_2_)).save(p_240472_0_);
    }

    private static void woodFromLogs(Consumer<IFinishedRecipe> p_240471_0_, IItemProvider p_240471_1_, IItemProvider p_240471_2_) {
        ShapedRecipeBuilder.shaped(p_240471_1_, 3).define('#', p_240471_2_).pattern("##").pattern("##").group("bark").unlockedBy("has_log", has(p_240471_2_)).save(p_240471_0_);
    }

    private static void woodenBoat(Consumer<IFinishedRecipe> p_240473_0_, IItemProvider p_240473_1_, IItemProvider p_240473_2_) {
        ShapedRecipeBuilder.shaped(p_240473_1_).define('#', p_240473_2_).pattern("# #").pattern("###").group("boat").unlockedBy("in_water", insideOf(Blocks.WATER)).save(p_240473_0_);
    }

    private static void woodenButton(Consumer<IFinishedRecipe> p_240474_0_, IItemProvider p_240474_1_, IItemProvider p_240474_2_) {
        ShapelessRecipeBuilder.shapeless(p_240474_1_).requires(p_240474_2_).group("wooden_button").unlockedBy("has_planks", has(p_240474_2_)).save(p_240474_0_);
    }

    private static void woodenDoor(Consumer<IFinishedRecipe> p_240475_0_, IItemProvider p_240475_1_, IItemProvider p_240475_2_) {
        ShapedRecipeBuilder.shaped(p_240475_1_, 3).define('#', p_240475_2_).pattern("##").pattern("##").pattern("##").group("wooden_door").unlockedBy("has_planks", has(p_240475_2_)).save(p_240475_0_);
    }

    private static void woodenFence(Consumer<IFinishedRecipe> p_240476_0_, IItemProvider p_240476_1_, IItemProvider p_240476_2_) {
        ShapedRecipeBuilder.shaped(p_240476_1_, 3).define('#', Items.STICK).define('W', p_240476_2_).pattern("W#W").pattern("W#W").group("wooden_fence").unlockedBy("has_planks", has(p_240476_2_)).save(p_240476_0_);
    }

    private static void woodenFenceGate(Consumer<IFinishedRecipe> p_240477_0_, IItemProvider p_240477_1_, IItemProvider p_240477_2_) {
        ShapedRecipeBuilder.shaped(p_240477_1_).define('#', Items.STICK).define('W', p_240477_2_).pattern("#W#").pattern("#W#").group("wooden_fence_gate").unlockedBy("has_planks", has(p_240477_2_)).save(p_240477_0_);
    }

    private static void woodenPressurePlate(Consumer<IFinishedRecipe> p_240478_0_, IItemProvider p_240478_1_, IItemProvider p_240478_2_) {
        ShapedRecipeBuilder.shaped(p_240478_1_).define('#', p_240478_2_).pattern("##").group("wooden_pressure_plate").unlockedBy("has_planks", has(p_240478_2_)).save(p_240478_0_);
    }

    private static void woodenSlab(Consumer<IFinishedRecipe> p_240479_0_, IItemProvider p_240479_1_, IItemProvider p_240479_2_) {
        ShapedRecipeBuilder.shaped(p_240479_1_, 6).define('#', p_240479_2_).pattern("###").group("wooden_slab").unlockedBy("has_planks", has(p_240479_2_)).save(p_240479_0_);
    }

    private static void woodenStairs(Consumer<IFinishedRecipe> p_240480_0_, IItemProvider p_240480_1_, IItemProvider p_240480_2_) {
        ShapedRecipeBuilder.shaped(p_240480_1_, 4).define('#', p_240480_2_).pattern("#  ").pattern("## ").pattern("###").group("wooden_stairs").unlockedBy("has_planks", has(p_240480_2_)).save(p_240480_0_);
    }

    private static void woodenTrapdoor(Consumer<IFinishedRecipe> p_240481_0_, IItemProvider p_240481_1_, IItemProvider p_240481_2_) {
        ShapedRecipeBuilder.shaped(p_240481_1_, 2).define('#', p_240481_2_).pattern("###").pattern("###").group("wooden_trapdoor").unlockedBy("has_planks", has(p_240481_2_)).save(p_240481_0_);
    }

    private static void woodenSign(Consumer<IFinishedRecipe> p_240482_0_, IItemProvider p_240482_1_, IItemProvider p_240482_2_) {
        String s = Registry.ITEM.getKey(p_240482_2_.asItem()).getPath();
        ShapedRecipeBuilder.shaped(p_240482_1_, 3).group("sign").define('#', p_240482_2_).define('X', Items.STICK).pattern("###").pattern("###").pattern(" X ").unlockedBy("has_" + s, has(p_240482_2_)).save(p_240482_0_);
    }

    private static void coloredWoolFromWhiteWoolAndDye(Consumer<IFinishedRecipe> p_240483_0_, IItemProvider p_240483_1_, IItemProvider p_240483_2_) {
        ShapelessRecipeBuilder.shapeless(p_240483_1_).requires(p_240483_2_).requires(Blocks.WHITE_WOOL).group("wool").unlockedBy("has_white_wool", has(Blocks.WHITE_WOOL)).save(p_240483_0_);
    }

    private static void carpetFromWool(Consumer<IFinishedRecipe> p_240484_0_, IItemProvider p_240484_1_, IItemProvider p_240484_2_) {
        String s = Registry.ITEM.getKey(p_240484_2_.asItem()).getPath();
        ShapedRecipeBuilder.shaped(p_240484_1_, 3).define('#', p_240484_2_).pattern("##").group("carpet").unlockedBy("has_" + s, has(p_240484_2_)).save(p_240484_0_);
    }

    private static void coloredCarpetFromWhiteCarpetAndDye(Consumer<IFinishedRecipe> p_240485_0_, IItemProvider p_240485_1_, IItemProvider p_240485_2_) {
        String s = Registry.ITEM.getKey(p_240485_1_.asItem()).getPath();
        String s1 = Registry.ITEM.getKey(p_240485_2_.asItem()).getPath();
        ShapedRecipeBuilder.shaped(p_240485_1_, 8).define('#', Blocks.WHITE_CARPET).define('$', p_240485_2_).pattern("###").pattern("#$#").pattern("###").group("carpet").unlockedBy("has_white_carpet", has(Blocks.WHITE_CARPET)).unlockedBy("has_" + s1, has(p_240485_2_)).save(p_240485_0_, s + "_from_white_carpet");
    }

    private static void bedFromPlanksAndWool(Consumer<IFinishedRecipe> p_240486_0_, IItemProvider p_240486_1_, IItemProvider p_240486_2_) {
        String s = Registry.ITEM.getKey(p_240486_2_.asItem()).getPath();
        ShapedRecipeBuilder.shaped(p_240486_1_).define('#', p_240486_2_).define('X', ItemTags.PLANKS).pattern("###").pattern("XXX").group("bed").unlockedBy("has_" + s, has(p_240486_2_)).save(p_240486_0_);
    }

    private static void bedFromWhiteBedAndDye(Consumer<IFinishedRecipe> p_240487_0_, IItemProvider p_240487_1_, IItemProvider p_240487_2_) {
        String s = Registry.ITEM.getKey(p_240487_1_.asItem()).getPath();
        ShapelessRecipeBuilder.shapeless(p_240487_1_).requires(Items.WHITE_BED).requires(p_240487_2_).group("dyed_bed").unlockedBy("has_bed", has(Items.WHITE_BED)).save(p_240487_0_, s + "_from_white_bed");
    }

    private static void banner(Consumer<IFinishedRecipe> p_240488_0_, IItemProvider p_240488_1_, IItemProvider p_240488_2_) {
        String s = Registry.ITEM.getKey(p_240488_2_.asItem()).getPath();
        ShapedRecipeBuilder.shaped(p_240488_1_).define('#', p_240488_2_).define('|', Items.STICK).pattern("###").pattern("###").pattern(" | ").group("banner").unlockedBy("has_" + s, has(p_240488_2_)).save(p_240488_0_);
    }

    private static void stainedGlassFromGlassAndDye(Consumer<IFinishedRecipe> p_240489_0_, IItemProvider p_240489_1_, IItemProvider p_240489_2_) {
        ShapedRecipeBuilder.shaped(p_240489_1_, 8).define('#', Blocks.GLASS).define('X', p_240489_2_).pattern("###").pattern("#X#").pattern("###").group("stained_glass").unlockedBy("has_glass", has(Blocks.GLASS)).save(p_240489_0_);
    }

    private static void stainedGlassPaneFromStainedGlass(Consumer<IFinishedRecipe> p_240490_0_, IItemProvider p_240490_1_, IItemProvider p_240490_2_) {
        ShapedRecipeBuilder.shaped(p_240490_1_, 16).define('#', p_240490_2_).pattern("###").pattern("###").group("stained_glass_pane").unlockedBy("has_glass", has(p_240490_2_)).save(p_240490_0_);
    }

    private static void stainedGlassPaneFromGlassPaneAndDye(Consumer<IFinishedRecipe> p_240491_0_, IItemProvider p_240491_1_, IItemProvider p_240491_2_) {
        String s = Registry.ITEM.getKey(p_240491_1_.asItem()).getPath();
        String s1 = Registry.ITEM.getKey(p_240491_2_.asItem()).getPath();
        ShapedRecipeBuilder.shaped(p_240491_1_, 8).define('#', Blocks.GLASS_PANE).define('$', p_240491_2_).pattern("###").pattern("#$#").pattern("###").group("stained_glass_pane").unlockedBy("has_glass_pane", has(Blocks.GLASS_PANE)).unlockedBy("has_" + s1, has(p_240491_2_)).save(p_240491_0_, s + "_from_glass_pane");
    }

    private static void coloredTerracottaFromTerracottaAndDye(Consumer<IFinishedRecipe> p_240492_0_, IItemProvider p_240492_1_, IItemProvider p_240492_2_) {
        ShapedRecipeBuilder.shaped(p_240492_1_, 8).define('#', Blocks.TERRACOTTA).define('X', p_240492_2_).pattern("###").pattern("#X#").pattern("###").group("stained_terracotta").unlockedBy("has_terracotta", has(Blocks.TERRACOTTA)).save(p_240492_0_);
    }

    private static void concretePowder(Consumer<IFinishedRecipe> p_240493_0_, IItemProvider p_240493_1_, IItemProvider p_240493_2_) {
        ShapelessRecipeBuilder.shapeless(p_240493_1_, 8).requires(p_240493_2_).requires(Blocks.SAND, 4).requires(Blocks.GRAVEL, 4).group("concrete_powder").unlockedBy("has_sand", has(Blocks.SAND)).unlockedBy("has_gravel", has(Blocks.GRAVEL)).save(p_240493_0_);
    }

    private static void cookRecipes(Consumer<IFinishedRecipe> p_218445_0_, String p_218445_1_, CookingRecipeSerializer<?> p_218445_2_, int p_218445_3_) {
        CookingRecipeBuilder.cooking(Ingredient.of(Items.BEEF), Items.COOKED_BEEF, 0.35F, p_218445_3_, p_218445_2_).unlockedBy("has_beef", has(Items.BEEF)).save(p_218445_0_, "cooked_beef_from_" + p_218445_1_);
        CookingRecipeBuilder.cooking(Ingredient.of(Items.CHICKEN), Items.COOKED_CHICKEN, 0.35F, p_218445_3_, p_218445_2_).unlockedBy("has_chicken", has(Items.CHICKEN)).save(p_218445_0_, "cooked_chicken_from_" + p_218445_1_);
        CookingRecipeBuilder.cooking(Ingredient.of(Items.COD), Items.COOKED_COD, 0.35F, p_218445_3_, p_218445_2_).unlockedBy("has_cod", has(Items.COD)).save(p_218445_0_, "cooked_cod_from_" + p_218445_1_);
        CookingRecipeBuilder.cooking(Ingredient.of(Blocks.KELP), Items.DRIED_KELP, 0.1F, p_218445_3_, p_218445_2_).unlockedBy("has_kelp", has(Blocks.KELP)).save(p_218445_0_, "dried_kelp_from_" + p_218445_1_);
        CookingRecipeBuilder.cooking(Ingredient.of(Items.SALMON), Items.COOKED_SALMON, 0.35F, p_218445_3_, p_218445_2_).unlockedBy("has_salmon", has(Items.SALMON)).save(p_218445_0_, "cooked_salmon_from_" + p_218445_1_);
        CookingRecipeBuilder.cooking(Ingredient.of(Items.MUTTON), Items.COOKED_MUTTON, 0.35F, p_218445_3_, p_218445_2_).unlockedBy("has_mutton", has(Items.MUTTON)).save(p_218445_0_, "cooked_mutton_from_" + p_218445_1_);
        CookingRecipeBuilder.cooking(Ingredient.of(Items.PORKCHOP), Items.COOKED_PORKCHOP, 0.35F, p_218445_3_, p_218445_2_).unlockedBy("has_porkchop", has(Items.PORKCHOP)).save(p_218445_0_, "cooked_porkchop_from_" + p_218445_1_);
        CookingRecipeBuilder.cooking(Ingredient.of(Items.POTATO), Items.BAKED_POTATO, 0.35F, p_218445_3_, p_218445_2_).unlockedBy("has_potato", has(Items.POTATO)).save(p_218445_0_, "baked_potato_from_" + p_218445_1_);
        CookingRecipeBuilder.cooking(Ingredient.of(Items.RABBIT), Items.COOKED_RABBIT, 0.35F, p_218445_3_, p_218445_2_).unlockedBy("has_rabbit", has(Items.RABBIT)).save(p_218445_0_, "cooked_rabbit_from_" + p_218445_1_);
    }

    protected static EnterBlockTrigger.Instance insideOf(Block p_200407_0_) {
        return new EnterBlockTrigger.Instance(EntityPredicate.AndPredicate.ANY, p_200407_0_, StatePropertiesPredicate.ANY);
    }

    protected static InventoryChangeTrigger.Instance has(IItemProvider p_200403_0_) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(p_200403_0_).build());
    }

    protected static InventoryChangeTrigger.Instance has(ITag<Item> p_200409_0_) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(p_200409_0_).build());
    }

    protected static InventoryChangeTrigger.Instance inventoryTrigger(ItemPredicate... p_200405_0_) {
        return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, p_200405_0_);
    }
}