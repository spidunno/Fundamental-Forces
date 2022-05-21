package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.core.setup.content.block.BlockEntityRegistry;
import com.sammy.ortus.systems.block.OrtusEntityBlock;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UITestBlockEntity extends OrtusBlockEntity {
    public boolean toggle = false;
    public UITestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public UITestBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.UI_TEST_BLOCK.get(),pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        toggle = !toggle;
        return InteractionResult.SUCCESS;
    }
}
