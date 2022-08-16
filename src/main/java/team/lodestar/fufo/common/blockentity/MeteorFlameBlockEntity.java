package team.lodestar.fufo.common.blockentity;

import team.lodestar.fufo.common.block.FlammableMeteoriteBlock;
import team.lodestar.fufo.common.entity.wisp.SparkEntity;
import team.lodestar.fufo.core.registratation.BlockEntityRegistrate;
import team.lodestar.fufo.core.setup.content.DamageSourceRegistry;
import team.lodestar.fufo.core.setup.content.item.ItemTagRegistry;
import team.lodestar.fufo.core.setup.content.magic.FireEffectTypeRegistry;
import team.lodestar.lodestone.handlers.FireEffectHandler;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.fireeffect.FireEffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;

public class MeteorFlameBlockEntity extends LodestoneBlockEntity {

    public final ArrayList<ItemEntity> items = new ArrayList<>();
    public int queuedSparks;


    public MeteorFlameBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public MeteorFlameBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistrate.METEOR_FLAME.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("queuedSparks", queuedSparks);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        queuedSparks = pTag.getInt("queuedSparks");
    }

    @Override
    public void init() {
        if (queuedSparks == 0) {
            attemptIgnition();
        }
    }

    @Override
    public void tick() {
        if (!level.isClientSide) {
            if (queuedSparks == 0) {
                if (level.random.nextFloat() < 0.02f) {
                    attemptIgnition();
                }
            } else {
                float extraChance = Math.max(0, queuedSparks - 12) * 0.02f;
                if (level.random.nextFloat() < (0.03f + extraChance)) {
                    float lerp = (0.7f + level.random.nextFloat() * 0.3f) / 255f;
                    Color color = new Color(255 * lerp, 0, 186 * lerp);

                    Vec3 randPos = BlockHelper.withinBlock(level.getRandom(), worldPosition);
                    float velocity = 0.35f + level.random.nextFloat() * 0.1f;
                    SparkEntity sparkEntity = new SparkEntity(level, randPos.x, randPos.y, randPos.z, 0.05f - level.random.nextFloat() * 0.1f, velocity, 0.05f - level.random.nextFloat() * 0.1f);
                    sparkEntity.setColor(color);
                    level.addFreshEntity(sparkEntity);
                    queuedSparks--;
                }
            }
            items.removeIf(e -> !e.isAlive());
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public void onEntityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof ItemEntity itemEntity) {
            ItemStack stack = itemEntity.getItem();
            if (!items.contains(itemEntity)) {
                if (stack.is(ItemTagRegistry.METEOR_FLAME_CATALYST)) {
                    items.add(itemEntity);
                }
                return;
            }
        }
        if (!(entity instanceof SparkEntity) && !entity.fireImmune() && !items.contains(entity)) {
            FireEffectInstance instance = FireEffectHandler.getFireEffectInstance(entity);
            if (instance == null) {
                FireEffectHandler.setCustomFireInstance(entity, new FireEffectInstance(FireEffectTypeRegistry.METEOR_FIRE).setDuration(20));
            } else {
                instance.setDuration(160);
                if (!level.isClientSide) {
                    instance.sync(entity);
                }
            }
            entity.hurt(DamageSourceRegistry.METEOR_FIRE, 1);
        }
    }

    public void attemptIgnition() {
        BlockPos below = getBlockPos().below();
        BlockState blockState = level.getBlockState(below);
        Block block = blockState.getBlock();
        if (block instanceof FlammableMeteoriteBlock) {
            boolean success = FlammableMeteoriteBlock.progressDepletion(level, below);
            if (success) {
                queuedSparks = 16;
            } else {
                BlockPos blockPos = getBlockPos();
                level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                level.levelEvent(null, 1009, blockPos, 0);
            }
        }
    }
}