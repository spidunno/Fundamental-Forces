package team.lodestar.fufo.common.block;

import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import team.lodestar.fufo.common.blockentity.MeteorFlameBlockEntity;
import team.lodestar.fufo.registry.client.FufoParticles;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;

import java.awt.*;

import static team.lodestar.lodestone.systems.rendering.particle.SimpleParticleOptions.Animator.WITH_AGE;

public class MeteorFlameBlock<T extends MeteorFlameBlockEntity> extends LodestoneEntityBlock<T> {
    protected static final VoxelShape DOWN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    public MeteorFlameBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).getBlock() instanceof FlammableMeteoriteBlock;
    }

    @Override
    protected void spawnDestroyParticles(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState) {
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide()) {
            pLevel.levelEvent(null, 1009, pPos, 0);
        }
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return DOWN_AABB;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(24) == 0) {
            pLevel.playLocalSound((double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.5D, (double) pPos.getZ() + 0.5D, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F + pRandom.nextFloat(), pRandom.nextFloat() * 0.7F + 0.3F, false);
        }
        for (int i = 0; i < 3; i++) {
            float lerp = (0.6f + pRandom.nextFloat() * 0.4f) / 255f;
            int lifetime = (int) ((double) 8 / ((double) pLevel.random.nextFloat() * 0.8D + 0.2D) * 2.5f);
            Color startingColor = new Color(66 * lerp, 36 * lerp, 95 * lerp);
            Color endingColor = new Color(108 * lerp, 38 * lerp, 96 * lerp).brighter();
            ParticleBuilders.create(FufoParticles.COLORED_SMOKE)
                    .randomOffset(0.5f, 0.25f)
                    .setScale(0.25f)
                    .setAlpha(1)
                    .setLifetime(lifetime)
                    .setColor(startingColor, endingColor)
                    .setGravity(-0.05f)
                    .overwriteAnimator(WITH_AGE)
                    .overwriteRenderType(ParticleRenderType.PARTICLE_SHEET_OPAQUE)
                    .spawn(pLevel, pPos.getX() + 0.5f, pPos.getY() + 0.5f, pPos.getZ() + 0.5f);
        }
    }
}