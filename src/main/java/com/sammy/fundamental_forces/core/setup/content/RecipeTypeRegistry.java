package com.sammy.fundamental_forces.core.setup.content;

import com.sammy.fundamental_forces.FundamentalForcesMod;
import com.sammy.fundamental_forces.common.recipe.ImpactConversionRecipe;
import com.sammy.fundamental_forces.common.recipe.ManaAbsorptionRecipe;
import com.sammy.fundamental_forces.common.recipe.NBTCarryRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class RecipeTypeRegistry {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FundamentalForcesMod.MODID);

    public static final RegistryObject<RecipeSerializer<NBTCarryRecipe>> NBT_CARRY = RECIPE_TYPES.register(NBTCarryRecipe.NAME, NBTCarryRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<ManaAbsorptionRecipe>> MANA_ABSORPTION = RECIPE_TYPES.register(ManaAbsorptionRecipe.NAME, ManaAbsorptionRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<ImpactConversionRecipe>> IMPACT_CONVERSION = RECIPE_TYPES.register(ImpactConversionRecipe.NAME, ImpactConversionRecipe.Serializer::new);
}
