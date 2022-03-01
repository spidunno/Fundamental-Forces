package com.sammy.fundamental_forces.core.setup.content.potion;

import com.sammy.fundamental_forces.FundamentalForcesMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionEffectRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FundamentalForcesMod.MODID);

}
