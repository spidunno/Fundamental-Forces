package team.lodestar.fufo.common.fluid.pump;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.fluid.PipeNodeBlockEntity;
import team.lodestar.fufo.core.fluid.FlowDir;
import team.lodestar.fufo.core.fluid.PipeNode;
import team.lodestar.fufo.core.fluid.PressureSource;

import org.apache.commons.lang3.tuple.Triple;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

// Pumps are pipe nodes with the following additional rules:
// 1. They have exactly two connections that are identifiable as the front and the back
// 2. They depressurize the "back" connection and pressurize the "front" connection
// 3. They force fluid to move from the back to the front, ignoring pressure rules
// (4. They must have a pipe node block directly in front of and behind them)

public class PumpBlockEntity extends PipeNodeBlockEntity implements PressureSource {

	private PipeNode back;
	private PipeNode front;
	private double force;
	
	public PumpBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		force = 2000; // for testing
	}

	@Override
	public void onPlace(LivingEntity placer, ItemStack stack) {
		super.onPlace(placer, stack);
//		BlockPos prevPos = PipeBuilderAssistant.INSTANCE.prevAnchorPos;
	}
	
	@Override
	public boolean addConnection(BlockPos bp) {
//		Minecraft.getInstance().mouseHandler.releaseMouse();
		if (level.getBlockEntity(bp) instanceof PipeNode other) {
			if (back == null) {
				back = other;
				backPos = bp;
			}
			else if (front == null) {
				front = other;
				frontPos = bp;
			}
			else return false; // if back and front are both spoken for reject the connection
			nearbyAnchorPositions.add(bp);
			if (getNetwork() == null) setNetwork(other.getNetwork(), false, true);
			else getNetwork().mergeWith(other.getNetwork());
			setChanged();
			return true;
		}
		return false;
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		if (backPos != null) back = (PipeNode)level.getBlockEntity(backPos);
		if (frontPos != null) front = (PipeNode)level.getBlockEntity(frontPos);
		FufoMod.LOGGER.info("Successfully loaded back and front!");
	}
	
	private void flip() {
		PipeNode temp = back;
		back = front;
		front = temp;
		backPos = back.getPos();
		frontPos = front.getPos();
	}
	
	private BlockPos backPos;
	private BlockPos frontPos;
	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		if (pTag.contains("back")) backPos = BlockPos.of(pTag.getLong("back"));
		if (pTag.contains("front")) frontPos = BlockPos.of(pTag.getLong("front"));
//		FufoMod.LOGGER.info(String.format("Successfully loaded positions %s and %s from NBT", backPos, frontPos));
	}
	
	@Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (backPos != null) pTag.putLong("back", backPos.asLong()); 
        if (frontPos != null) pTag.putLong("front", frontPos.asLong());
    }
	
	@Override
	public double getPressure() {
		FufoMod.LOGGER.error("Calling the wrong getPressure method!");
		Thread.dumpStack();
		return force;
	}
	
	@Override
	public double getPressure(FlowDir dir) {
		double p = (dir == FlowDir.OUT ? force : -force);
//		FufoMod.LOGGER.info(String.format("Pressure at %s from direction %s: %s", this, dir, p));
		return p;
	}

	@Override
	public int getCapacity() {
		return 100;
	}


	@Override
	public PipeNode getConnection(FlowDir dir) {
		// TODO Auto-generated method stub
		return (dir == FlowDir.IN ? back : front);
	}

	@Override
	public int getForce(FlowDir dir) {
		// TODO Auto-generated method stub
		return (int)(dir == FlowDir.OUT ? force : -force);
	}
	
	@Override
	public String getDebugMessage(boolean sneak) {
		String msg = super.getDebugMessage(sneak);
		String io = String.format("In: %s / Out: %s\n", backPos, frontPos);
		return msg + "\n" + io;
	}
	
	@Override
	public void onDevTool(UseOnContext ctx) {
		flip();
	}
}
