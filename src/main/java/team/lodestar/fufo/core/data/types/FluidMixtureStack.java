package team.lodestar.fufo.core.data.types;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidMixtureStack extends FluidStack { // this is probably a very bad idea but oh well

	private List<FluidStack> components = new ArrayList<>();
	public FluidMixtureStack(Fluid fluid, int amount) {
		super(fluid, amount);
	}
	
	public void addFluid(Fluid fluid, int amount) {
		components.stream().filter(fs -> fs.getFluid().equals(fluid)).findFirst().ifPresentOrElse(fs -> fs.grow(amount), () -> components.add(new FluidStack(fluid, amount)));
	}
	
	@Override
	public int getAmount() {
		return components.stream().mapToInt(FluidStack::getAmount).sum();
	}
}