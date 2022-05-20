/*package com.sammy.fufo.core.data;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.block.OrbBlock;
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
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Function;

import static com.sammy.fufo.FufoMod.fufoPath;
import static com.sammy.fufo.core.setup.content.block.AllBlocks.METEOR_FIRE;
import static com.sammy.ortus.helpers.DataHelper.takeAll;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.UPPER;

public class SpaceModBlockStates extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public SpaceModBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, FufoMod.FUFO, exFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Space Mod BlockStates";
    }

    @Override
    protected void registerStatesAndModels() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(AllBlocks.BLOCKS.getEntries());

        ArrayList<RegistryObject<Block>> customModels = new ArrayList<>(List.of(METEOR_FIRE));

        takeAll(blocks, customModels::contains);

        takeAll(blocks, b -> b.get() instanceof OrbBlock).forEach(this::emptyBlock);

        takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(this::grassBlock);
        takeAll(blocks, b -> b.get() instanceof StairBlock).forEach(this::stairsBlock);
        takeAll(blocks, b -> b.get() instanceof RotatedPillarBlock).forEach(this::logBlock);
        takeAll(blocks, b -> b.get() instanceof WallBlock).forEach(this::wallBlock);
        takeAll(blocks, b -> b.get() instanceof FenceBlock).forEach(this::fenceBlock);
        takeAll(blocks, b -> b.get() instanceof FenceGateBlock).forEach(this::fenceGateBlock);
        takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(this::doorBlock);
        takeAll(blocks, b -> b.get() instanceof TrapDoorBlock).forEach(this::trapdoorBlock);
        takeAll(blocks, b -> b.get() instanceof PressurePlateBlock).forEach(this::pressurePlateBlock);
        takeAll(blocks, b -> b.get() instanceof ButtonBlock).forEach(this::buttonBlock);
        takeAll(blocks, b -> b.get() instanceof DoublePlantBlock).forEach(this::tallPlantBlock);
        takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(this::plantBlock);
        takeAll(blocks, b -> b.get() instanceof LanternBlock).forEach(this::lanternBlock);

        Collection<RegistryObject<Block>> slabs = takeAll(blocks, b -> b.get() instanceof SlabBlock);
        blocks.forEach(this::basicBlock);
        slabs.forEach(this::slabBlock);

    }

    public void basicBlock(RegistryObject<Block> AllBlocksObject) {
        simpleBlock(AllBlocksObject.get());
    }

    public void emptyBlock(RegistryObject<Block> AllBlocksObject)
    {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        ModelFile empty = models().withExistingParent(name, new ResourceLocation("block/air"));
        getVariantBuilder(AllBlocksObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(empty).build());
    }
    public void signBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        String particleName = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath().replaceFirst("_wall", "").replaceFirst("_sign", "") + "_planks";

        ModelFile sign = models().withExistingParent(name, fufoPath("block/template_sign")).texture("particle", fufoPath("block/" + particleName));
        getVariantBuilder(AllBlocksObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(sign).build());
    }

    public void customBlock(RegistryObject<Block> AllBlocksObject)
    {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        ModelFile model = models().withExistingParent(name+"_child", fufoPath("block/"+name));
        getVariantBuilder(AllBlocksObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(model).build());
    }
    public void glowingBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        String glow = name + "_glow";
        ModelFile farmland = models().withExistingParent(name, fufoPath("block/template_glowing_block")).texture("all", fufoPath("block/" + name)).texture("particle", fufoPath("block/" + name)).texture("glow", fufoPath("block/" + glow));
        getVariantBuilder(AllBlocksObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(farmland).build());
    }

    public void rotatedBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        ModelFile file = models().cubeAll(name, fufoPath("block/" + name));

        getVariantBuilder(AllBlocksObject.get()).partialState().modelForState()
                .modelFile(file)
                .nextModel().modelFile(file).rotationY(90)
                .nextModel().modelFile(file).rotationY(180)
                .nextModel().modelFile(file).rotationY(270)
                .addModel();
    }

    public void torchBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        ModelFile torch = models().torchWall(AllBlocksObject.get().getRegistryName().getPath(), fufoPath("block/" + name));

        getVariantBuilder(AllBlocksObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(torch).build());
    }

    public void wallTorchBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        ModelFile torch = models().torchWall(AllBlocksObject.get().getRegistryName().getPath(), fufoPath("block/" + name.substring(5)));

        getVariantBuilder(AllBlocksObject.get())
                .partialState().with(WallTorchBlock.FACING, Direction.NORTH)
                .modelForState().modelFile(torch).rotationY(270).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.WEST)
                .modelForState().modelFile(torch).rotationY(180).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.SOUTH)
                .modelForState().modelFile(torch).rotationY(90).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.EAST)
                .modelForState().modelFile(torch).addModel();
    }

    public void grassBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        ModelFile file = models().cubeBottomTop(name, fufoPath("block/" + name + "_side"), new ResourceLocation("block/dirt"), fufoPath("block/" + name + "_top"));

        getVariantBuilder(AllBlocksObject.get()).partialState().modelForState()
                .modelFile(file)
                .nextModel().modelFile(file).rotationY(90)
                .nextModel().modelFile(file).rotationY(180)
                .nextModel().modelFile(file).rotationY(270)
                .addModel();
    }

    public void trapdoorBlock(RegistryObject<Block> AllBlocksObject) {
        trapdoorBlock((TrapDoorBlock) AllBlocksObject.get(), blockTexture(AllBlocksObject.get()), true);
    }

    public void doorBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        doorBlock((DoorBlock) AllBlocksObject.get(), fufoPath("block/" + name + "_bottom"), fufoPath("block/" + name + "_top"));
    }

    public void fenceGateBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 11);
        fenceGateBlock((FenceGateBlock) AllBlocksObject.get(), fufoPath("block/" + baseName));
    }

    public void fenceBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 6);
        fenceBlock((FenceBlock) AllBlocksObject.get(), fufoPath("block/" + baseName));
    }

    public void wallBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        wallBlock((WallBlock) AllBlocksObject.get(), fufoPath("block/" + baseName));
    }

    public void stairsBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        stairsBlock((StairBlock) AllBlocksObject.get(), fufoPath("block/" + baseName));
    }

    public void pressurePlateBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 15);
        ModelFile pressurePlateDown = models().withExistingParent(name + "_down", new ResourceLocation("block/pressure_plate_down")).texture("texture", fufoPath("block/" + baseName));
        ModelFile pressurePlateUp = models().withExistingParent(name + "_up", new ResourceLocation("block/pressure_plate_up")).texture("texture", fufoPath("block/" + baseName));

        getVariantBuilder(AllBlocksObject.get()).partialState().with(PressurePlateBlock.POWERED, true).modelForState().modelFile(pressurePlateDown).addModel().partialState().with(PressurePlateBlock.POWERED, false).modelForState().modelFile(pressurePlateUp).addModel();
    }

    public void lanternBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        ModelFile lantern = models().withExistingParent(name, new ResourceLocation("block/template_lantern")).texture("lantern", fufoPath("block/" + name));
        ModelFile hangingLantern = models().withExistingParent(name + "_hanging", new ResourceLocation("block/template_hanging_lantern")).texture("lantern", fufoPath("block/" + name));

        getVariantBuilder(AllBlocksObject.get()).partialState().with(LanternBlock.HANGING, true).modelForState().modelFile(hangingLantern).addModel().partialState().with(LanternBlock.HANGING, false).modelForState().modelFile(lantern).addModel();
    }

    public void buttonBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        ModelFile buttom = models().withExistingParent(name, new ResourceLocation("block/button")).texture("texture", fufoPath("block/" + baseName));
        ModelFile buttonPressed = models().withExistingParent(name + "_pressed", new ResourceLocation("block/button_pressed")).texture("texture", fufoPath("block/" + baseName));
        Function<BlockState, ModelFile> modelFunc = $ -> buttom;
        Function<BlockState, ModelFile> pressedModelFunc = $ -> buttonPressed;
        getVariantBuilder(AllBlocksObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(BlockStateProperties.POWERED) ? pressedModelFunc.apply(s) : modelFunc.apply(s)).uvLock(s.getValue(BlockStateProperties.ATTACH_FACE).equals(AttachFace.WALL)).rotationX(s.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90).rotationY(((s.getValue(BlockStateProperties.HORIZONTAL_FACING).get2DDataValue() + 180) + (s.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360).build());
        models().withExistingParent(name + "_inventory", new ResourceLocation("block/button_inventory")).texture("texture", fufoPath("block/" + baseName));

    }

    public void tallPlantBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        ModelFile bottom = models().withExistingParent(name + "_bottom", new ResourceLocation("block/cross")).texture("cross", fufoPath("block/" + name + "_bottom"));
        ModelFile top = models().withExistingParent(name + "_top", new ResourceLocation("block/cross")).texture("cross", fufoPath("block/" + name + "_top"));

        getVariantBuilder(AllBlocksObject.get()).partialState().with(DoublePlantBlock.HALF, LOWER).modelForState().modelFile(bottom).addModel().partialState().with(DoublePlantBlock.HALF, UPPER).modelForState().modelFile(top).addModel();
    }

    public void plantBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        ModelFile cross = models().withExistingParent(name, new ResourceLocation("block/cross")).texture("cross", fufoPath("block/" + name));

        getVariantBuilder(AllBlocksObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(cross).build());
    }

    public void slabBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        slabBlock((SlabBlock) AllBlocksObject.get(), fufoPath(baseName), fufoPath("block/" + baseName));
    }

    public void logBlock(RegistryObject<Block> AllBlocksObject) {
        if (AllBlocksObject.get().getRegistryName().getPath().endsWith("wood")) {
            woodBlock(AllBlocksObject);
            return;
        }
        logBlock((RotatedPillarBlock) AllBlocksObject.get());
    }


    public void woodBlock(RegistryObject<Block> AllBlocksObject) {
        String name = Registry.BLOCK.getKey(AllBlocksObject.get()).getPath();
        String baseName = name + "_log";
        axisBlock((RotatedPillarBlock) AllBlocksObject.get(), fufoPath("block/" + baseName), fufoPath("block/" + baseName));
    }
}*/