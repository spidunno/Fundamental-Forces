package com.sammy.fundamental_forces.core.systems.meteorfire;

import com.sammy.fundamental_forces.core.setup.content.DamageSourceRegistry;
import com.sammy.fundamental_forces.core.systems.worldevent.WorldEventInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class FireEffectType {
    public final String id;

    protected final int damage;
    protected final int tickInterval;

    public FireEffectType(String id, int damage, int tickInterval) {
        this.id = id;
        this.damage = damage;
        this.tickInterval = tickInterval;
    }

    public int getDamage(FireEffectInstance instance) {
        return damage;
    }

    public int getTickInterval(FireEffectInstance instance) {
        return tickInterval;
    }

    public void extinguish(FireEffectInstance instance, Entity target) {
        instance.duration = 0;
        target.playSound(SoundEvents.GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (target.level.getRandom().nextFloat() - target.level.getRandom().nextFloat()) * 0.4F);
    }

    public void burn(FireEffectInstance instance, Entity target) {
        target.hurt(DamageSource.ON_FIRE, damage);
    }

    public boolean isValid(FireEffectInstance instance) {
        return instance.duration > 0;
    }
}