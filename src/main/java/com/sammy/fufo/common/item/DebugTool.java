package com.sammy.fufo.common.item;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.blockentity.PipeNodeBlockEntity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DebugTool extends Item {

	public DebugTool(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context)
	{
		BlockEntity tileentity = context.getLevel().getBlockEntity(context.getClickedPos());
		Player ep = context.getPlayer();
		if (tileentity instanceof PipeNodeBlockEntity node) {
			if (ep.isShiftKeyDown()) {
				FufoMod.LOGGER.info(node.getNetwork() == null ? "No network" : node.getNetwork().getID());
			}
			else {
				FufoMod.LOGGER.info(String.format("%s mb of %s", node.getStoredFluid().getAmount(), node.getStoredFluid().getFluid()));
			}
//			else context.getPlayer().sendMessage(Component.nullToEmpty(String.format("%s neighbours", node.countNeighbours())), context.getPlayer().getUUID());
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}