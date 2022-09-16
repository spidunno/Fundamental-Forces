package team.lodestar.fufo.registry.common;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.starfall.impact.ImpactConversionRecipe;
import team.lodestar.fufo.common.recipe.WeaveRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class FufoRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FufoMod.FUFO);

    public static final RegistryObject<RecipeSerializer<ImpactConversionRecipe>> IMPACT_CONVERSION = RECIPE_TYPES.register(ImpactConversionRecipe.NAME, ImpactConversionRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<WeaveRecipe>> WEAVE = RECIPE_TYPES.register(WeaveRecipe.NAME, WeaveRecipe.Serializer::new);
}
