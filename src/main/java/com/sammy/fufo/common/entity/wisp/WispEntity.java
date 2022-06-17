package com.sammy.fufo.common.entity.wisp;

import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class WispEntity extends AbstractWispEntity {
    public WispEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public WispEntity(Level level) {
        this(EntityRegistry.METEOR_FIRE_WISP.get(), level);
    }

    public WispEntity(Level level, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        super(EntityRegistry.METEOR_FIRE_WISP.get(), level, posX, posY, posZ, velX, velY, velZ);
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(getDeltaMovement().multiply(0.98f,0.98f,0.98f));
    }

    @Override
    public boolean hasPriority() {
        return true;
    }
}