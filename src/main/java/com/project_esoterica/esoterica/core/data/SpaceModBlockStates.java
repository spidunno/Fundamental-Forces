package com.project_esoterica.esoterica.core.data;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.core.registry.block.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fmllegacy.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.UPPER;

public class SpaceModBlockStates extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public SpaceModBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, EsotericaMod.MOD_ID, exFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Space Mod BlockStates";
    }

    @Override
    protected void registerStatesAndModels() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BlockRegistry.BLOCKS.getEntries());

        EsotericaHelper.takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(this::grassBlock);
        EsotericaHelper.takeAll(blocks, b -> b.get() instanceof StairBlock).forEach(this::stairsBlock);
        EsotericaHelper.takeAll(blocks, b -> b.get() instanceof RotatedPillarBlock).forEach(this::logBlock);
        EsotericaHelper.takeAll(blocks, b -> b.get() instanceof WallBlock).forEach(this::wallBlock);
        EsotericaHelper.takeAll(blocks, b -> b.get() instanceof FenceBlock).forEach(this::fenceBlock);
        EsotericaHelper.takeAll(blocks, b -> b.get() instanceof FenceGateBlock).forEach(this::fenceGateBlock);
        EsotericaHelper.takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(this::doorBlock);
        EsotericaHelper.takeAll(blocks, b -> b.get() instanceof TrapDoorBlock).forEach(this::trapdoorBlock);
        EsotericaHelper.takeAll(blocks, b -> b.get() instanceof PressurePlateBlock).forEach(this::pressurePlateBlock);
        EsotericaHelper.takeAll(blocks, b -> b.get() instanceof ButtonBlock).forEach(this::buttonBlock);
        EsotericaHelper.takeAll(blocks, b -> b.get() instanceof DoublePlantBlock).forEach(this::tallPlantBlock);
        EsotericaHelper.takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(this::plantBlock);
        EsotericaHelper.takeAll(blocks, b -> b.get() instanceof LanternBlock).forEach(this::lanternBlock);

        Collection<RegistryObject<Block>> slabs = EsotericaHelper.takeAll(blocks, b -> b.get() instanceof SlabBlock);
        blocks.forEach(this::basicBlock);
        slabs.forEach(this::slabBlock);

    }

    public void basicBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get());
    }

    public void signBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String particleName = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath().replaceFirst("_wall", "").replaceFirst("_sign", "") + "_planks";

        ModelFile sign = models().withExistingParent(name, EsotericaHelper.prefix("block/template_sign")).texture("particle", EsotericaHelper.prefix("block/" + particleName));
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(sign).build());
    }

    public void glowingBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String glow = name + "_glow";
        ModelFile farmland = models().withExistingParent(name, EsotericaHelper.prefix("block/template_glowing_block")).texture("all", EsotericaHelper.prefix("block/" + name)).texture("particle", EsotericaHelper.prefix("block/" + name)).texture("glow", EsotericaHelper.prefix("block/" + glow));
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(farmland).build());
    }

    public void rotatedBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile file = models().cubeAll(name, EsotericaHelper.prefix("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).partialState().modelForState()
                .modelFile(file)
                .nextModel().modelFile(file).rotationY(90)
                .nextModel().modelFile(file).rotationY(180)
                .nextModel().modelFile(file).rotationY(270)
                .addModel();
    }

    public void torchBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile torch = models().torchWall(blockRegistryObject.get().getRegistryName().getPath(), EsotericaHelper.prefix("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(torch).build());
    }

    public void wallTorchBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile torch = models().torchWall(blockRegistryObject.get().getRegistryName().getPath(), EsotericaHelper.prefix("block/" + name.substring(5)));

        getVariantBuilder(blockRegistryObject.get())
                .partialState().with(WallTorchBlock.FACING, Direction.NORTH)
                .modelForState().modelFile(torch).rotationY(270).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.WEST)
                .modelForState().modelFile(torch).rotationY(180).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.SOUTH)
                .modelForState().modelFile(torch).rotationY(90).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.EAST)
                .modelForState().modelFile(torch).addModel();
    }

    public void grassBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile file = models().cubeBottomTop(name, EsotericaHelper.prefix("block/" + name + "_side"), new ResourceLocation("block/dirt"), EsotericaHelper.prefix("block/" + name + "_top"));

        getVariantBuilder(blockRegistryObject.get()).partialState().modelForState()
                .modelFile(file)
                .nextModel().modelFile(file).rotationY(90)
                .nextModel().modelFile(file).rotationY(180)
                .nextModel().modelFile(file).rotationY(270)
                .addModel();
    }

    public void trapdoorBlock(RegistryObject<Block> blockRegistryObject) {
        trapdoorBlock((TrapDoorBlock) blockRegistryObject.get(), blockTexture(blockRegistryObject.get()), true);
    }

    public void doorBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        doorBlock((DoorBlock) blockRegistryObject.get(), EsotericaHelper.prefix("block/" + name + "_bottom"), EsotericaHelper.prefix("block/" + name + "_top"));
    }

    public void fenceGateBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 11);
        fenceGateBlock((FenceGateBlock) blockRegistryObject.get(), EsotericaHelper.prefix("block/" + baseName));
    }

    public void fenceBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 6);
        fenceBlock((FenceBlock) blockRegistryObject.get(), EsotericaHelper.prefix("block/" + baseName));
    }

    public void wallBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        wallBlock((WallBlock) blockRegistryObject.get(), EsotericaHelper.prefix("block/" + baseName));
    }

    public void stairsBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        stairsBlock((StairBlock) blockRegistryObject.get(), EsotericaHelper.prefix("block/" + baseName));
    }

    public void pressurePlateBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 15);
        ModelFile pressurePlateDown = models().withExistingParent(name + "_down", new ResourceLocation("block/pressure_plate_down")).texture("texture", EsotericaHelper.prefix("block/" + baseName));
        ModelFile pressurePlateUp = models().withExistingParent(name + "_up", new ResourceLocation("block/pressure_plate_up")).texture("texture", EsotericaHelper.prefix("block/" + baseName));

        getVariantBuilder(blockRegistryObject.get()).partialState().with(PressurePlateBlock.POWERED, true).modelForState().modelFile(pressurePlateDown).addModel().partialState().with(PressurePlateBlock.POWERED, false).modelForState().modelFile(pressurePlateUp).addModel();
    }

    public void lanternBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile lantern = models().withExistingParent(name, new ResourceLocation("block/template_lantern")).texture("lantern", EsotericaHelper.prefix("block/" + name));
        ModelFile hangingLantern = models().withExistingParent(name + "_hanging", new ResourceLocation("block/template_hanging_lantern")).texture("lantern", EsotericaHelper.prefix("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).partialState().with(LanternBlock.HANGING, true).modelForState().modelFile(hangingLantern).addModel().partialState().with(LanternBlock.HANGING, false).modelForState().modelFile(lantern).addModel();
    }

    public void buttonBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        ModelFile buttom = models().withExistingParent(name, new ResourceLocation("block/button")).texture("texture", EsotericaHelper.prefix("block/" + baseName));
        ModelFile buttonPressed = models().withExistingParent(name + "_pressed", new ResourceLocation("block/button_pressed")).texture("texture", EsotericaHelper.prefix("block/" + baseName));
        Function<BlockState, ModelFile> modelFunc = $ -> buttom;
        Function<BlockState, ModelFile> pressedModelFunc = $ -> buttonPressed;
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(BlockStateProperties.POWERED) ? pressedModelFunc.apply(s) : modelFunc.apply(s)).uvLock(s.getValue(BlockStateProperties.ATTACH_FACE).equals(AttachFace.WALL)).rotationX(s.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90).rotationY(((s.getValue(BlockStateProperties.HORIZONTAL_FACING).get2DDataValue() + 180) + (s.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360).build());
        models().withExistingParent(name + "_inventory", new ResourceLocation("block/button_inventory")).texture("texture", EsotericaHelper.prefix("block/" + baseName));

    }

    public void tallPlantBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile bottom = models().withExistingParent(name + "_bottom", new ResourceLocation("block/cross")).texture("cross", EsotericaHelper.prefix("block/" + name + "_bottom"));
        ModelFile top = models().withExistingParent(name + "_top", new ResourceLocation("block/cross")).texture("cross", EsotericaHelper.prefix("block/" + name + "_top"));

        getVariantBuilder(blockRegistryObject.get()).partialState().with(DoublePlantBlock.HALF, LOWER).modelForState().modelFile(bottom).addModel().partialState().with(DoublePlantBlock.HALF, UPPER).modelForState().modelFile(top).addModel();
    }

    public void plantBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile cross = models().withExistingParent(name, new ResourceLocation("block/cross")).texture("cross", EsotericaHelper.prefix("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(cross).build());
    }

    public void slabBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        slabBlock((SlabBlock) blockRegistryObject.get(), EsotericaHelper.prefix(baseName), EsotericaHelper.prefix("block/" + baseName));
    }

    public void logBlock(RegistryObject<Block> blockRegistryObject) {
        if (blockRegistryObject.get().getRegistryName().getPath().endsWith("wood")) {
            woodBlock(blockRegistryObject);
            return;
        }
        logBlock((RotatedPillarBlock) blockRegistryObject.get());
    }

    public void sapFilledBlock(RegistryObject<Block> blockRegistryObject) {
        axisBlock((RotatedPillarBlock) blockRegistryObject.get(), EsotericaHelper.prefix("block/sap_filled_runewood_log"), EsotericaHelper.prefix("block/stripped_runewood_log_top"));
    }

    public void woodBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name + "_log";
        axisBlock((RotatedPillarBlock) blockRegistryObject.get(), EsotericaHelper.prefix("block/" + baseName), EsotericaHelper.prefix("block/" + baseName));
    }
}