package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.common.entity.wisp.WispEntity;
import com.sammy.fufo.core.setup.content.DamageSourceRegistry;
import com.sammy.fufo.core.setup.content.block.BlockEntityRegistry;
import com.sammy.fufo.core.setup.content.item.ItemTagRegistry;
import com.sammy.fufo.core.setup.content.magic.FireEffectTypeRegistry;
import com.sammy.ortus.handlers.FireEffectHandler;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import com.sammy.ortus.systems.fireeffect.FireEffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;

public class MeteorFlameBlockEntity extends OrtusBlockEntity {

    public final ArrayList<ItemEntity> items = new ArrayList<>();

    public MeteorFlameBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public MeteorFlameBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.METEOR_FLAME.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide) {
            if (level.random.nextFloat() < 0.01f) {
                float lerp = (0.8f + level.random.nextFloat() * 0.2f) / 255f;
                Color startingColor = new Color(253 * lerp, 255 * lerp, 234 * lerp);
                Color midColor = new Color(255 * lerp, 0, 186 * lerp);
                Color endingColor = new Color(223 * lerp, 0, 255 * lerp);

                Vec3 randPos = BlockHelper.randPos(worldPosition, 0, 1);
                WispEntity wispEntity = new WispEntity(level, randPos.x, randPos.y, randPos.z, 0, 0.14f, 0);
                wispEntity.setColor(startingColor, midColor, endingColor);
                level.addFreshEntity(wispEntity);
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
        if (!(entity instanceof WispEntity) && !entity.fireImmune() && !items.contains(entity)) {
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
}