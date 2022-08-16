package team.lodestar.fufo.data.builder;

import com.google.gson.JsonObject;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.recipe.WeaveRecipe;
import team.lodestar.fufo.registry.common.FufoRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class WeaveRecipeBuilder {

    private final WeaveRecipe recipe;

    public WeaveRecipeBuilder(WeaveRecipe recipe) {
        this.recipe = recipe;
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName) {
        build(consumerIn, FufoMod.fufoPath("weave/" + recipeName));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new Result(id, recipe));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;

        private final WeaveRecipe recipe;

        public Result(ResourceLocation id, WeaveRecipe recipe) {
            this.id = id;
            this.recipe = recipe;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            ((WeaveRecipe.Serializer) FufoRecipeTypes.WEAVE.get()).toJson(json, recipe);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return FufoRecipeTypes.WEAVE.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}