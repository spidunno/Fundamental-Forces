package com.sammy.fufo.core.setup.content.block;

import com.sammy.ortus.systems.block.OrtusBlockProperties;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BlockPropertiesRegistry {


    public static OrtusBlockProperties CRACK_PROPERTIES() {
        return new OrtusBlockProperties(Material.METAL, MaterialColor.FIRE).needsPickaxe().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).isRedstoneConductor(Blocks::never);
    }
    public static BlockBehaviour.Properties ASTEROID_ROCK_PROPERTIES() {
        return new OrtusBlockProperties(Material.STONE, MaterialColor.STONE).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.25F, 9.0F);
    }
    public static OrtusBlockProperties METEOR_FIRE_PROPERTIES() {
        return new OrtusBlockProperties(Material.FIRE, MaterialColor.FIRE).isCutoutLayer().noDrops().sound(SoundType.WOOL).noCollission().instabreak().lightLevel(b-> 15);
    }
    public static OrtusBlockProperties CHARRED_ROCK_PROPERTIES() {
        return new OrtusBlockProperties(Material.STONE, MaterialColor.STONE).needsPickaxe().sound(SoundType.BASALT).requiresCorrectToolForDrops().strength(1.5F, 9.0F);
    }
    public static OrtusBlockProperties VOLCANIC_GLASS_PROPERTIES() {
        return new OrtusBlockProperties(Material.GLASS).sound(SoundType.GLASS).noOcclusion().strength(0.4f);
    }
    public static OrtusBlockProperties SCORCHED_EARTH_PROPERTIES() {
        return new OrtusBlockProperties(Material.GRASS).sound(SoundType.GRASS).noOcclusion().strength(0.7f);
    }
    public static OrtusBlockProperties ORB_PROPERTIES() {
        return new OrtusBlockProperties(Material.WOOL, MaterialColor.COLOR_BLUE).sound(SoundType.WOOL).noCollission().instabreak().lightLevel((b) -> 14);
    }

    public static OrtusBlockProperties ANCHOR_PROPERTIES() {
        return new OrtusBlockProperties(Material.METAL, MaterialColor.METAL).sound(SoundType.METAL).dynamicShape().instabreak().isCutoutLayer();
    }
}
