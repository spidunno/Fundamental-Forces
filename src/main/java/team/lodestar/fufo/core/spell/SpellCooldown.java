package team.lodestar.fufo.core.spell;

import net.minecraft.nbt.CompoundTag;

public class SpellCooldown {
    public final int duration;
    public int timer;
    public boolean discarded;

    public SpellCooldown(int duration) {
        this.duration = duration;
    }

    public SpellCooldown(int duration, int timer) {
        this.duration = duration;
        this.timer = timer;
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

    public static boolean shouldTick(SpellCooldown cooldown) {
        return cooldown != null && !cooldown.discarded;
    }

    public CompoundTag serializeNBT() {
        CompoundTag cooldownTag = new CompoundTag();
        cooldownTag.putInt("duration", duration);
        cooldownTag.putInt("timer", timer);
        return cooldownTag;
    }

    public static SpellCooldown deserializeNBT(CompoundTag tag) {
        return new SpellCooldown(tag.getInt("duration"), tag.getInt("timer"));
    }
}