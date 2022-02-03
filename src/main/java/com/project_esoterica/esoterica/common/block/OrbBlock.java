package com.project_esoterica.esoterica.common.block;

import com.project_esoterica.esoterica.common.blockentity.OrbBlockEntity;
import com.project_esoterica.esoterica.core.setup.block.BlockEntityRegistry;
import com.project_esoterica.esoterica.core.systems.block.SimpleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OrbBlock extends SimpleBlock<OrbBlockEntity> {

    public static final VoxelShape SHAPE = Block.box(6, 6, 6, 10, 10, 10);

    public OrbBlock(Properties properties) {
        super(properties);
        setTile(BlockEntityRegistry.ORB);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}