package team.lodestar.fufo.unsorted.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

public class TileHelper {
	private TileHelper() {}
	
	/**
	 * A wrapper for Level.getBlockEntity that avoids overhead from checking an unloaded chunk.
	 * @param level
	 * @param pos
	 * @return
	 */
	public static Optional<BlockEntity> getBlockEntitySafe(Level level, BlockPos pos) {
		if (level.isLoaded(pos)) return Optional.of(level.getBlockEntity(pos));
		return Optional.empty();
	}
}