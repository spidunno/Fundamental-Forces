package team.lodestar.fufo.common.fluid.sealed_barrel;

import team.lodestar.fufo.common.fluid.PipeNodeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

// Still a WIP
// How long has this been on the backburner for?
public class SealedBarrelBlockEntity extends PipeNodeBlockEntity {

	public SealedBarrelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public int getCapacity() {
		return 8000;
	}

}