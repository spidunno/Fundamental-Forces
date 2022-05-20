package com.sammy.fufo.core.index.content.block;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.block.*;
import com.sammy.fufo.common.blockentity.*;
import com.sammy.ortus.systems.block.OrtusBlockProperties;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
public class BlockRegistrate {

    public static <T extends SlabBlock> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> slab(RegistryEntry<? extends Block> parent) {
        return (ctx, p) -> p.slabBlock(ctx.getEntry(), p.blockTexture(parent.get()), p.blockTexture(parent.get()));
    }
    public static <T extends StairBlock> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> stairs(RegistryEntry<? extends Block> parent) {
        return (ctx, p) -> p.stairsBlock(ctx.getEntry(), p.blockTexture(parent.get()));
    }

    public static final BlockEntry<Block> BLOCK_OF_CRACK =
            FufoMod.registrate().block("block_of_crack", Block::new)
                    .properties((properties) -> new OrtusBlockProperties(Material.METAL, MaterialColor.FIRE).needsPickaxe().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).isRedstoneConductor(Blocks::never))
                    .simpleItem()
                    .register();

    public static final BlockEntry<OrbBlock<OrbBlockEntity>> FORCE_ORB =
            FufoMod.registrate().block("force_orb", OrbBlock::new)
                    .simpleItem()
                    .properties((properties) -> new OrtusBlockProperties(Material.WOOL, MaterialColor.COLOR_BLUE).sound(SoundType.WOOL).noCollission().instabreak().lightLevel((b) -> 14))
                    .register();

    public static final BlockEntry<PipeAnchorBlock<AnchorBlockEntity>> ANCHOR =
            FufoMod.registrate().block("anchor", PipeAnchorBlock::new)
                    .properties((properties) ->  new OrtusBlockProperties(Material.METAL, MaterialColor.METAL).sound(SoundType.METAL).dynamicShape().instabreak().isCutoutLayer())
                    .simpleItem()
                    .register();

    public static final BlockEntry<BurnerExtractorBlock<BurnerExtractorBlockEntity>> BURNER_EXTRACTOR =
            FufoMod.registrate().block("burner_extractor", BurnerExtractorBlock::new)
                    .properties((properties) -> new OrtusBlockProperties(Material.METAL, MaterialColor.METAL).sound(SoundType.METAL).dynamicShape().isCutoutLayer())
                    .simpleItem()
                    .register();
    public static final BlockEntry<UITestBlock<UITestBlockEntity>> UI_TEST_BLOCK =
            FufoMod.registrate().block("ui_test_block",UITestBlock::new)
                    .properties((properties -> new OrtusBlockProperties(Material.STONE,MaterialColor.COLOR_GRAY).sound(SoundType.STONE)))
                    .simpleItem()
                    .register();
    public static final BlockEntry<MeteorFlameBlock<MeteorFlameBlockEntity>> METEOR_FIRE =
            FufoMod.registrate().block("meteor_fire", MeteorFlameBlock::new)
                    .simpleItem()
                    .properties((properties) -> new OrtusBlockProperties(Material.FIRE, MaterialColor.FIRE).isCutoutLayer().noDrops().sound(SoundType.WOOL).noCollission().instabreak().lightLevel(b-> 15))
                    .register();

    public static final BlockEntry<FlammableMeteoriteBlock> ORTUSITE =
            FufoMod.registrate().block("ortusite", (prop) -> new FlammableMeteoriteBlock(prop, (s, p) ->  METEOR_FIRE.get().defaultBlockState()))
                    .properties((properties) -> new OrtusBlockProperties(Material.STONE, MaterialColor.STONE).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.25F, 9.0F))
                    .simpleItem()
                    .register();

    public static final BlockEntry<Block> DEPLETED_ORTUSITE =
            FufoMod.registrate().block("depleted_ortusite", Block::new)
                    .properties((properties -> new OrtusBlockProperties(Material.STONE, MaterialColor.STONE).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.25F, 9.0F)))
                    .simpleItem()
                    .register();

    private static final NonNullUnaryOperator<BlockBehaviour.Properties> CHARRED_ROCK_PROPERTIES = (properties) -> new OrtusBlockProperties(Material.STONE, MaterialColor.STONE).needsPickaxe().sound(SoundType.BASALT).requiresCorrectToolForDrops().strength(1.5F, 9.0F);

    public static final BlockEntry<Block> CHARRED_ROCK =
            FufoMod.registrate().block("charred_rock", Block::new)
                    .properties(CHARRED_ROCK_PROPERTIES)
                    .simpleItem()
                    .register();

    public static final BlockEntry<Block> POLISHED_CHARRED_ROCK =
            FufoMod.registrate().block("polished_charred_rock", Block::new)
                    .properties(CHARRED_ROCK_PROPERTIES)
                    .simpleItem()
                    .register();

    public static final BlockEntry<SlabBlock> CHARRED_ROCK_SLAB =
            FufoMod.registrate().block("charred_rock_slab", SlabBlock::new)
                    .properties(CHARRED_ROCK_PROPERTIES)
                    .blockstate(slab(CHARRED_ROCK))
                    .simpleItem()
                    .register();

    public static final BlockEntry<SlabBlock> POLISHED_CHARRED_ROCK_SLAB =
            FufoMod.registrate().block("polished_charred_rock_slab", SlabBlock::new)
                    .properties(CHARRED_ROCK_PROPERTIES)
                    .blockstate(slab(POLISHED_CHARRED_ROCK))
                    .simpleItem()
                    .register();

    public static final BlockEntry<StairBlock> CHARRED_ROCK_STAIRS =
            FufoMod.registrate().block("charred_rock_stairs", o -> new StairBlock(() -> CHARRED_ROCK.get().defaultBlockState(), o))
                    .properties(CHARRED_ROCK_PROPERTIES)
                    .blockstate(stairs(CHARRED_ROCK))
                    .simpleItem()
                    .register();

    public static final BlockEntry<StairBlock> POLISHED_CHARRED_ROCK_STAIRS =
            FufoMod.registrate().block("polished_charred_rock_stairs", o -> new StairBlock(() -> CHARRED_ROCK.get().defaultBlockState(), o))
                    .properties(CHARRED_ROCK_PROPERTIES)
                    .blockstate(stairs(CHARRED_ROCK))
                    .simpleItem()
                    .register();

    public static OrtusBlockProperties VOLCANIC_GLASS_PROPERTIES() {
        return new OrtusBlockProperties(Material.GLASS).sound(SoundType.GLASS).noOcclusion().strength(0.4f);
    }

    public static final BlockEntry<GlassBlock> VOLCANIC_GLASS =
            FufoMod.registrate().block("volcanic_glass", GlassBlock::new)
                    .properties((x) -> VOLCANIC_GLASS_PROPERTIES())
                    .simpleItem()
                    .register();

    public static final BlockEntry<ScorchedEarthBlock> SCORCHED_EARTH =
            FufoMod.registrate().block("scorched_earth", ScorchedEarthBlock::new)
                    .properties((x) -> VOLCANIC_GLASS_PROPERTIES())
                    .simpleItem()
                    .register();


    /** here so java doesnt ignore the class */
    public static void register() {}


}
