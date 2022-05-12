package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.core.setup.content.block.BlockEntityRegistry;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.helpers.RenderHelper;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class AnchorBlockEntity extends OrtusBlockEntity {

    public static List<AnchorBlockEntity> NEARBY_ANCHORS = new ArrayList<AnchorBlockEntity>(2);

    public AnchorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public AnchorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ANCHOR.get(), pos, state);
    }

    @Override
    public void onPlace(LivingEntity placer, ItemStack stack) {
        if(!level.isClientSide) {
            NEARBY_ANCHORS = BlockHelper.getBlockEntities(AnchorBlockEntity.class, level, this.getBlockPos(), 4);
            NEARBY_ANCHORS.removeIf(anchor -> anchor == this);
            placer.sendMessage(Component.nullToEmpty("Found " + NEARBY_ANCHORS.size() + " anchors"), placer.getUUID());
        }
    }
}
