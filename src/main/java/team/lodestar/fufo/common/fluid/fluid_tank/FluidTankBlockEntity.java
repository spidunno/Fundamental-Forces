package team.lodestar.fufo.common.fluid.fluid_tank;

import team.lodestar.fufo.common.fluid.PipeNodeBlockEntity;
import team.lodestar.fufo.core.fluid.FlowDir;
import team.lodestar.fufo.core.fluid.FluidStats;
import team.lodestar.fufo.core.fluid.PipeNode;
import team.lodestar.fufo.core.fluid.PressureSource;
import team.lodestar.fufo.registry.common.FufoBlockEntities;
import team.lodestar.fufo.unsorted.ForcesThatAreActuallyFundamental;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

// Thinking about it more, maybe all nodes other than basic PipeNodeBlockEntity instances should be pressure sources
public class FluidTankBlockEntity extends PipeNodeBlockEntity implements PressureSource {
	
	private static final int MB_PER_BLOCK = 10000; // Fluid-shrinking technology is omnipresent and I'm not questioning it
	private static final double AREA = 1.0; // Cross-sectional area of the tank in square meters
	
    public FluidTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void onNeighborUpdate(BlockState state, BlockPos pos, BlockPos neighbor) {
        super.onNeighborUpdate(state, pos, neighbor);
    }
    
    @Override
    public int getCapacity() {
    	return MB_PER_BLOCK;
    }
    
    // rho*g*h; returns amount in pascals
    private double getInternalPressure() {
    	return ForcesThatAreActuallyFundamental.g * FluidStats.getInfo(fluidType).rho * (getFluidAmount() / AREA / 1000.0);
    }

	@Override
	public List<PipeNode> getConnectedNodes(FlowDir dir) {
		// TODO Auto-generated method stub
		if (dir == FlowDir.IN) return List.of();
		else return getConnectedNodes();
	}

	@Override
	public boolean shouldPropagate() {
		return this.getFluidAmount() / this.getCapacity() > 0.95;
	}
	
	@Override
	public int getForce(FlowDir dir) {
		return (int)getInternalPressure();
	}
}