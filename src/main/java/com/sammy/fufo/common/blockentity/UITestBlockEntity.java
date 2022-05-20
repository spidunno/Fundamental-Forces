package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.core.index.content.block.BlockEntityRegistrate;
import com.sammy.ortus.systems.block.OrtusEntityBlock;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UITestBlockEntity extends OrtusBlockEntity {
    public UITestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public UITestBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistrate.UI_TEST_BLOCK.get(),pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {

        return super.onUse(player, hand);
    }
}
