package com.sammy.fufo.common.block;

import com.sammy.fufo.common.blockentity.MeteorFlameBlockEntity;
import com.sammy.fufo.core.setup.client.FufoParticleRegistry;
import com.sammy.ortus.systems.block.OrtusEntityBlock;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import com.sammy.ortus.systems.rendering.particle.SimpleParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

import static com.sammy.ortus.systems.rendering.particle.SimpleParticleOptions.Animator.WITH_AGE;

public class MeteorFlameBlock<T extends MeteorFlameBlockEntity> extends OrtusEntityBlock<T> {
    protected static final VoxelShape DOWN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    public MeteorFlameBlock(Properties properties) {
        super(properties);
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
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        if (pRandom.nextInt(24) == 0) {
            pLevel.playLocalSound((double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.5D, (double) pPos.getZ() + 0.5D, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F + pRandom.nextFloat(), pRandom.nextFloat() * 0.7F + 0.3F, false);
        }
        ParticleBuilders.create(FufoParticleRegistry.COLORED_SMOKE)
                .randomOffset(1)
                .setScale(0.5f)
                .overwriteAnimator(WITH_AGE)
                .repeat(pLevel, pPos.getX() + 0.5f, pPos.getY() + 0.5f, pPos.getZ() + 0.5f, 4);
    }
}