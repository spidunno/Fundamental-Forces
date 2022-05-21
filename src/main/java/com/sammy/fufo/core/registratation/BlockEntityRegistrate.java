package com.sammy.fufo.core.registratation;
import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.blockentity.*;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BlockEntityRegistrate {
    public static final BlockEntityEntry<BlockEntity> BURNER_EXTRACTOR =
            FufoMod.registrate().blockEntity("burner_extractor",BurnerExtractorBlockEntity::new).register();
    public static final BlockEntityEntry<BlockEntity> UI_TEST_BLOCK =
            FufoMod.registrate().blockEntity("ui_test_block", UITestBlockEntity::new).register();
    public static final BlockEntityEntry<BlockEntity> ANCHOR =
            FufoMod.registrate().blockEntity("anchor", AnchorBlockEntity::new).register();
    public static final BlockEntityEntry<BlockEntity> ORB =
            FufoMod.registrate().blockEntity("orb", OrbBlockEntity::new).register();
    public static final BlockEntityEntry<BlockEntity> METEOR_FLAME =
            FufoMod.registrate().blockEntity("meteor_flame", MeteorFlameBlockEntity::new).register();

    public static void register() {}
}