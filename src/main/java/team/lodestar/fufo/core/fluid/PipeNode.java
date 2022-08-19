package team.lodestar.fufo.core.fluid;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface PipeNode {
	public FluidStack getStoredFluid();
	
	/**
	 * Add a fluid to the node.
	 * @param stack
	 * @return The amount of fluid not added
	 */
	public double addFluid(Fluid fluid, double amount);
	
	public void transferFluid(double amount, PipeNode dest);
	
	/**
	 * Some nodes such as pumps have extra things they do with their action that wouldn't be appropriate to put
	 * into transferFluid. This method covers such actions.
	 */
	public default void doExtraAction() {}
	
	public default double getDistance(PipeNode other) {
		return Math.sqrt(getPos().distSqr(other.getPos()));
	}
	
	public List<PipeNode> getConnectedNodes();
	
	public boolean addConnection(BlockPos bp);
	
	public boolean removeConnection(BlockPos bp);
	
	public void updateSource(PressureSource p, FlowDir dir, double dist);
	
	public double getDistFromSource(PressureSource p, FlowDir dir);
	
	public BlockPos getPos();
	
	/**
	 * Returns the "base" pressure of the node. Note that this does NOT include external factors
	 * such as gravity, other nodes, neighbouring pumps, etc
	 * @return
	 */
	public double getPressure();
	
	public double getFluidAmount();
	
	/**
	 * Some node types such as pumps may want to override this to calculate pressure differently
	 * depending on whether they are pushing or pulling fluid
	 * @param side
	 * @return
	 */
	public default double getPressure(FlowDir side) { return getPressure(); }
	
	public void setNetwork(FluidPipeNetwork network, boolean reciprocate, boolean recalc);
	
	public FluidPipeNetwork getNetwork();
	
	public void setTarget(BlockPos target);
	
	public int getCapacity();
}