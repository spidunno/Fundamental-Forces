package com.project_esoterica.esoterica.common.blockentity;

import com.project_esoterica.esoterica.common.recipe.ManaAbsorptionRecipe;
import com.project_esoterica.esoterica.core.registry.block.BlockEntityRegistry;
import com.project_esoterica.esoterica.core.registry.item.ItemTagRegistry;
import com.project_esoterica.esoterica.core.systems.blockentity.SimpleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class MeteorFlameBlockEntity extends SimpleBlockEntity {

    public final ArrayList<ItemEntity> items = new ArrayList<>();

    public MeteorFlameBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public MeteorFlameBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.METEOR_FLAME.get(), pos, state);
    }

    @Override
    public void tick() {

    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public void onEntityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof ItemEntity itemEntity) {
            ItemStack stack = itemEntity.getItem();
            if (ItemTagRegistry.METEOR_FLAME_CATALYST.contains(stack.getItem()) || !ManaAbsorptionRecipe.getRecipes(level, stack).isEmpty()) {
                if (!items.contains(itemEntity)) {
                    items.add(itemEntity);
                }
                return;
            }
        }
        if (!entity.fireImmune() && !items.contains(entity)) {
            entity.setRemainingFireTicks(entity.getRemainingFireTicks() + 1);
            if (entity.getRemainingFireTicks() == 0) {
                entity.setSecondsOnFire(8);
            }
            entity.hurt(DamageSource.IN_FIRE, 1);
        }
    }
}
