package com.project_esoterica.esoterica.common.entity.falling;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class FallingEntity extends Entity {

    public FallingEntity(EntityType<?> p_19870, Level p_19871_) {
        super(p_19870, p_19871_);
        this.noPhysics = true;
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(0, 1,0);
    }
}