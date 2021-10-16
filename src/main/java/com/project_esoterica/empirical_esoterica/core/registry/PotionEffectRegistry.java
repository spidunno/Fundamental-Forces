package com.project_esoterica.empirical_esoterica.core.registry;

import com.project_esoterica.empirical_esoterica.EmpiricalEsoterica;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionEffectRegistry
{
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EmpiricalEsoterica.MOD_ID);

}
