package com.project_esoterica.esoterica.core.systems.magic.spell;

import com.project_esoterica.esoterica.core.setup.magic.SpellTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;

public class SpellInstance {

    //https://tenor.com/view/fire-explosion-meme-fishing-gif-23044892
    public static final SpellInstance EMPTY = new SpellInstance(SpellTypeRegistry.EMPTY);
    public final SpellType type;
    public SpellCooldown cooldown;

    public SpellInstance(SpellType type) {
        this.type = type;
    }

    public SpellInstance(SpellType type, SpellCooldown cooldown) {
        this.type = type;
        this.cooldown = cooldown;
    }

    public void castBlock(ServerPlayer player, BlockPos pos, BlockHitResult hitVec) {
        if (!SpellCooldown.isOnCooldown(cooldown)) {
            type.castBlock(this, player, pos, hitVec);
        }
    }
    public void cast(ServerPlayer player) {
        if (!SpellCooldown.isOnCooldown(cooldown)) {
            type.cast(this, player);
        }
    }

    public void tick() {
        if (SpellCooldown.isOnCooldown(cooldown)) {
            cooldown.tick();
        }
    }

    public boolean isEmpty() {
        return this.type == null || this.type.equals(SpellTypeRegistry.EMPTY);
    }

    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putString("typeId", type.id);
        if (SpellCooldown.isOnCooldown(cooldown)) {
            CompoundTag cooldownTag = new CompoundTag();
            tag.put("spellCooldown", cooldown.serializeNBT(cooldownTag));
        }
        return tag;
    }


    public static SpellInstance deserializeNBT(CompoundTag tag) {
        return new SpellInstance(SpellTypeRegistry.SPELL_TYPES.get(tag.getString("typeId")), SpellCooldown.deserializeNBT(tag.getCompound("spellCooldown")));
    }
}