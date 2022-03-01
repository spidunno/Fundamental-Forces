package com.sammy.fundamental_forces.common.block;

import com.sammy.fundamental_forces.common.blockentity.OrbBlockEntity;
import com.sammy.fundamental_forces.core.systems.block.SimpleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OrbBlock<T extends OrbBlockEntity> extends SimpleBlock<T> {

    public static final VoxelShape SHAPE = Block.box(6, 6, 6, 10, 10, 10);

    public OrbBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}