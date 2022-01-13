package com.project_esoterica.esoterica.common.recipe;

import com.google.gson.JsonObject;
import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.core.registry.RecipeTypeRegistry;
import com.project_esoterica.esoterica.core.systems.recipe.IEsotericaRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;

public class ManaAbsorptionRecipe extends IEsotericaRecipe
{
    public static final String NAME = "block_transmutation";
    public static class Type implements RecipeType<ManaAbsorptionRecipe> {
        @Override
        public String toString () {
            return EsotericaMod.MODID + ":" + NAME;
        }

        public static final Type INSTANCE = new Type();
    }

    private final ResourceLocation id;

    public final Ingredient input;
    public final Ingredient output;

    public ManaAbsorptionRecipe(ResourceLocation id, Ingredient input, Ingredient output)
    {
        this.id = id;
        this.input = input;
        this.output = output;
    }
    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return RecipeTypeRegistry.MANA_ABSORPTION.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return Type.INSTANCE;
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    public boolean doesInputMatch(Block input)
    {
        return input.equals(this.input);
    }
    public boolean doesOutputMatch(Block output)
    {
        return output.equals(this.output);
    }

    public static ManaAbsorptionRecipe getRecipe(Level level, Block block)
    {
        List<ManaAbsorptionRecipe> recipes = getRecipes(level);
        for (ManaAbsorptionRecipe recipe : recipes)
        {
            if (recipe.doesInputMatch(block))
            {
                return recipe;
            }
        }
        return null;
    }
    public static List<ManaAbsorptionRecipe> getRecipes(Level level)
    {
        return level.getRecipeManager().getAllRecipesFor(Type.INSTANCE);
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ManaAbsorptionRecipe> {

        @Override
        public ManaAbsorptionRecipe fromJson(ResourceLocation recipeId, JsonObject json)
        {
            return new ManaAbsorptionRecipe(recipeId, Ingredient.fromJson(json.getAsJsonObject("input")), Ingredient.fromJson(json.getAsJsonObject("output")));
        }

        @Nullable
        @Override
        public ManaAbsorptionRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
        {
            return new ManaAbsorptionRecipe(recipeId, Ingredient.fromNetwork(buffer), Ingredient.fromNetwork(buffer));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ManaAbsorptionRecipe recipe)
        {
            recipe.input.toNetwork(buffer);
            recipe.output.toNetwork(buffer);
        }
    }
}
