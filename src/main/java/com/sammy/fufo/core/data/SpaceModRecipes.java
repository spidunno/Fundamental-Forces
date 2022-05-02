package com.sammy.fufo.core.data;


import com.sammy.fufo.core.data.builder.ImpactConversionRecipeBuilder;
import com.sammy.fufo.core.data.builder.ManaAbsorbtionRecipeBuilder;
import com.sammy.fufo.core.setup.content.block.BlockRegistry;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class SpaceModRecipes extends RecipeProvider {
    public SpaceModRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Space Mod Recipe Provider";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        new ImpactConversionRecipeBuilder(Ingredient.of(Tags.Items.COBBLESTONE), Ingredient.of(BlockRegistry.CHARRED_ROCK.get())).build(consumer, "cobblestone_charred_rock");
        new ImpactConversionRecipeBuilder(Ingredient.of(Tags.Items.COBBLESTONE), Ingredient.of(Blocks.GRAVEL)).build(consumer, "cobblestone_gravel");
        new ImpactConversionRecipeBuilder(Ingredient.of(Tags.Items.STONE), Ingredient.of(BlockRegistry.CHARRED_ROCK.get())).build(consumer, "stone_charred_rock");
        new ImpactConversionRecipeBuilder(Ingredient.of(Tags.Items.STONE), Ingredient.of(Blocks.GRAVEL)).build(consumer, "stone_gravel");
        new ImpactConversionRecipeBuilder(Ingredient.of(Tags.Items.STONE), Ingredient.of(Blocks.COBBLESTONE)).build(consumer, "stone_cobblestone");
        new ImpactConversionRecipeBuilder(Ingredient.of(Tags.Items.SAND), Ingredient.of(BlockRegistry.VOLCANIC_GLASS.get())).build(consumer, "sand_volcanic_glass");
        new ImpactConversionRecipeBuilder(Ingredient.of(Tags.Items.NETHERRACK), Ingredient.of(Blocks.MAGMA_BLOCK)).build(consumer, "netherrack_magma_block");

        new ManaAbsorbtionRecipeBuilder(Ingredient.of(Tags.Items.COBBLESTONE), Ingredient.of(BlockRegistry.CHARRED_ROCK.get())).build(consumer, "cobblestone_charred_rock");
        new ManaAbsorbtionRecipeBuilder(Ingredient.of(Tags.Items.GLASS), Ingredient.of(BlockRegistry.VOLCANIC_GLASS.get())).build(consumer, "glass_volcanic_glass");
        new ManaAbsorbtionRecipeBuilder(Ingredient.of(Tags.Items.SAND), Ingredient.of(BlockRegistry.VOLCANIC_GLASS.get())).build(consumer, "sand_volcanic_glass");
        new ManaAbsorbtionRecipeBuilder(Ingredient.of(Items.GRASS_BLOCK), Ingredient.of(BlockRegistry.SCORCHED_EARTH.get())).build(consumer, "grass_scorched_earth");
    }

    protected static EnterBlockTrigger.TriggerInstance insideOf(Block pBlock) {
        return new EnterBlockTrigger.TriggerInstance(EntityPredicate.Composite.ANY, pBlock, StatePropertiesPredicate.ANY);
    }

    protected static InventoryChangeTrigger.TriggerInstance has(MinMaxBounds.Ints pCount, ItemLike pItem) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItem).withCount(pCount).build());
    }

    protected static InventoryChangeTrigger.TriggerInstance has(ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItemLike).build());
    }

    protected static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> pTag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pTag).build());
    }

    protected static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... pPredicates) {
        return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, pPredicates);
    }
}