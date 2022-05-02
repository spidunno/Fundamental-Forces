package com.sammy.fundamental_forces.core.data.builder;

import com.google.gson.JsonObject;
import com.sammy.fundamental_forces.FufoMod;
import com.sammy.fundamental_forces.core.setup.content.RecipeTypeRegistry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ManaAbsorbtionRecipeBuilder
{
    private final Ingredient input;
    private final Ingredient output;

    public ManaAbsorbtionRecipeBuilder(Ingredient input, Ingredient output)
    {
        this.input = input;
        this.output = output;
    }
    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName)
    {
        build(consumerIn, FufoMod.prefix("mana_absorption/" + recipeName));
    }
    public void build(Consumer<FinishedRecipe> consumerIn)
    {
        build(consumerIn, output.getItems()[0].getItem().getRegistryName().getPath());
    }
    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id)
    {
        consumerIn.accept(new Result(id, input, output));
    }

    public static class Result implements FinishedRecipe
    {
        private final ResourceLocation id;

        private final Ingredient input;
        private final Ingredient output;

        public Result(ResourceLocation id, Ingredient input, Ingredient output)
        {
            this.id = id;
            this.input = input;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("input", input.toJson().getAsJsonObject());
            json.add("output", output.toJson().getAsJsonObject());
        }

        @Override
        public ResourceLocation getId()
        {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType()
        {
            return RecipeTypeRegistry.MANA_ABSORPTION.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement()
        {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId()
        {
            return null;
        }
    }
}