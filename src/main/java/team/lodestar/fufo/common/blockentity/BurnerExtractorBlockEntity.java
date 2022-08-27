package team.lodestar.fufo.common.blockentity;

import team.lodestar.fufo.registry.common.FufoBlockEntities;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BurnerExtractorBlockEntity extends LodestoneBlockEntity {
    public BurnerExtractorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
