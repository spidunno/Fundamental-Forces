package com.project_esoterica.empirical_esoterica.core.registry;

import com.project_esoterica.empirical_esoterica.EmpiricalEsoterica;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistry
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, EmpiricalEsoterica.MOD_ID);

}
