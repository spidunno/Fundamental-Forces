package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.client.ui.programming.ProgrammingScreen;
import com.sammy.fufo.client.ui.Vector2;
import com.sammy.fufo.client.ui.component.*;
import com.sammy.fufo.client.ui.component.TextComponent;
import com.sammy.fufo.client.ui.constraint.PercentageConstraint;
import com.sammy.fufo.client.ui.constraint.PixelConstraint;
import com.sammy.fufo.core.registratation.BlockEntityRegistrate;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;

public class ArrayBlockEntity extends OrtusBlockEntity {
    public long previousTime = -1;
    public FlexBox box;

    public int uiWidth = 24;
    public int uiHeight = 32;

    public ArrayBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {

        super(type, pos, state);

        box = new FlexBox()
                .withWidth(new PixelConstraint(uiWidth))
                .withHeight(new PixelConstraint(uiHeight))
                .padded(new Vector2(1, 1))
                .withColor(Color.DARK_GRAY)
                .withAlignmentAlongAxis(FlexBox.Alignment.CENTER)
                .withAlignmentAgainstAxis(FlexBox.Alignment.CENTER)
                .withAxis(FlexBox.Axis.VERTICAL)
                .withOpacity(0.5f)
                .withSpacing(1);

        double positionSpeed = 3.5;
        double sizeSpeed = 3.5;

        box
                .withChild(
                        new ScrollBox()
                                .withWidth(new PercentageConstraint(0.5))
                                .withHeight(new PercentageConstraint(0.5))
                                .withColor(Color.RED)
                                .withChild(
                                        new TextComponent("Hello World", 1f)
                                )
                );


    }

    public ArrayBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistrate.CRUDE_ARRAY.get(), pos, state);

    }

    @Override
    public void tick() {
        // get all players within 5 blocks
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {

        return InteractionResult.SUCCESS;
    }
}
