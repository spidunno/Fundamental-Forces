package team.lodestar.fufo.data;


import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Vec3i;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.recipe.WeaveRecipe;
import team.lodestar.fufo.core.weaving.BindingType;
import team.lodestar.fufo.core.weaving.recipe.EntityTypeBindable;
import team.lodestar.fufo.core.weaving.recipe.IngredientBindable;
import team.lodestar.fufo.core.weaving.recipe.ItemStackBindable;
import team.lodestar.fufo.data.builder.WeaveRecipeBuilder;

import java.util.function.Consumer;

public class FufoWeaveCraftingRecipes extends RecipeProvider {
    public FufoWeaveCraftingRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Space Mod Recipe Provider";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        new WeaveRecipeBuilder(
            new WeaveRecipe(new IngredientBindable(Ingredient.of(Items.DIAMOND_SWORD)), FufoMod.fufoPath("glue"), "standard")
                .link(
                    new Vec3i(0, 0, 0),
                    new Vec3i(0, 1, 0),
                    new BindingType(FufoMod.fufoPath("ultimate_bingus")),
                    new EntityTypeBindable(new Vec3i(2, 2, 2), EntityType.HORSE)
                )
                .link(
                    new Vec3i(0, 2, 0),
                    new Vec3i(0, 3, 0),
                    new BindingType(FufoMod.fufoPath("ultimate_floppe")),
                    new IngredientBindable(new Vec3i(2, 2, 2), Ingredient.of(Tags.Items.SHEARS))
                )
                .primersAllowed(ForgeRegistries.ITEMS.getKey(Items.DIAMOND_SWORD), 1)
                .primersAllowed(ForgeRegistries.ITEMS.getKey(Items.SHEARS), 1)
                .primersAllowed(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.HORSE), 1)
                .setOutput(new ItemStack(Items.SLIME_BALL))
        ).build(consumer, "glue");

        new WeaveRecipeBuilder(
            new WeaveRecipe(new IngredientBindable(Ingredient.of(Items.DIAMOND_SWORD)), FufoMod.fufoPath("glue_two_electric_boogaloo"), "standard")
                .link(
                    new Vec3i(0, 0, 0),
                    new Vec3i(0, 1, 0),
                    new BindingType(FufoMod.fufoPath("ultimate_bingus")),
                    new ItemStackBindable(new Vec3i(1, 1, 1), Items.HORSE_SPAWN_EGG.getDefaultInstance())
                )
                .link(
                    new Vec3i(0, 2, 0),
                    new Vec3i(0, 3, 0),
                    new BindingType(FufoMod.fufoPath("ultimate_floppe")),
                    new IngredientBindable(new Vec3i(1, 1, 1), Ingredient.of(Tags.Items.SHEARS))
                )
                .primersAllowed(ForgeRegistries.ITEMS.getKey(Items.DIAMOND_SWORD), 1)
                .primersAllowed(ForgeRegistries.ITEMS.getKey(Items.SHEARS), 1)
                .primersAllowed(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.HORSE), 1)
                .setOutput(new ItemStack(Items.SLIME_BALL))
        ).build(consumer, "glue_two_electric_boogaloo");
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