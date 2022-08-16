package team.lodestar.fufo.common.blockentity;

import java.util.ArrayList;

import team.lodestar.fufo.core.fluid.FluidPipeNetwork;
import team.lodestar.fufo.core.fluid.PipeNode;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

public abstract class AbstractPipeNodeBlockEntityImpl extends LodestoneBlockEntity implements PipeNode {

	private static final int RANGE = 10;

    public ArrayList<BlockPos> nearbyAnchorPositions = new ArrayList<>();
    
    private FluidStack fluid = FluidStack.EMPTY;
    
    private double partialFill = 0.0;
    private boolean isOpen = false;
    private FluidPipeNetwork network;
    private int networkID;
    private BlockPos target;
	public AbstractPipeNodeBlockEntityImpl(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
}