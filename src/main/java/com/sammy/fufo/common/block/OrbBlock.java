package com.sammy.fufo.common.block;

import com.sammy.fufo.common.blockentity.OrbBlockEntity;
import com.sammy.ortus.systems.block.OrtusEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OrbBlock<T extends OrbBlockEntity> extends OrtusEntityBlock<T> {

    public static final VoxelShape SHAPE = Block.box(6, 6, 6, 10, 10, 10);

    public OrbBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}