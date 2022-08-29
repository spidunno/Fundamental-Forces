package team.lodestar.fufo.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.registry.common.FufoRecipeTypes;
import team.lodestar.lodestone.systems.recipe.ILodestoneRecipe;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ManaAbsorptionRecipe extends ILodestoneRecipe
{
    public static final String NAME = "mana_absorption";
    public static class Type implements RecipeType<ManaAbsorptionRecipe> {
        @Override
        public String toString () {
            return FufoMod.FUFO + ":" + NAME;
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
        return FufoRecipeTypes.MANA_ABSORPTION.get();
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

    public boolean doesInputMatch(ItemStack input)
    {
        return this.input.test(input);
    }
    public boolean doesOutputMatch(ItemStack output)
    {
        return this.output.test(output);
    }

    public static ArrayList<ManaAbsorptionRecipe> getRecipes(Level level, ItemStack input)
    {
        List<ManaAbsorptionRecipe> recipes = getRecipes(level);
        recipes.removeIf(r -> !r.doesInputMatch(input));
        return new ArrayList<>(recipes);
    }
    public static List<ManaAbsorptionRecipe> getRecipes(Level level)
    {
        return level.getRecipeManager().getAllRecipesFor(Type.INSTANCE);
    }

    public static class Serializer implements RecipeSerializer<ManaAbsorptionRecipe> {

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
