package team.lodestar.fufo.registry.common;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.client.rendering.block.*;
import team.lodestar.fufo.common.blockentity.*;
import team.lodestar.fufo.common.fluid.PipeNodeBlockEntity;
import team.lodestar.fufo.common.fluid.fluid_tank.FluidTankBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import team.lodestar.fufo.common.fluid.pump.PumpBlockEntity;
import team.lodestar.fufo.common.fluid.sealed_barrel.SealedBarrelBlockEntity;
import team.lodestar.fufo.common.fluid.valve.ValveBlockEntity;

public class FufoBlockEntities {

    public static final BlockEntityEntry<SealedBarrelBlockEntity> SEALED_BARREL =
            FufoMod.registrate().blockEntity("sealed_barrel", SealedBarrelBlockEntity::new).validBlocks(FufoBlocks.SEALED_BARREL).register();

    public static final BlockEntityEntry<FluidTankBlockEntity> FLUID_TANK =
            FufoMod.registrate().blockEntity("fluid_tank", FluidTankBlockEntity::new).validBlocks(FufoBlocks.SEALED_TANK).register();

    public static final BlockEntityEntry<BurnerExtractorBlockEntity> BURNER_EXTRACTOR =
            FufoMod.registrate().blockEntity("burner_extractor", BurnerExtractorBlockEntity::new).register();

    public static final BlockEntityEntry<UITestBlockEntity> UI_TEST_BLOCK =
            FufoMod.registrate().blockEntity("ui_test_block", UITestBlockEntity::new).renderer(() -> UIRenderer::new).validBlocks(FufoBlocks.UI_TEST).register();

    public static final BlockEntityEntry<PipeNodeBlockEntity> ANCHOR =
            FufoMod.registrate().blockEntity("anchor", PipeNodeBlockEntity::new).validBlocks(FufoBlocks.PIPE_ANCHOR).renderer(() -> AnchorRenderer::new).register();

    public static final BlockEntityEntry<ArrayBlockEntity> CRUDE_ARRAY =
            FufoMod.registrate().blockEntity("crude_array", ArrayBlockEntity::new).renderer(() -> ArrayRenderer::new).validBlocks(FufoBlocks.CRUDE_ARRAY).register();

    public static final BlockEntityEntry<OrbBlockEntity> ORB =
            FufoMod.registrate().blockEntity("orb", OrbBlockEntity::new).renderer(() -> OrbRenderer::new).validBlocks(FufoBlocks.FORCE_ORB).register();

    public static final BlockEntityEntry<MeteorFlameBlockEntity> METEOR_FLAME =
            FufoMod.registrate().blockEntity("meteor_flame", MeteorFlameBlockEntity::new).validBlocks(FufoBlocks.METEOR_FIRE).register();

    public static final BlockEntityEntry<CrudePrimerBlockEntity> CRUDE_PRIMER =
            FufoMod.registrate().blockEntity("crude_primer", CrudePrimerBlockEntity::new).renderer(() -> CrudePrimerRenderer::new).validBlocks(FufoBlocks.CRUDE_PRIMER).register();

    public static final BlockEntityEntry<CrudeNeedleBlockEntity> CRUDE_NEEDLE =
            FufoMod.registrate().blockEntity("crude_needle", CrudeNeedleBlockEntity::new).validBlocks(FufoBlocks.CRUDE_NEEDLE).register();

    public static final BlockEntityEntry<PumpBlockEntity> PUMP =
            FufoMod.registrate().blockEntity("pump", PumpBlockEntity::new).validBlocks(FufoBlocks.PUMP).renderer(() -> AnchorRenderer::new).register();
    public static final BlockEntityEntry<ValveBlockEntity> VALVE =
            FufoMod.registrate().blockEntity("valve", ValveBlockEntity::new).validBlocks(FufoBlocks.VALVE).register();

    public static void register() {
    }
}