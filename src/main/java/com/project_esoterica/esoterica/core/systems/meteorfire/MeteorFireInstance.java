package com.project_esoterica.esoterica.core.systems.meteorfire;

import com.project_esoterica.esoterica.common.capability.EntityDataCapability;
import com.project_esoterica.esoterica.core.setup.DamageSourceRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;

import java.awt.*;

public class MeteorFireInstance {
    public int remainingTicks;
    public int damage;
    public int frequency;
    public Color brightColor;
    public Color darkColor;

    public MeteorFireInstance(int damage, int frequency, Color brightColor, Color darkColor) {
        this.damage = damage;
        this.frequency = frequency;
        this.brightColor = brightColor;
        this.darkColor = darkColor;
    }
    public MeteorFireInstance addTicks(int increase)
    {
        this.remainingTicks += increase;
        return this;
    }
    public MeteorFireInstance override(MeteorFireInstance newInstance)
    {
        this.remainingTicks = newInstance.remainingTicks;
        this.damage = newInstance.damage;
        this.frequency = newInstance.frequency;
        this.brightColor = newInstance.brightColor;
        this.darkColor = newInstance.darkColor;
        return this;
    }
    public void tick(Entity entity)
    {
        if ((entity.isInPowderSnow || entity.isInWaterRainOrBubble()))
        {
            remainingTicks = 0;
            entity.playSound(SoundEvents.GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (entity.level.getRandom().nextFloat() - entity.level.getRandom().nextFloat()) * 0.4F);
        }
        if (!entity.level.isClientSide && entity.getRemainingFireTicks() > 0)
        {
            remainingTicks = 0;
            EntityDataCapability.sync(entity);
        }
        if (entity.fireImmune()) {
            remainingTicks -=4;
        }
        else
        {
            remainingTicks--;
            if (remainingTicks > 0 && remainingTicks % frequency == 0) {
                entity.hurt(DamageSourceRegistry.METEOR_FIRE, damage);
            }
        }
    }
    public boolean isValid()
    {
        return remainingTicks > 0;
    }

    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putInt("remainingTicks", remainingTicks);
        tag.putInt("damage", damage);
        tag.putInt("frequency", frequency);
        tag.putInt("brightColor", brightColor.getRGB());
        tag.putInt("darkColor", darkColor.getRGB());
        return tag;
    }

    public static MeteorFireInstance deserializeNBT(CompoundTag tag) {
        MeteorFireInstance instance = new MeteorFireInstance(tag.getInt("damage"), tag.getInt("frequency"), new Color(tag.getInt("brightColor")), new Color(tag.getInt("darkColor")));
        return instance.addTicks(tag.getInt("remainingTicks"));
    }
}
