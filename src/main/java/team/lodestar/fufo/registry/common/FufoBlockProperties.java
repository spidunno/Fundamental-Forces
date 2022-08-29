package team.lodestar.fufo.registry.common;

import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class FufoBlockProperties {


    public static LodestoneBlockProperties CRACK_PROPERTIES() {
        return new LodestoneBlockProperties(Material.METAL, MaterialColor.FIRE).needsPickaxe().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).isRedstoneConductor(Blocks::never);
    }

    public static LodestoneBlockProperties ASTEROID_PROPERTIES() {
        return new LodestoneBlockProperties(Material.STONE, MaterialColor.STONE).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.25F, 9.0F);
    }

    public static LodestoneBlockProperties METEOR_FIRE_PROPERTIES() {
        return new LodestoneBlockProperties(Material.FIRE, MaterialColor.FIRE).isCutoutLayer().sound(SoundType.WOOL).noCollission().instabreak().lightLevel(b -> 15);
    }

    public static LodestoneBlockProperties CHARRED_ROCK_PROPERTIES() {
        return new LodestoneBlockProperties(Material.STONE, MaterialColor.STONE).needsPickaxe().sound(SoundType.BASALT).requiresCorrectToolForDrops().strength(1.5F, 9.0F);
    }

    public static LodestoneBlockProperties VOLCANIC_GLASS_PROPERTIES() {
        return new LodestoneBlockProperties(Material.GLASS).sound(SoundType.GLASS).noOcclusion().strength(0.4f);
    }

    public static LodestoneBlockProperties SCORCHED_EARTH_PROPERTIES() {
        return new LodestoneBlockProperties(Material.GRASS).sound(SoundType.GRASS).noOcclusion().strength(0.7f);
    }

    public static LodestoneBlockProperties ORB_PROPERTIES() {
        return new LodestoneBlockProperties(Material.WOOL, MaterialColor.COLOR_BLUE).sound(SoundType.WOOL).noCollission().instabreak().lightLevel((b) -> 14);
    }

    public static LodestoneBlockProperties CRUDE_COPPER_PROPERTIES() {
        return new LodestoneBlockProperties(Material.METAL).sound(SoundType.COPPER).dynamicShape().isCutoutLayer();
    }

    public static LodestoneBlockProperties CRUDE_IRON_PROPERTIES() {
        return new LodestoneBlockProperties(Material.METAL).sound(SoundType.METAL).dynamicShape().isCutoutLayer();
    }

    public static LodestoneBlockProperties CRUDE_PROPERTIES() {
        return new LodestoneBlockProperties(Material.METAL).sound(SoundType.LODESTONE).dynamicShape().isCutoutLayer();
    }

    public static LodestoneBlockProperties UI_TEST_BLOCK_PROPERTIES() {
        return new LodestoneBlockProperties(Material.STONE, MaterialColor.COLOR_GRAY).sound(SoundType.STONE);
    }
}