package team.lodestar.fufo.core.fluid;

import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * 
 * @author ProfessorLucario
 *
 * A DestinationPipeNode is a node that accepts fluid. Examples of these are tanks, machines, and open pipes.
 * This is in contrast to regular pipes which just transfer fluid but internally don't store any.
 * 
 * The fluid pipe network keeps track of all destinations for the purpose of calculating pressure.
 * It then creates a linear drop between each source's given pressure and each destination's actual
 * (not potential) pressure, and then superposes everything together to get the final node pressures.
 */
public interface DestinationPipeNode extends PipeNode {
	
	/**
	 * Destination nodes have two pressures, potential and actual. The potential pressure of a node is
	 * the pressure determined in accordance with the rest of the network, while the real pressure
	 * is determined by factors such as the amount of fluid stored.
	 * If the two numbers match, the node is in equilibrium.
	 * @return
	 */
	public double getPotentialPressure();
	public double getRealPressure();
	
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
	 * Returns the amount of fluid stored in this node.
	 * @return
	 */
	public double getFluidAmount();
	
	/**
	 * Returns the amount of fluid that this node can store. This method should be overriden.
	 * @return
	 */
	public int getCapacity();
}
