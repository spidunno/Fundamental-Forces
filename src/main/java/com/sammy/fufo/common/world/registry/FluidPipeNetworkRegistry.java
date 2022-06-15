package com.sammy.fufo.common.world.registry;

import java.util.HashMap;
import java.util.Map;

import com.sammy.fufo.core.systems.logistics.FluidPipeNetwork;

import net.minecraft.world.level.Level;

public class FluidPipeNetworkRegistry {

	private static Map<Level, FluidPipeNetworkRegistry> INSTANCES = new HashMap<>();
	private Map<Integer, FluidPipeNetwork> networks = new HashMap<>();
	
	public static FluidPipeNetworkRegistry getRegistry(Level level) {
		return INSTANCES.get(level);
	}
}