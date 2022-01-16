package com.project_esoterica.esoterica.common.blockentity;

import com.project_esoterica.esoterica.common.capability.EntityDataCapability;
import com.project_esoterica.esoterica.common.recipe.ManaAbsorptionRecipe;
import com.project_esoterica.esoterica.core.setup.DamageSourceRegistry;
import com.project_esoterica.esoterica.core.setup.block.BlockEntityRegistry;
import com.project_esoterica.esoterica.core.setup.item.ItemTagRegistry;
import com.project_esoterica.esoterica.core.systems.blockentity.SimpleBlockEntity;
import com.project_esoterica.esoterica.core.systems.meteorfire.MeteorFireHandler;
import com.project_esoterica.esoterica.core.systems.meteorfire.MeteorFireInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
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
            if (!MeteorFireHandler.hasMeteorFireInstance(entity))
            {
                MeteorFireHandler.setMeteorFireInstance(entity, new MeteorFireInstance(1, 15, Color.BLUE, Color.PINK).addTicks(160));
            }
            else
            {
                EntityDataCapability.getCapability(entity).ifPresent(c -> {
                    c.meteorFireInstance.remainingTicks++;
                });
            }
            entity.hurt(DamageSourceRegistry.METEOR_FIRE, 1);
        }
    }
}
