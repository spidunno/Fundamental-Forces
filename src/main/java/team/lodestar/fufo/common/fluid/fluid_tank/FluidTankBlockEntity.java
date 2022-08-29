package team.lodestar.fufo.common.fluid.fluid_tank;

import team.lodestar.fufo.registry.common.FufoBlockEntities;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FluidTankBlockEntity extends LodestoneBlockEntity {
    public FluidTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void onNeighborUpdate(BlockState state, BlockPos pos, BlockPos neighbor) {
        super.onNeighborUpdate(state, pos, neighbor);
    }
}