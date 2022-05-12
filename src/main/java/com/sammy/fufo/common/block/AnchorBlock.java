package com.sammy.fufo.common.block;

import com.sammy.fufo.common.blockentity.AnchorBlockEntity;
import com.sammy.ortus.systems.block.OrtusEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AnchorBlock<T extends AnchorBlockEntity> extends OrtusEntityBlock<T> {

    public static final VoxelShape SHAPE = Block.box(5, 5, 5, 11, 11, 11);

    public AnchorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
