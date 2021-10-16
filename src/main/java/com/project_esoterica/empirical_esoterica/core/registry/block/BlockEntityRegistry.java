package com.project_esoterica.empirical_esoterica.core.registry.block;

import com.project_esoterica.empirical_esoterica.EmpiricalEsoterica;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockEntityRegistry
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, EmpiricalEsoterica.MOD_ID);

}
