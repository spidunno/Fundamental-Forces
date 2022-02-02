package com.project_esoterica.esoterica.core.systems.magic.spell;

import com.project_esoterica.esoterica.core.setup.magic.SpellTypeRegistry;
import com.project_esoterica.esoterica.core.systems.easing.Easing;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;

public class SpellInstance {

    //https://tenor.com/view/fire-explosion-meme-fishing-gif-23044892
    public static final SpellInstance EMPTY = new SpellInstance(SpellTypeRegistry.EMPTY);
    public final SpellType type;
    public SpellCooldownData cooldown;
    public boolean selected;
    public int selectedTime;
    public float selectedFadeAnimation;

    public SpellInstance(SpellType type) {
        this.type = type;
    }

    public SpellInstance(SpellType type, SpellCooldownData cooldown) {
        this.type = type;
        this.cooldown = cooldown;
    }

    public void castBlock(ServerPlayer player, BlockPos pos, BlockHitResult hitVec) {
        if (!SpellCooldownData.isOnCooldown(cooldown)) {
            type.castBlock(this, player, pos, hitVec);
        }
    }
    public void cast(ServerPlayer player) {
        if (!SpellCooldownData.isOnCooldown(cooldown)) {
            type.cast(this, player);
        }
    }
    public void castCommon(ServerPlayer player) {
        if (!SpellCooldownData.isOnCooldown(cooldown)) {
            type.castCommon(this, player);
        }
    }

    public void tick() {
    }

    public void baseTick() {
        if (SpellCooldownData.isOnCooldown(cooldown)) {
            cooldown.tick();
        }
        selectedTime = selected ? selectedTime+1 : 0;
        if (selected && selectedFadeAnimation < 40)
        {
            selectedFadeAnimation++;
        }
        else if (selectedFadeAnimation > 0)
        {
            selectedFadeAnimation--;
        }
        tick();
    }

    public float getFade()
    {
        Easing easing = Easing.EXPO_IN;
        if (selected)
        {
            easing = Easing.EXPO_OUT;
        }
        return 0.5f- easing.ease(selectedFadeAnimation, 0, 0.5f, 40);
    }

    public boolean isEmpty() {
        return this.type == null || this.type.equals(SpellTypeRegistry.EMPTY);
    }

    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putString("typeId", type.id);
        if (SpellCooldownData.isOnCooldown(cooldown)) {
            CompoundTag cooldownTag = new CompoundTag();
            tag.put("spellCooldown", cooldown.serializeNBT(cooldownTag));
        }
        return tag;
    }


    public static SpellInstance deserializeNBT(CompoundTag tag) {
        return new SpellInstance(SpellTypeRegistry.SPELL_TYPES.get(tag.getString("typeId")), SpellCooldownData.deserializeNBT(tag.getCompound("spellCooldown")));
    }
}