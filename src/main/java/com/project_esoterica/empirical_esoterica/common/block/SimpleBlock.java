package com.project_esoterica.empirical_esoterica.common.block;

import com.project_esoterica.empirical_esoterica.common.blockentity.SimpleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class SimpleBlock extends Block implements EntityBlock {
    protected VoxelShape shape = null;
    protected BlockEntityType<? extends BlockEntity> tileEntityType = null;

    public SimpleBlock(Properties properties) {
        super(properties);
    }

    public SimpleBlock setTile(BlockEntityType<? extends BlockEntity> type) {
        this.tileEntityType = type;
        return this;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return hasTileEntity(state) ? tileEntityType.create(pos, state) : null;
    }

    public boolean hasTileEntity(BlockState state) {
        return this.tileEntityType != null;
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        onBlockBroken(state, world, pos);
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
        onBlockBroken(state, world, pos);
        super.onBlockExploded(state, world, pos, explosion);
    }

    public void onBlockBroken(BlockState state, BlockGetter world, BlockPos pos) {
        if (hasTileEntity(state)) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof SimpleBlockEntity) {
                SimpleBlockEntity simpleTileEntity = (SimpleBlockEntity) tileEntity;
                simpleTileEntity.onBreak();
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        if (hasTileEntity(state)) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof SimpleBlockEntity) {
                SimpleBlockEntity simpleTileEntity = (SimpleBlockEntity) tileEntity;
                return simpleTileEntity.onUse(player, hand);
            }
        }
        return super.use(state, world, pos, player, hand, ray);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getVisualShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getVisualShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shape != null ? shape : Shapes.block();
    }
}