package com.sammy.fufo.core.reference;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class FluidStats {
	private FluidStats() {}
	
	public static final double R = 8.314; // J/(mol*K)
	private static final Map<Fluid, FluidInfo> map = new HashMap<>();
	
	public static FluidInfo getInfo(Fluid fluid) {
		return Optional.ofNullable(map.get(fluid)).orElse(map.get(Fluids.WATER));
	}
	
	private static void addInfo(Fluid fluid, double mu, double rho, double fp, double bp) {
		map.put(fluid, new FluidInfo(mu, rho, fp, bp));
	}
	
	// Put physical properties of mod fluids here
	static {
		addInfo(Fluids.WATER, 1000, 8.9e-4, 273.15, 373.15);
		addInfo(Fluids.LAVA, 2850, 28.14, 1273.15, 2000); // lava stats taken from wikipedia
	}
	
	public static class FluidInfo {
		final double mu; // viscosity (kg/(m*s))
		final double rho; // density (kg/m^3)
		final double freezingPoint; // K
		final double boilingPoint; // K
		
		FluidInfo(double m, double r, double f, double b) {
			mu = m;
			rho = r;
			freezingPoint = f;
			boilingPoint = b;
		}
	}
}