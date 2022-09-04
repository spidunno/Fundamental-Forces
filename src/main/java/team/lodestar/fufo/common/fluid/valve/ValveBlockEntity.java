package team.lodestar.fufo.common.fluid.valve;

import team.lodestar.fufo.common.fluid.PipeNodeBlockEntity;
import team.lodestar.fufo.core.fluid.FlowDir;
import team.lodestar.fufo.core.fluid.PipeNode;
import team.lodestar.fufo.core.fluid.PressureSource;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ValveBlockEntity extends PipeNodeBlockEntity implements PressureSource {

	private boolean isOpen;
	public ValveBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PipeNode getConnection(FlowDir dir) {
		return null;
	}

	@Override
	public int getForce(FlowDir dir) {
		if (isOpen) return 0;
		else return (int)(dir == FlowDir.IN ? this.getPressure() : -this.getPressure());
	}

	@Override
	public List<PipeNode> getConnectedNodes(FlowDir dir) {
		// TODO Auto-generated method stub
		return null;
	}

}
