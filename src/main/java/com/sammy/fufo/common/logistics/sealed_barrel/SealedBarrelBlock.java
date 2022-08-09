package com.sammy.fufo.common.logistics.sealed_barrel;

import com.sammy.fufo.common.blockentity.SealedBarrelBlockEntity;
import com.sammy.ortus.systems.block.OrtusEntityBlock;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Locale;

public class SealedBarrelBlock<T extends SealedBarrelBlockEntity> extends OrtusEntityBlock<T> {

    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", Shape.class);

    public enum Shape implements StringRepresentable {
        NORMAL, NO_WINDOW;

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    public SealedBarrelBlock(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(defaultBlockState().setValue(SHAPE, Shape.NORMAL));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(SHAPE);
    }
}