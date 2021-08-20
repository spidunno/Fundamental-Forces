package com.space_mod_group.space_mod.common.block;

import com.space_mod_group.space_mod.common.tile.SimpleTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SimpleBlock extends Block
{
    protected VoxelShape shape = null;
    protected TileEntityType<? extends TileEntity> tileEntityType = null;

    public SimpleBlock(Properties properties)
    {
        super(properties);
    }

    public SimpleBlock setTile(TileEntityType<? extends TileEntity> type)
    {
        this.tileEntityType = type;
        return this;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return this.tileEntityType != null;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return tileEntityType.create();
    }


    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        onBlockBroken(state, world, pos);
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion)
    {
        onBlockBroken(state, world, pos);
        super.onBlockExploded(state, world, pos, explosion);
    }

    public void onBlockBroken(BlockState state, IBlockReader world, BlockPos pos)
    {
        if (hasTileEntity(state))
        {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof SimpleTileEntity)
            {
                SimpleTileEntity simpleTileEntity = (SimpleTileEntity) tileEntity;
                simpleTileEntity.onBreak(state, pos);
            }
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray)
    {
        if (hasTileEntity(state))
        {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof SimpleTileEntity)
            {
                SimpleTileEntity simpleTileEntity = (SimpleTileEntity) tileEntity;
                return simpleTileEntity.onUse(state, pos, player, hand);
            }
        }
        return super.use(state, world, pos, player, hand, ray);
    }
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
    {
        return getVisualShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
    {
        return getVisualShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
    {
        return shape != null ? shape : VoxelShapes.block();
    }
}