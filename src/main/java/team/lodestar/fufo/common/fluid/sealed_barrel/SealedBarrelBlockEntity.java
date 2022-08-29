package team.lodestar.fufo.common.fluid.sealed_barrel;

import net.minecraftforge.fluids.capability.IFluidHandler;
import org.apache.commons.lang3.tuple.Triple;
import team.lodestar.fufo.common.fluid.PipeNodeBlockEntity;
import team.lodestar.fufo.core.fluid.FlowDir;
import team.lodestar.fufo.core.fluid.FluidPipeNetwork;
import team.lodestar.fufo.core.fluid.PipeNode;
import team.lodestar.fufo.core.fluid.PressureSource;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.ArrayList;
import java.util.List;

// Still a WIP
// How long has this been on the backburner for?
public class SealedBarrelBlockEntity extends PipeNodeBlockEntity implements PipeNode {

	public SealedBarrelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public int getCapacity() {
		return 8000;
	}

}