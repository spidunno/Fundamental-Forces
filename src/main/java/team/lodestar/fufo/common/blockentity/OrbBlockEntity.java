package team.lodestar.fufo.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;

import java.awt.*;

public class OrbBlockEntity extends LodestoneBlockEntity {

	public OrbBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void clientTick() {
		if (level == null) {
			return;
		}

		double x = worldPosition.getX() + 0.5;
		double y = worldPosition.getY() + 0.5;
		double z = worldPosition.getZ() + 0.5;
		int lifeTime = 3 + level.random.nextInt(9);
		float scale = 0.1f + level.random.nextFloat() * 0.07f;
		ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
						.setScale(scale, 0)
						.setLifetime(lifeTime)
						.setAlpha(0.2f)
						.randomMotion(0.02f)
						.randomOffset(0.05f)
						.setColor(Color.CYAN, Color.YELLOW)
						.setMotionCoefficient(1.25f)
						.spawn(level, x, y, z);
//            if (level.getGameTime() % 2L == 0) {
//                RenderHelper.create(ParticleRegistry.SQUARE)
//                        .setScale(scale * 2, 0)
//                        .setLifetime(lifeTime + 4)
//                        .setAlpha(0, 0.8f)
//                        .addVelocity(0, 0.04f, 0)
//                        .randomVelocity(0.02f)
//                        .randomOffset(0.1f)
//                        .setColor(Color.CYAN, Color.YELLOW)
//                        .setMotionCoefficient(1.25f)
//                        .spawn(level, x, y, z);
//            }
//            RenderHelper.create(ParticleRegistry.CIRCLE)
//                    .setScale(scale, 0)
//                    .setLifetime(lifeTime + 4)
//                    .setAlpha(0, 0.3f)
//                    .randomVelocity(0.02f)
//                    .randomOffset(0.1f)
//                    .setColor(Color.CYAN, Color.YELLOW)
//                    .setMotionCoefficient(1.5f)
//                    .spawn(level, x, y+0.025f, z);


	}
}