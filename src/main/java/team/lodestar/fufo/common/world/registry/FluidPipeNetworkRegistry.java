package team.lodestar.fufo.common.world.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.core.fluid.FluidPipeNetwork;
import team.lodestar.fufo.core.fluid.PipeNode;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;

// This is a lazy loading implementation that only loads individual networks from NBT as needed
public class FluidPipeNetworkRegistry {
	
	private Level level;
	private Map<Integer, FluidPipeNetwork> networks = new HashMap<>();
	private static Map<Level, FluidPipeNetworkRegistry> INSTANCES = new HashMap<>();
	private ListTag nbt;
	
	// Client-side
	private FluidPipeNetworkRegistry(Level world, ListTag nbt) {
		if (world == null) throw new NullPointerException("Attempting to construct a registry for a null world!");
		this.level = world;
		this.nbt = nbt;
	}
	
	public static FluidPipeNetworkRegistry getRegistry(Level world) {
		if (!INSTANCES.containsKey(world)) INSTANCES.put(world, new FluidPipeNetworkRegistry(world, new ListTag()));
		return INSTANCES.get(world);
	}
	
	public ListTag serialize() {
		ListTag nbt = new ListTag();
		for (int id : networks.keySet()) {
			CompoundTag net = new CompoundTag();
			net.putInt("id", id);
			net.putInt("numNodes", networks.get(id).getNodes().size());
			ListTag nodes = new ListTag();
			for (PipeNode p : networks.get(id).getNodes()) {
				CompoundTag pos = new CompoundTag();
				pos.putLong("pos", p.getPos().asLong());
				nodes.add(pos);
			}
			net.put("nodes", nodes);
			nbt.add(net);
		}
		return nbt;
	}
	
	public static void load(Level level, ListTag tag) {
//		if (INSTANCES.containsKey(level)) throw new IllegalArgumentException("Tried to load in an already-loaded registry!");
		FufoMod.LOGGER.info("Loading network registry with tag " + tag);
		INSTANCES.put(level, new FluidPipeNetworkRegistry(level, tag));
	}
	
	public boolean contains(FluidPipeNetwork network) {
		return networks.containsKey(network.getID());
	}
	
	public void addNetwork(FluidPipeNetwork network) {
		networks.put(network.getID(), network);
		FufoMod.LOGGER.info("Adding network " + network.getID());
	}
	
	public void removeNetwork(FluidPipeNetwork network) {
		networks.remove(network.getID());
	}
	
	public Set<Integer> idList() {
		return networks.keySet();
	}
	
	public FluidPipeNetwork getOrLoadNetwork(int id) {
		if (!networks.containsKey(id)) {
			for (Tag tag : nbt) {
				CompoundTag net = (CompoundTag)tag;
				if (net.getInt("id") == id) {
					networks.put(id, new FluidPipeNetwork(level, net));
					break;
				}
			}
		}
		return networks.get(id);
	}
	
//	public FluidPipeNetwork getOrCreateNetwork(int id) {
//		if (!networks.containsKey(id)) networks.put(id, new FluidPipeNetwork(level));
//		return networks.get(id);
//	}

	public void tickNetworks() {
		for (FluidPipeNetwork network : networks.values()) {
			network.tick();
		}
	}
}