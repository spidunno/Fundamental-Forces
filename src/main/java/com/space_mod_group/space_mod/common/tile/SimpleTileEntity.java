package com.space_mod_group.space_mod.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class SimpleTileEntity extends TileEntity
{
    public SimpleTileEntity(TileEntityType<?> type)
    {
        super(type);
    }
    public void onBreak(BlockState state, BlockPos pos) {
        invalidateCaps();
    }

    public ActionResultType onUse(BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
        return ActionResultType.PASS;
    }
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
    {
        super.onDataPacket(net, packet);
        loadData(packet.getTag());
    }
    @Override
    public CompoundNBT getUpdateTag()
    {
        return save(new CompoundNBT());
    }
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        CompoundNBT nbt = new CompoundNBT();
        save(nbt);
        return new SUpdateTileEntityPacket(worldPosition, 0, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound)
    {
        super.save(compound);
        return saveData(compound);
    }
    public CompoundNBT saveData(CompoundNBT compound)
    {
        return compound;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound)
    {
        super.load(state, compound);
        loadData(compound);
    }
    public void loadData(CompoundNBT compound)
    {

    }
}