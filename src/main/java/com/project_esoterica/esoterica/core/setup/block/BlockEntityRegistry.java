package com.project_esoterica.esoterica.core.setup.block;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.common.blockentity.MeteorFlameBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, EsotericaMod.MODID);
    public static final RegistryObject<BlockEntityType<MeteorFlameBlockEntity>> METEOR_FLAME = BLOCK_ENTITY_TYPES.register("meteor_flame", () -> BlockEntityType.Builder.of(MeteorFlameBlockEntity::new, BlockRegistry.METEOR_FIRE.get()).build(null));

}
