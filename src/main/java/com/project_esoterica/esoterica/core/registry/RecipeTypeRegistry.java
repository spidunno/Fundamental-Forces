package com.project_esoterica.esoterica.core.registry;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.common.recipe.ManaAbsorptionRecipe;
import com.project_esoterica.esoterica.common.recipe.NBTCarryRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class RecipeTypeRegistry {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, EsotericaMod.MODID);

    public static final RegistryObject<RecipeSerializer<NBTCarryRecipe>> NBT_CARRY = RECIPE_TYPES.register(NBTCarryRecipe.NAME, NBTCarryRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<ManaAbsorptionRecipe>> MANA_ABSORPTION = RECIPE_TYPES.register(ManaAbsorptionRecipe.NAME, ManaAbsorptionRecipe.Serializer::new);
}
