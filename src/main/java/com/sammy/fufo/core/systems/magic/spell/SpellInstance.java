package com.sammy.fufo.core.systems.magic.spell;

import com.sammy.fufo.common.capability.FufoPlayerDataCapability;
import com.sammy.fufo.common.packets.spell.SyncSpellCooldownPacket;
import com.sammy.fufo.core.setup.content.magic.SpellRegistry;
import com.sammy.fufo.core.systems.magic.element.MagicElement;
import com.sammy.fufo.core.systems.magic.spell.attributes.cast.SpellCastMode;
import com.sammy.fufo.core.systems.magic.spell.attributes.effect.SpellEffect;
import com.sammy.ortus.systems.easing.Easing;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PacketDistributor;

import static com.sammy.fufo.core.setup.server.PacketRegistry.INSTANCE;

public class SpellInstance {

    //https://tenor.com/view/fire-explosion-meme-fishing-gif-23044892
    public static final SpellInstance EMPTY = new SpellInstance(SpellRegistry.EMPTY);
    public SpellType spellType;
    public SpellCooldown oldCooldown;
    public SpellCooldown cooldown;
    public boolean selected;
    public int selectedTime;
    public float selectedFadeAnimation;
    public SpellCastMode castMode;
    public SpellEffect effect;
    public MagicElement element;

    public SpellInstance(SpellType spellType, SpellCastMode castMode, MagicElement element) {
        this.spellType = spellType;
        this.castMode = castMode;
        this.effect = spellType.effect;
        this.effect.element = element;
        this.element = element;
    }

    public SpellInstance(SpellType spellType) {
        this.spellType = spellType;
    }

    public SpellInstance setCooldown(SpellCooldown cooldown) {
        this.cooldown = cooldown;
        return this;
    }
    public SpellInstance setCooldown() {
        this.cooldown = spellType.cooldownFunction.apply(this);
        return this;
    }

    public void cast(ServerPlayer player, BlockPos pos, BlockHitResult hitVec) {
        if (castMode.canCast(this, player, pos, hitVec)) {
            effect.cast(this, player, hitVec);
        }
    }

    public void cast(ServerPlayer player) {
        if (castMode.canCast(this, player)) {
            effect.cast(this, player);
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
            FufoPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SyncSpellCooldownPacket(player.getUUID(), c.hotbarHandler.spellHotbar.getSelectedSpellIndex(player), cooldown)));
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
        return SpellCooldown.shouldTick(cooldown);
    }
    public boolean isReady(){
        if(cooldown==null){setCooldown();return true;}
        if(!isOnCooldown()){cooldown.reset();return true;}
        return false;
    }

    public boolean isEmpty() {
        return spellType == null || spellType.equals(SpellRegistry.EMPTY);
    }

    public CompoundTag serializeNBT() {
        CompoundTag spellTag = new CompoundTag();
        spellTag.putString("type", spellType.id.toString());
        if (element != null) {
            spellTag.putString("elementType", element.id.toString());
        }
        if (castMode != null) {
            spellTag.put("castMode", castMode.serializeNBT());
        }
        if (isOnCooldown()) {
            spellTag.put("spellCooldown", cooldown.serializeNBT());
        }
        return spellTag;
    }

    public static SpellInstance deserializeNBT(CompoundTag tag) {
        SpellInstance spellInstance = new SpellInstance(SpellRegistry.SPELL_TYPES.get(new ResourceLocation(tag.getString("type"))),
                SpellCastMode.deserializeNBT(tag.getCompound("castMode")),
                SpellRegistry.ELEMENTS.get(new ResourceLocation(tag.getString("elementType"))));
        if (tag.contains("spellCooldown")) {
            spellInstance.setCooldown(SpellCooldown.deserializeNBT(tag.getCompound("spellCooldown")));
        }
        return spellInstance;
    }
}