package team.lodestar.fufo.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.fufo.client.ui.Vector2;
import team.lodestar.fufo.client.ui.component.FlexBox;
import team.lodestar.fufo.client.ui.component.ScrollBox;
import team.lodestar.fufo.client.ui.component.TextComponent;
import team.lodestar.fufo.client.ui.constraint.PercentageConstraint;
import team.lodestar.fufo.client.ui.constraint.PixelConstraint;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import javax.annotation.Nonnull;
import java.awt.*;

public class ArrayBlockEntity extends LodestoneBlockEntity {
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

	@Override
	public InteractionResult onUse(@Nonnull Player player, @Nonnull InteractionHand hand) {
		return InteractionResult.SUCCESS;
	}
}