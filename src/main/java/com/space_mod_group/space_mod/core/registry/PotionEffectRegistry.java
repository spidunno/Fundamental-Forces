package com.space_mod_group.space_mod.core.registry;

import com.space_mod_group.space_mod.SpaceMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionEffectRegistry
{
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SpaceMod.MOD_ID);

}
