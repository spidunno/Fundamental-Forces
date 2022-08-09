package com.sammy.fufo.core.registratation;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.client.renderers.block.*;
import com.sammy.fufo.common.blockentity.*;
import com.sammy.fufo.common.logistics.fluid_tank.FluidTankBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class BlockEntityRegistrate {

    public static final BlockEntityEntry<FluidTankBlockEntity> FLUID_TANK =
        FufoMod.registrate().<FluidTankBlockEntity>blockEntity("fluid_tank", FluidTankBlockEntity::new).validBlocks(BlockRegistrate.FLUID_TANK).register();

    public static final BlockEntityEntry<BurnerExtractorBlockEntity> BURNER_EXTRACTOR =
            FufoMod.registrate().<BurnerExtractorBlockEntity>blockEntity("burner_extractor", BurnerExtractorBlockEntity::new).register();

    public static final BlockEntityEntry<UITestBlockEntity> UI_TEST_BLOCK =
            FufoMod.registrate().<UITestBlockEntity>blockEntity("ui_test_block", UITestBlockEntity::new).renderer(() -> UIRenderer::new).validBlocks(BlockRegistrate.UI_TEST).register();

    public static final BlockEntityEntry<PipeNodeBlockEntity> ANCHOR =
            FufoMod.registrate().<PipeNodeBlockEntity>blockEntity("anchor", PipeNodeBlockEntity::new).validBlocks(BlockRegistrate.PIPE_ANCHOR).renderer(() -> AnchorRenderer::new).register();

    public static final BlockEntityEntry<ArrayBlockEntity> CRUDE_ARRAY =
            FufoMod.registrate().<ArrayBlockEntity>blockEntity("crude_array", ArrayBlockEntity::new).renderer(() -> ArrayRenderer::new).validBlocks(BlockRegistrate.CRUDE_ARRAY).register();

    public static final BlockEntityEntry<OrbBlockEntity> ORB =
            FufoMod.registrate().<OrbBlockEntity>blockEntity("orb", OrbBlockEntity::new).renderer(() -> OrbRenderer::new).validBlocks(BlockRegistrate.FORCE_ORB).register();

    public static final BlockEntityEntry<MeteorFlameBlockEntity> METEOR_FLAME =
            FufoMod.registrate().<MeteorFlameBlockEntity>blockEntity("meteor_flame", MeteorFlameBlockEntity::new).validBlocks(BlockRegistrate.METEOR_FIRE).register();


    public static final BlockEntityEntry<CrudePrimerBlockEntity> CRUDE_PRIMER =
            FufoMod.registrate().<CrudePrimerBlockEntity>blockEntity("crude_primer", CrudePrimerBlockEntity::new).renderer(() -> CrudePrimerRenderer::new).validBlocks(BlockRegistrate.CRUDE_PRIMER).register();

    public static final BlockEntityEntry<CrudeNeedleBlockEntity> CRUDE_NEEDLE =
            FufoMod.registrate().<CrudeNeedleBlockEntity>blockEntity("crude_needle", CrudeNeedleBlockEntity::new).validBlocks(BlockRegistrate.CRUDE_NEEDLE).register();

    public static final BlockEntityEntry<PumpBlockEntity> PUMP =
    		FufoMod.registrate().<PumpBlockEntity>blockEntity("pump", PumpBlockEntity::new).validBlocks(BlockRegistrate.PUMP).renderer(() -> AnchorRenderer::new).register();
    public static final BlockEntityEntry<ValveBlockEntity> VALVE =
    		FufoMod.registrate().<ValveBlockEntity>blockEntity("valve", ValveBlockEntity::new).validBlocks(BlockRegistrate.VALVE).register();
    public static void register() {
    }
}