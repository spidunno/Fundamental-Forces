package com.project_esoterica.esoterica.core.registry.block;

import com.project_esoterica.esoterica.EsotericaMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EsotericaMod.MOD_ID);

    public static BlockBehaviour.Properties ASTEROID_ROCK_PROPERTIES() {
        return BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.25F, 9.0F);
    }

    public static final RegistryObject<Block> ASTEROID_ROCK = BLOCKS.register("asteroid_rock", () -> new Block(ASTEROID_ROCK_PROPERTIES()));
}