package team.lodestar.fufo.core.fluid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.fluid.FluidPipeNetworkRegistry;
import team.lodestar.fufo.unsorted.ForcesThatAreActuallyFundamental;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.world.ForgeChunkManager;

/**
 * FluidPipeNetwork handles the pressure system, fluid transfer, etc.
 * @see team.lodestar.fufo.common.fluid.PipeNodeBlockEntity
 * @author ProfessorLucario
 *
 */
public class FluidPipeNetwork {
	public static boolean MANUAL_TICKING = false; // Debug only, remove before release
	public static final double PRESSURE_TRANSFER_COEFF = 0.0005; // Must be between 0 and 1
	public static final double DRAIN_COEFF = 0.1;
	public Set<PipeNode> nodes = new HashSet<>(); // may have to be changed to a List
	public Set<BlockPos> nodePositions = new HashSet<>();
	private List<PressureSource> pressureSources = new ArrayList<>();
	
	private int numNodes; // This number is NOT changed during runtime. Only during save/load so that the network knows when all nodes have been loaded
	private int numLoaded;
	private int id;
	private Level world; // In this household we call it a World
	private boolean loaded = false;
	
	public FluidPipeNetwork(Level world) {
		this.world = world;
		id = makeID();
		FluidPipeNetworkRegistry.getRegistry(world).addNetwork(this);
		FufoMod.LOGGER.info("Creating new network with ID " + id);
	}
	
	public static void logIfManual(Object o) {
		if (MANUAL_TICKING) FufoMod.LOGGER.info(o.toString());
	}
	
	// Called when loading a previously-existing pipe network from NBT
	public FluidPipeNetwork(Level world, CompoundTag nbt) {
		if (world == null) throw new NullPointerException("Attempting to build a network in a null world!");
		this.world = world;
		id = nbt.getInt("id");
		numNodes = nbt.getInt("numNodes");
		for (Tag t : nbt.getList("nodes", Tag.TAG_COMPOUND)) {
			CompoundTag node = (CompoundTag)t;
			BlockEntity te = world.getBlockEntity(BlockPos.of(node.getLong("pos")));
			if (te instanceof PipeNode p) {
				addNode(p, true, false);
				if (p instanceof PressureSource s) {
					addSource(s, false);
				}
			}
		}
		forceLoadNetwork();
		FufoMod.LOGGER.info("Successfully loaded network " + id + " from memory");
	}

	// Called from PipeNodeBlockEntity if it's the last one in a network to load
	// in order to ensure that recalcPressure does not run until all nodes are loaded
	public void finishLoading() {
		recalcPressure();
	}
	
	public void loadNode() {
		numLoaded++;
	}
	
	public int numLoadedNodes() {
		return numLoaded;
	}
	public int numSavedNodes() {
		return numNodes;
	}

	// Make a random network ID that is guaranteed to be nonzero and not already used
	private int makeID() {
		int id;
		do {
			id = FufoMod.RANDOM.nextInt();
		} while (id == 0 || FluidPipeNetworkRegistry.getRegistry(world).idList().contains(id));
		return id;
	}
	
	public void addNode(PipeNode node, boolean reciprocate, boolean calcPressure) {
		nodes.add(node);
		nodePositions.add(node.getPos());

		if (calcPressure) recalcPressure();
		if (reciprocate) node.setNetwork(this, false, calcPressure);
	}
	
	public void removeNode(PipeNode node) {
		nodes.remove(node);
		nodePositions.remove(node.getPos());
		recalcPressure();
	}
	
	// Each node just needs to keep its own distance from each pressure source.
	// This is not an efficient algorithm but it doesn't run very often so should be ok
	// Y'know, as long as we can avoid infinite recursion
	private void recalcPressureHelper(PressureSource source, FlowDir dir, PipeNode node, Set<PipeNode> visited, double distance) {
		node.updateSource(source, dir, distance);
		for (PipeNode next : node.getConnectedNodes()) {
			if (next == source) continue;
			double nextDist = distance + Math.sqrt(node.getPos().distSqr(next.getPos()));
			if (!visited.contains(next) || nextDist < next.getDistFromSource(source, dir)) {
				visited.add(next);
				recalcPressureHelper(source, dir, next, visited, nextDist);
			}
		}
	}
	
	private void recalcPressure() {

		for (PressureSource p : pressureSources) {
			
			PipeNode in = p.getConnection(FlowDir.IN);
			PipeNode out = p.getConnection(FlowDir.OUT);
			if (in != null && out != null) { // Ignore contributions from pumps/etc that aren't fully connected
				recalcPressureHelper(p, FlowDir.IN, in, new HashSet<PipeNode>(), Math.sqrt(p.getPos().distSqr(in.getPos())));
				recalcPressureHelper(p, FlowDir.OUT, out, new HashSet<PipeNode>(), Math.sqrt(p.getPos().distSqr(out.getPos())));
			}
		}
	}
	
	public void addSource(PressureSource source, boolean recalc) {
		pressureSources.add(source);
		if (recalc) recalcPressure();
	}
	
	public void removeSource(PressureSource source, boolean recalc) {
		pressureSources.removeIf(s -> s.getPos().equals(source.getPos()));
		if (recalc) recalcPressure();
	}

