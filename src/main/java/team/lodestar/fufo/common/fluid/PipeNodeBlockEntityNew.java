package team.lodestar.fufo.common.fluid;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import team.lodestar.fufo.core.fluid.FlowDir;
import team.lodestar.fufo.core.fluid.FluidPipeNetwork;
import team.lodestar.fufo.core.fluid.PipeNode;
import team.lodestar.fufo.core.fluid.PressureSource;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class PipeNodeBlockEntityNew extends LodestoneBlockEntity implements PipeNode {

	public PipeNodeBlockEntityNew(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		// TODO Auto-generated constructor stub
	}

	@Override
	public FluidStack getStoredFluid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double addFluid(Fluid fluid, double amount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void transferFluid(double amount, PipeNode dest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PipeNode> getConnectedNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addConnection(BlockPos bp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeConnection(BlockPos bp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateSource(PressureSource p, FlowDir dir, double dist) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getDistFromSource(PressureSource p, FlowDir dir) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BlockPos getPos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getPressure() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFluidAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNetwork(FluidPipeNetwork network, boolean reciprocate, boolean recalc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FluidPipeNetwork getNetwork() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPotentialPressure() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRealPressure() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean shouldPropagate() {
		// TODO Auto-generated method stub
		return false;
	}

}
