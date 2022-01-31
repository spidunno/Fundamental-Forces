package com.project_esoterica.esoterica.core.systems.magic.spell;

import net.minecraft.nbt.CompoundTag;

public class SpellCooldown {
    public final int duration;
    public int timer;
    public boolean discarded;

    public SpellCooldown(int duration) {
        this.duration = duration;
    }

    public float getProgress() {
        return timer / (float) duration;
    }

    public float getPercentage() {
        return (duration - timer) / (float) duration;
    }

    public void tick() {
        timer++;
        if (timer >= duration) {
            discarded = true;
        }
    }

    public CompoundTag serializeNBT(CompoundTag tag) {
        if (!discarded) {
            tag.putInt("duration", duration);
            tag.putInt("timer", timer);
        }
        return tag;
    }
    public static boolean isOnCooldown(SpellCooldown cooldown)
    {
        return cooldown != null && !cooldown.discarded;
    }
    public static SpellCooldown deserializeNBT(CompoundTag tag) {
        SpellCooldown cooldown = new SpellCooldown(tag.getInt("duration"));
        cooldown.timer = tag.getInt("timer");
        return cooldown;
    }
}