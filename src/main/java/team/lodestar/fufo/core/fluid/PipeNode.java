package team.lodestar.fufo.core.fluid;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * A "node" in the fluid pipe network is any tile (block) entity that interacts with the network.
 * This includes pipe segments, pumps, valves, machines, etc.
 * Any block entity class that is intended to interact with the network must implement this interface,
 * either directly or by extending <code>PipeNodeBlockEntity</code>. It is highly recommended that <code>PipeNodeBlockEntity</code>
 * be extended wherever possible, to ensure consistent implementation of this interface's methods.
 * 
 * Pipe nodes support non-integer fluid amounts; this is by default implemented by keeping a <code>double</code> value
 * to represent the fractional amount on top of the standard <code>FluidStack</code>. If you write your own implementation
 * for these methods, please ensure that no fluid is inadvertently created or lost, especially through transfers.
 * @author ProfessorLucario
 * @see team.lodestar.fufo.common.fluid.PipeNodeBlockEntity
 */
public interface PipeNode {
	/**
	 * Returns the integer part of the stored fluid. Meant for interfacing with other mods' fluid systems.
	 * @return
	 */
	public FluidStack getStoredFluid();
	
	/**
	 * Adds a fluid to the node. Implementations are expected to handle the cases of attempting to add a type or amount of fluid
	 * that the node cannot handle.
	 * @param fluid The type of fluid to add
	 * @return The amount of fluid not added (for example, if the node would be overfilled, the amount of excess)
	 */
	public double addFluid(Fluid fluid, double amount);
	
	/**
	 * Transfers fluid from this node to another.
	 * @param amount The amount of fluid to transfer
	 * @param dest The node to send to. It is expected (though not technically necessary) that the destination node be in the same network.
	 */
	public void transferFluid(double amount, PipeNode dest);
	
	/**
	 * Some nodes such as pumps have extra things they do with their action that wouldn't be appropriate to put
	 * into transferFluid. This method covers such actions.
	 */
	public default void doExtraAction() {}
	
	/**
	 * Convenience method for getting the true pythagorean distance from this node to another.
	 * @param other The node to measure distance to
	 * @return The resulting distance.
	 */
	public default double getDistance(PipeNode other) {
		return Math.sqrt(getPos().distSqr(other.getPos()));
	}
	
	/**
	 * Returns a list of all other nodes in the network that this node is directly connected to.
	 */
	public List<PipeNode> getConnectedNodes();
	
	/**
	 * Creates a connection between this node and the one at the given position.
	 * @param bp The BlockPos of the node to connect to
	 * @return The result of the connection attempt; true if it was successful.
	 */
	public boolean addConnection(BlockPos bp);
	
	/**
	 * Removes the connection between this node and the one at the given position.
	 * @param bp The BlockPos of the node to disconnect from
	 * @return The result of the disconnection attempt; true if it was successful.
	 */
	public boolean removeConnection(BlockPos bp);
	
	/**
	 * Recalculating the pressure contribution from every source to every node in every network is extremely inefficient,
	 * so instead it is done lazily by caching the contribution and only updating when something relevant in the network changes.
	 * 
	 * @param p The pressure source to poll
	 * @param dir Which side (in/out) of the source we are polling from
	 * @param dist The node's distance to the source
	 */
	public void updateSource(PressureSource p, FlowDir dir, double dist);
	
	/**
	 * Calculates and returns this node's distance to a pressure source. Note that this is "through-the-network" distance,
	 * not absolute pythagorean or taxicab distance. By default this method uses a depth-first recursive algorithm,
	 * though this will likely be changed in future as more efficient methods exist.
	 * @param p The pressure source to calculate the distance to
	 * @param dir Which side of the source to try to reach
	 * @return The calculate distance
	 */
	public double getDistFromSource(PressureSource p, FlowDir dir);
	
	/**
	 * To ensure that we can fetch the location of a PipeNode without it being dependent on the <code>BlockEntity</code> class.
	 * In 99% of cases this should just call <code>BlockEntity.getPos()</code>.
	 */
	public BlockPos getPos();
	
	/**
	 * Returns the "base" pressure of the node. Note that this does NOT include external factors
	 * such as gravity, other nodes, neighbouring pumps, etc
	 * @return
	 */
	public double getPressure();
	
	/**
	 * Returns the amount of fluid stored in this node.
	 * @return
	 */
	public double getFluidAmount();
	
	/**
	 * Some node types such as pumps may want to override this to calculate pressure differently
	 * depending on whether they are pushing or pulling fluid
	 * @param side
	 * @return
	 */
	public default double getPressure(FlowDir side) { return getPressure(); }
	
	/**
	 * Assigns this node to a given network. This is the node-side method which handles the node's cached network ID and network reference.
	 * @param network The network to add this node to
	 * @param reciprocate If this is set to true, the network must run its corresponding method which adds the node to its list of members. This allows for a node to join a network from either the node's side or the network's side without causing infinite loops.
	 * @param recalc If this is set to true, the network's pressures will be recalculated.
	 */
	public void setNetwork(FluidPipeNetwork network, boolean reciprocate, boolean recalc);
	
	/**
	 * Returns the network that this node belongs to.
	 * @return
	 */
	public FluidPipeNetwork getNetwork();
	
	/**
	 * Returns the amount of fluid that this node can store. This method should be overriden.
	 * @return
	 */
	public int getCapacity();
}