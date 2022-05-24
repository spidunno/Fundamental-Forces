package com.sammy.fufo.core.systems.magic.spell;

import com.sammy.fufo.common.capability.FufoPlayerDataCapability;
import com.sammy.fufo.common.packets.spell.UpdateCooldownPacket;
import com.sammy.fufo.core.setup.content.magic.SpellHolderRegistry;
import com.sammy.fufo.core.systems.magic.spell.attributes.cast.SpellCastMode;
import com.sammy.fufo.core.systems.magic.spell.attributes.effect.SpellEffect;
import com.sammy.fufo.core.systems.magic.spell.attributes.element.SpellElement;
import com.sammy.ortus.systems.easing.Easing;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

import static com.sammy.fufo.core.setup.server.PacketRegistry.INSTANCE;

public class SpellInstance {

    //https://tenor.com/view/fire-explosion-meme-fishing-gif-23044892
    public static final SpellInstance EMPTY = new SpellInstance(SpellHolderRegistry.EMPTY);
    // type = old SpellType
    public SpellCooldownData oldCooldown;
    public SpellCooldownData cooldown;
    public Supplier<SpellCooldownData> cooldownSupplier;
    public boolean selected;
    public int selectedTime;
    public float selectedFadeAnimation;
    public SpellCastMode castMode;
    public SpellEffect effect;
    public SpellElement element;

    public SpellInstance(SpellCastMode castMode, SpellEffect effect, SpellElement element, Supplier<SpellCooldownData> cooldownSupplier) {
        this.castMode = castMode;
        this.effect = effect;
        this.element = element;
        this.cooldownSupplier = cooldownSupplier;
    }

    public SpellInstance(SpellCastMode castMode, SpellEffect effect, Supplier<SpellCooldownData> cooldownSupplier) {
        this.castMode = castMode;
        this.effect = effect;
        this.cooldownSupplier = cooldownSupplier;
    }

    public SpellInstance(SpellCastMode castMode) {
        this.castMode = castMode;
    }

    public SpellInstance(SpellHolder type) {
    }
    public void cast(ServerPlayer player, BlockPos pos, BlockHitResult hitVec) {
        if(!isOnCooldown()) {
            castMode.cast(this);
        }
    }

    public void cast(ServerPlayer player) {
        if (!isOnCooldown()) {
            castMode.cast(this);
        }
    }

    public void tick(Level level) {
    }

    public void baseTick(Level level) {
        if (isOnCooldown()) {
            cooldown.tick();
        }
        selectedTime = selected ? selectedTime + 1 : 0;
        if (selected && selectedFadeAnimation < 20) {
            selectedFadeAnimation++;
        } else if (selectedFadeAnimation > 0) {
            selectedFadeAnimation -= 0.5f;
        }
        tick(level);
    }

    public void playerTick(ServerPlayer player) {
        if (cooldown != null && !cooldown.equals(oldCooldown)) {
            FufoPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new UpdateCooldownPacket(player.getUUID(), c.hotbarHandler.spellHotbar.getSelectedSpellIndex(player), cooldown)));
        }
        oldCooldown = cooldown;
    }

    public float getIconFadeout() {
        Easing easing = Easing.EXPO_IN;
        if (selected) {
            easing = Easing.EXPO_OUT;
        }
        return 0.5f - easing.ease(selectedFadeAnimation, 0, 0.5f, 20);
    }

    public boolean isOnCooldown() {
        return SpellCooldownData.isValid(cooldown);
    }
    public boolean isEmpty() {
        return type == null || type.equals(SpellHolderRegistry.EMPTY);
    }

    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putString("typeId", type.id);
        if (isOnCooldown()) {
            CompoundTag cooldownTag = new CompoundTag();
            tag.put("spellCooldown", cooldown.serializeNBT(cooldownTag));
        }
        return tag;
    }

    public static SpellInstance deserializeNBT(CompoundTag tag) {
        return new SpellInstance(SpellHolderRegistry.SPELL_TYPES.get(tag.getString("typeId")), SpellCooldownData.deserializeNBT(tag.getCompound("spellCooldown")));
    }
}