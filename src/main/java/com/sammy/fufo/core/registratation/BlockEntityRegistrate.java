package com.sammy.fufo.core.registratation;
import com.sammy.fufo.FufoMod;
import com.sammy.fufo.client.renderers.block.ArrayRenderer;
import com.sammy.fufo.client.renderers.block.OrbRenderer;
import com.sammy.fufo.common.blockentity.*;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class BlockEntityRegistrate {

    public static final BlockEntityEntry<BurnerExtractorBlockEntity> BURNER_EXTRACTOR =
            FufoMod.registrate().<BurnerExtractorBlockEntity>blockEntity("burner_extractor", BurnerExtractorBlockEntity::new).register();

    public static final BlockEntityEntry<UITestBlockEntity> UI_TEST_BLOCK =
            FufoMod.registrate().<UITestBlockEntity>blockEntity("ui_test_block", UITestBlockEntity::new).register();

    public static final BlockEntityEntry<AnchorBlockEntity> ANCHOR =
            FufoMod.registrate().<AnchorBlockEntity>blockEntity("anchor", AnchorBlockEntity::new).register();

    public static final BlockEntityEntry<OrbBlockEntity> ORB =
            FufoMod.registrate().<OrbBlockEntity>blockEntity("orb", OrbBlockEntity::new).validBlocks(BlockRegistrate.FORCE_ORB).renderer(() -> OrbRenderer::new).register();

    public static final BlockEntityEntry<MeteorFlameBlockEntity> METEOR_FLAME =
            FufoMod.registrate().<MeteorFlameBlockEntity>blockEntity("meteor_flame", MeteorFlameBlockEntity::new).validBlocks(BlockRegistrate.METEOR_FIRE).register();

    public static final BlockEntityEntry<ArrayBlockEntity> CRUDE_ARRAY =
            FufoMod.registrate().<ArrayBlockEntity>blockEntity("crude_array", ArrayBlockEntity::new).renderer(() -> ArrayRenderer::new).validBlocks(BlockRegistrate.CRUDE_ARRAY).register();

    public static void register() {
    }
}