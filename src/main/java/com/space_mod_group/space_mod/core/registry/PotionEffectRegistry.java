package com.space_mod_group.space_mod.core.registry;

import com.space_mod_group.space_mod.SpaceMod;
import net.minecraft.entity.EntityType;
import net.minecraft.potion.Effect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionEffectRegistry
{
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, SpaceMod.MOD_ID);

}
