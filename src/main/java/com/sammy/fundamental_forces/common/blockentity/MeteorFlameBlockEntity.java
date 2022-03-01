package com.sammy.fundamental_forces.common.blockentity;

import com.sammy.fundamental_forces.common.capability.EntityDataCapability;
import com.sammy.fundamental_forces.common.recipe.ManaAbsorptionRecipe;
import com.sammy.fundamental_forces.core.setup.content.DamageSourceRegistry;
import com.sammy.fundamental_forces.core.setup.content.block.BlockEntityRegistry;
import com.sammy.fundamental_forces.core.setup.content.item.ItemTagRegistry;
import com.sammy.fundamental_forces.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.fundamental_forces.core.handlers.MeteorFireHandler;
import com.sammy.fundamental_forces.core.systems.meteorfire.MeteorFireInstance;
import net.minecraft.core.BlockPos;
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
        if (!level.isClientSide)
        {
            items.removeIf(e -> !e.isAlive());
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public void onEntityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof ItemEntity itemEntity) {
            ItemStack stack = itemEntity.getItem();
            if (!items.contains(itemEntity)) {
                if (ItemTagRegistry.METEOR_FLAME_CATALYST.contains(stack.getItem()) || !ManaAbsorptionRecipe.getRecipes(level, stack).isEmpty()) {
                    items.add(itemEntity);
                }
                return;
            }
        }
        if (!entity.fireImmune() && !items.contains(entity)) {
            if (!MeteorFireHandler.hasMeteorFireInstance(entity))
            {
                MeteorFireHandler.setMeteorFireInstance(entity, new MeteorFireInstance(1, 15).addTicks(160));
            }
            else
            {
                EntityDataCapability.getCapability(entity).ifPresent(c -> c.meteorFireInstance.remainingTicks++);
            }
            entity.hurt(DamageSourceRegistry.METEOR_FIRE, 1);
        }
    }
}
