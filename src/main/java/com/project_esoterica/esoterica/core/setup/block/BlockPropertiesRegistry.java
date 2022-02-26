package com.project_esoterica.esoterica.core.setup.block;

import com.project_esoterica.esoterica.core.systems.block.SimpleBlockProperties;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import static com.project_esoterica.esoterica.core.systems.block.SimpleBlockProperties.StateType.PREDEFINED;

public class BlockPropertiesRegistry {


    public static SimpleBlockProperties CRACK_PROPERTIES() {
        return new SimpleBlockProperties(Material.METAL, MaterialColor.FIRE).needsPickaxe().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).isRedstoneConductor(Blocks::never);
    }
    public static BlockBehaviour.Properties ASTEROID_ROCK_PROPERTIES() {
        return new SimpleBlockProperties(Material.STONE, MaterialColor.STONE).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.25F, 9.0F);
    }
    public static SimpleBlockProperties METEOR_FIRE_PROPERTIES() {
        return new SimpleBlockProperties(Material.FIRE, MaterialColor.FIRE).blockStateDefinition(PREDEFINED).customLoot().sound(SoundType.WOOL).noCollission().instabreak().lightLevel(b-> 15);
    }
    public static SimpleBlockProperties CHARRED_ROCK_PROPERTIES() {
        return new SimpleBlockProperties(Material.STONE, MaterialColor.STONE).needsPickaxe().sound(SoundType.BASALT).requiresCorrectToolForDrops().strength(1.5F, 9.0F);
    }
    public static SimpleBlockProperties VOLCANIC_GLASS_PROPERTIES() {
        return new SimpleBlockProperties(Material.GLASS).sound(SoundType.GLASS).noOcclusion().strength(0.4f);
    }
    public static SimpleBlockProperties SCORCHED_EARTH_PROPERTIES() {
        return new SimpleBlockProperties(Material.GRASS).sound(SoundType.GRASS).noOcclusion().strength(0.7f);
    }
    public static SimpleBlockProperties ORB_PROPERTIES() {
        return new SimpleBlockProperties(Material.WOOL, MaterialColor.COLOR_BLUE).sound(SoundType.WOOL).noCollission().instabreak().lightLevel((b) -> 14);
    }
}
