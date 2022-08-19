package team.lodestar.fufo.common.block;

import team.lodestar.fufo.common.blockentity.OrbBlockEntity;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OrbBlock<T extends OrbBlockEntity> extends LodestoneEntityBlock<T> {

    public static final VoxelShape SHAPE = Block.box(6, 6, 6, 10, 10, 10);

    public OrbBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}