	public int getID() {
		return id;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	
	public void forceLoadNetwork() {
		if (world != null && !world.isClientSide) {
			for (BlockPos bp : nodePositions) {
				LevelChunk chunk = world.getChunkAt(bp);
				ForgeChunkManager.forceChunk((ServerLevel)world, FufoMod.FUFO, bp, chunk.getPos().x, chunk.getPos().z, true, true);
				nodes.add((PipeNode)world.getBlockEntity(bp));
			}
		}
		this.loaded = true;
	}
	
	public Set<PipeNode> getNodes() {
		return nodes;
	}
	
	private List<Pair<PipeNode, Double>> calcTransfers(PipeNode node, List<PipeNode> connections) {
		List<Pair<PipeNode, Double>> intermediateTransfers = new ArrayList<>();
		for (PipeNode other : connections) {

			if (!node.getStoredFluid().isEmpty()) {
				// The pressure difference required to overcome a height difference is equal to the fluid's density times gravity times the change in height
				int dy = other.getPos().getY() - node.getPos().getY();
				double rho = FluidStats.getInfo(node.getStoredFluid().getFluid()).rho;
				double g = ForcesThatAreActuallyFundamental.g;
				double targetPressure;
				if (other instanceof SidedNode sided && sided.getConnectedNodes(FlowDir.OUT).contains(node)) targetPressure = other.getPressure(FlowDir.OUT);
				else targetPressure = other.getPressure(FlowDir.IN);
				double adjustedPressureDifference = node.getPressure(FlowDir.OUT) - targetPressure - (node.getFluidAmount()*rho*g*dy)/1000;  

					logIfManual(String.format("TRANSFER from %s (%s) to %s (%s)", node, node.getPressure(FlowDir.OUT), other, other.getPressure(FlowDir.IN)));
					logIfManual(String.format("Gravitational pressure difference = %s", node.getFluidAmount()*dy*rho*g/1000));
					logIfManual(String.format("Amount to transfer = %s", adjustedPressureDifference));
				
				intermediateTransfers.add(Pair.of(other, Math.max(0, adjustedPressureDifference) * PRESSURE_TRANSFER_COEFF));
				
//					Triple<PipeNode, PipeNode, Double> t = Triple.of(node, other, Math.max(0, adjustedPressureDifference) * PRESSURE_TRANSFER_COEFF);
//					transfers.add(t);
			}
		}
		return intermediateTransfers;
	}
	
	public void tick() {
		if (MANUAL_TICKING) FufoMod.LOGGER.info("Ticking network");
		List<Triple<PipeNode, PipeNode, Double>> transfers = new ArrayList<>(); // Triple members, in order: Source, destination, amount
		// Calculate amount to transfer
		for (PipeNode node : nodes) {
			List<Pair<PipeNode, Double>> intermediateTransfers = new ArrayList<>();
			if (node instanceof SidedNode sn) {
				for (FlowDir dir: FlowDir.values()) {
					intermediateTransfers.addAll(calcTransfers(node, sn.getConnectedNodes(dir)));
				}
			}
			else {
				intermediateTransfers.addAll(calcTransfers(node, node.getConnectedNodes()));
			}
			logIfManual(intermediateTransfers);
			// If the total amount of fluid to transfer out of a node would be greater than the amount of fluid it actually has,
			// ensure a correct split
			double sum = intermediateTransfers.stream().mapToDouble(p -> p.getRight()).sum();
			double ratio = Math.min(node.getFluidAmount()/sum, 1);
			for (int i=0; i<intermediateTransfers.size(); i++) {
				Pair<PipeNode, Double> p = intermediateTransfers.get(i);
				transfers.add(Triple.of(node, p.getLeft(), p.getRight() * ratio));
			}
		}
		logIfManual(transfers);
		// Transfer the fluid
		for (Triple<PipeNode, PipeNode, Double> t : transfers) {
			t.getLeft().transferFluid(t.getRight(), t.getMiddle());
		}
		
		nodes.forEach(n -> n.doExtraAction());
	}
	
	public boolean contains(PipeNode node) {
		return nodes.contains(node);
	}
	
	public void mergeWith(FluidPipeNetwork other) {
		other.nodes.forEach(node -> node.setNetwork(this, true, false));
		recalcPressure();
	}
	
	private FluidPipeNetwork subnetHelper(PipeNode current, FluidPipeNetwork network) {
		for (PipeNode p : current.getConnectedNodes()) {
			if (!network.contains(p)) {
				network.addNode(p, true, true);
				return subnetHelper(p, network);
			}
		}
		return network;
	}
	
	private FluidPipeNetwork makeSubnet(PipeNode base) {
		return subnetHelper(base, new FluidPipeNetwork(world));
	}
	
	// Worst case scenario: A destroyed node with n connections splits the network into n subnets
	// Regardless, the "original" network is destroyed
	public Set<FluidPipeNetwork> splitNetwork(List<PipeNode> nodes) {
		Set<FluidPipeNetwork> networks = new HashSet<>();
		for (int i=0; i<nodes.size(); i++) { // Do manual iteration in order to avoid CMEs
			PipeNode p = nodes.get(i);
			if (p.getNetwork() == this) networks.add(makeSubnet(p));
		}
		FluidPipeNetworkRegistry.getRegistry((ServerLevel)world).removeNetwork(this);
		return networks;
	}

	public String getInfo() {
		StringBuilder builder = new StringBuilder();
		builder.append("Network ID: " + id +"\n");
		builder.append("Pressure sources:\n");
		for (PressureSource p : pressureSources) {
			builder.append(p.toString() + "\n");
		}
		builder.append("Nodes:\n");
		for (PipeNode p : nodes) {
			builder.append(p.toString() + "\n");
		}
		return builder.toString();
	}

	

	
}