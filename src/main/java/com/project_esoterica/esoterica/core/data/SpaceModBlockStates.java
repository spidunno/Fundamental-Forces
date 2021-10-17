package com.project_esoterica.esoterica.core.data;

import com.project_esoterica.esoterica.EmpiricalEsoterica;
import com.project_esoterica.esoterica.EsotericHelper;
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
        super(gen, EmpiricalEsoterica.MOD_ID, exFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Space Mod BlockStates";
    }

    @Override
    protected void registerStatesAndModels() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BlockRegistry.BLOCKS.getEntries());

        EsotericHelper.takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(this::grassBlock);
        EsotericHelper.takeAll(blocks, b -> b.get() instanceof StairBlock).forEach(this::stairsBlock);
        EsotericHelper.takeAll(blocks, b -> b.get() instanceof RotatedPillarBlock).forEach(this::logBlock);
        EsotericHelper.takeAll(blocks, b -> b.get() instanceof WallBlock).forEach(this::wallBlock);
        EsotericHelper.takeAll(blocks, b -> b.get() instanceof FenceBlock).forEach(this::fenceBlock);
        EsotericHelper.takeAll(blocks, b -> b.get() instanceof FenceGateBlock).forEach(this::fenceGateBlock);
        EsotericHelper.takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(this::doorBlock);
        EsotericHelper.takeAll(blocks, b -> b.get() instanceof TrapDoorBlock).forEach(this::trapdoorBlock);
        EsotericHelper.takeAll(blocks, b -> b.get() instanceof PressurePlateBlock).forEach(this::pressurePlateBlock);
        EsotericHelper.takeAll(blocks, b -> b.get() instanceof ButtonBlock).forEach(this::buttonBlock);
        EsotericHelper.takeAll(blocks, b -> b.get() instanceof DoublePlantBlock).forEach(this::tallPlantBlock);
        EsotericHelper.takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(this::plantBlock);
        EsotericHelper.takeAll(blocks, b -> b.get() instanceof LanternBlock).forEach(this::lanternBlock);

        Collection<RegistryObject<Block>> slabs = EsotericHelper.takeAll(blocks, b -> b.get() instanceof SlabBlock);
        blocks.forEach(this::basicBlock);
        slabs.forEach(this::slabBlock);

    }

    public void basicBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get());
    }

    public void signBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String particleName = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath().replaceFirst("_wall", "").replaceFirst("_sign", "") + "_planks";

        ModelFile sign = models().withExistingParent(name, EsotericHelper.prefix("block/template_sign")).texture("particle", EsotericHelper.prefix("block/" + particleName));
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(sign).build());
    }

    public void glowingBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String glow = name + "_glow";
        ModelFile farmland = models().withExistingParent(name, EsotericHelper.prefix("block/template_glowing_block")).texture("all", EsotericHelper.prefix("block/" + name)).texture("particle", EsotericHelper.prefix("block/" + name)).texture("glow", EsotericHelper.prefix("block/" + glow));
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(farmland).build());
    }

    public void rotatedBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile file = models().cubeAll(name, EsotericHelper.prefix("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).partialState().modelForState()
                .modelFile(file)
                .nextModel().modelFile(file).rotationY(90)
                .nextModel().modelFile(file).rotationY(180)
                .nextModel().modelFile(file).rotationY(270)
                .addModel();
    }

    public void torchBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile torch = models().torchWall(blockRegistryObject.get().getRegistryName().getPath(), EsotericHelper.prefix("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(torch).build());
    }

    public void wallTorchBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile torch = models().torchWall(blockRegistryObject.get().getRegistryName().getPath(), EsotericHelper.prefix("block/" + name.substring(5)));

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
        ModelFile file = models().cubeBottomTop(name, EsotericHelper.prefix("block/" + name + "_side"), new ResourceLocation("block/dirt"), EsotericHelper.prefix("block/" + name + "_top"));

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
        doorBlock((DoorBlock) blockRegistryObject.get(), EsotericHelper.prefix("block/" + name + "_bottom"), EsotericHelper.prefix("block/" + name + "_top"));
    }

    public void fenceGateBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 11);
        fenceGateBlock((FenceGateBlock) blockRegistryObject.get(), EsotericHelper.prefix("block/" + baseName));
    }

    public void fenceBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 6);
        fenceBlock((FenceBlock) blockRegistryObject.get(), EsotericHelper.prefix("block/" + baseName));
    }

    public void wallBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        wallBlock((WallBlock) blockRegistryObject.get(), EsotericHelper.prefix("block/" + baseName));
    }

    public void stairsBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        stairsBlock((StairBlock) blockRegistryObject.get(), EsotericHelper.prefix("block/" + baseName));
    }

    public void pressurePlateBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 15);
        ModelFile pressurePlateDown = models().withExistingParent(name + "_down", new ResourceLocation("block/pressure_plate_down")).texture("texture", EsotericHelper.prefix("block/" + baseName));
        ModelFile pressurePlateUp = models().withExistingParent(name + "_up", new ResourceLocation("block/pressure_plate_up")).texture("texture", EsotericHelper.prefix("block/" + baseName));

        getVariantBuilder(blockRegistryObject.get()).partialState().with(PressurePlateBlock.POWERED, true).modelForState().modelFile(pressurePlateDown).addModel().partialState().with(PressurePlateBlock.POWERED, false).modelForState().modelFile(pressurePlateUp).addModel();
    }

    public void lanternBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile lantern = models().withExistingParent(name, new ResourceLocation("block/template_lantern")).texture("lantern", EsotericHelper.prefix("block/" + name));
        ModelFile hangingLantern = models().withExistingParent(name + "_hanging", new ResourceLocation("block/template_hanging_lantern")).texture("lantern", EsotericHelper.prefix("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).partialState().with(LanternBlock.HANGING, true).modelForState().modelFile(hangingLantern).addModel().partialState().with(LanternBlock.HANGING, false).modelForState().modelFile(lantern).addModel();
    }

    public void buttonBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        ModelFile buttom = models().withExistingParent(name, new ResourceLocation("block/button")).texture("texture", EsotericHelper.prefix("block/" + baseName));
        ModelFile buttonPressed = models().withExistingParent(name + "_pressed", new ResourceLocation("block/button_pressed")).texture("texture", EsotericHelper.prefix("block/" + baseName));
        Function<BlockState, ModelFile> modelFunc = $ -> buttom;
        Function<BlockState, ModelFile> pressedModelFunc = $ -> buttonPressed;
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(BlockStateProperties.POWERED) ? pressedModelFunc.apply(s) : modelFunc.apply(s)).uvLock(s.getValue(BlockStateProperties.ATTACH_FACE).equals(AttachFace.WALL)).rotationX(s.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90).rotationY(((s.getValue(BlockStateProperties.HORIZONTAL_FACING).get2DDataValue() + 180) + (s.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360).build());
        models().withExistingParent(name + "_inventory", new ResourceLocation("block/button_inventory")).texture("texture", EsotericHelper.prefix("block/" + baseName));

    }

    public void tallPlantBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile bottom = models().withExistingParent(name + "_bottom", new ResourceLocation("block/cross")).texture("cross", EsotericHelper.prefix("block/" + name + "_bottom"));
        ModelFile top = models().withExistingParent(name + "_top", new ResourceLocation("block/cross")).texture("cross", EsotericHelper.prefix("block/" + name + "_top"));

        getVariantBuilder(blockRegistryObject.get()).partialState().with(DoublePlantBlock.HALF, LOWER).modelForState().modelFile(bottom).addModel().partialState().with(DoublePlantBlock.HALF, UPPER).modelForState().modelFile(top).addModel();
    }

    public void plantBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile cross = models().withExistingParent(name, new ResourceLocation("block/cross")).texture("cross", EsotericHelper.prefix("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(cross).build());
    }

    public void slabBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        slabBlock((SlabBlock) blockRegistryObject.get(), EsotericHelper.prefix(baseName), EsotericHelper.prefix("block/" + baseName));
    }

    public void logBlock(RegistryObject<Block> blockRegistryObject) {
        if (blockRegistryObject.get().getRegistryName().getPath().endsWith("wood")) {
            woodBlock(blockRegistryObject);
            return;
        }
        logBlock((RotatedPillarBlock) blockRegistryObject.get());
    }

    public void sapFilledBlock(RegistryObject<Block> blockRegistryObject) {
        axisBlock((RotatedPillarBlock) blockRegistryObject.get(), EsotericHelper.prefix("block/sap_filled_runewood_log"), EsotericHelper.prefix("block/stripped_runewood_log_top"));
    }

    public void woodBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name + "_log";
        axisBlock((RotatedPillarBlock) blockRegistryObject.get(), EsotericHelper.prefix("block/" + baseName), EsotericHelper.prefix("block/" + baseName));
    }
}