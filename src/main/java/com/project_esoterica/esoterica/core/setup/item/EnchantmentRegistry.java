package com.project_esoterica.esoterica.core.setup.item;

import com.project_esoterica.esoterica.EsotericaMod;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EsotericaMod.MODID);

}