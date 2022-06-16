package com.sammy.fufo.core.systems.logistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.tuple.Triple;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.world.registry.FluidPipeNetworkRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.world.ForgeChunkManager;

// I should be able to reuse some ideas from Inventory Link.
public class FluidPipeNetwork {
	public static final double PRESSURE_TRANSFER_COEFF = 0.1; // Must be between 0 and 1
	public static final double DRAIN_COEFF = 0.1;
	public Set<PipeNode> nodes = new HashSet<>(); // may have to be changed to a List
	public Set<BlockPos> nodePositions = new HashSet<>();
	
	private int id;
	private Level world; // In this household we call it a World
	private boolean loaded = false;
	
	public FluidPipeNetwork(Level world) {
		this.world = world;
		id = makeID(); // TODO: Make a new system that doesn't risk random conflicts
		FluidPipeNetworkRegistry.getRegistry(world).addNetwork(this);
		FufoMod.LOGGER.info("Creating new network with ID " + id);
//		FluidPipeNetworkRegistry.INSTANCE.addNetwork(this);
	}
	
	// Called when loading a previously-existing pipe network from NBT
	public FluidPipeNetwork(Level world, CompoundTag nbt) {
		if (world == null) throw new NullPointerException("Attempting to build a network in a null world!");
//		Minecraft.getInstance().mouseHandler.releaseMouse();
		this.world = world;
		id = nbt.getInt("id");
		for (Tag t : nbt.getList("nodes", Tag.TAG_COMPOUND)) {
			CompoundTag node = (CompoundTag)t;
			BlockEntity te = world.getBlockEntity(BlockPos.of(node.getLong("pos")));
			if (te instanceof PipeNode p) {
				addNode(p, true);
			}
		}
		forceLoadNetwork();
	}
	
	// Make a random network ID that is guaranteed to be nonzero and not already used
	private int makeID() {
		int id;
		do {
			id = FufoMod.RANDOM.nextInt();
		} while (id == 0 || FluidPipeNetworkRegistry.getRegistry(world).idList().contains(id));
		return id;
	}
	
	public void addNode(PipeNode node, boolean reciprocate) {
		nodes.add(node);
		nodePositions.add(node.getPos());
		if (reciprocate) node.setNetwork(this, false);
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
	
	public void tick() {
		
		// First calculate pressures
		// We need some sort of reference frame
		
		
		List<Triple<PipeNode, PipeNode, Double>> transfers = new ArrayList<>(); // Triple members, in order: Source, destination, amount
		// Calculate amount to transfer
		for (PipeNode node : nodes) {
			List<PipeNode> connections = node.getConnectedNodes();
			for (PipeNode other : connections) {
				if (!node.getStoredFluid().isEmpty()) {
					Triple<PipeNode, PipeNode, Double> t = Triple.of(node, other, Math.max(0, (node.getBasePressure() - other.getBasePressure()) * PRESSURE_TRANSFER_COEFF));
					transfers.add(t);
				}
			}
		}
		
		// Transfer the fluid
		for (Triple<PipeNode, PipeNode, Double> t : transfers) {
			t.getLeft().transferFluid(t.getRight(), t.getMiddle());
		}
	}
	
	public boolean contains(PipeNode node) {
		return nodes.contains(node);
	}
	
	public void mergeWith(FluidPipeNetwork other) {
		other.nodes.forEach(node -> node.setNetwork(this, true));
	}
	
	private FluidPipeNetwork subnetHelper(PipeNode current, FluidPipeNetwork network) {
		for (PipeNode p : current.getConnectedNodes()) {
			if (!network.contains(p)) {
				network.addNode(p, true);
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
}