package team.lodestar.fufo.core.spell;

import team.lodestar.fufo.common.capability.FufoPlayerDataCapability;
import team.lodestar.fufo.common.packets.spell.SyncSpellCooldownPacket;
import team.lodestar.fufo.core.element.MagicElement;
import team.lodestar.fufo.registry.common.magic.FufoMagicElements;
import team.lodestar.fufo.registry.common.magic.FufoSpellTypes;
import team.lodestar.lodestone.systems.easing.Easing;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.fufo.registry.common.FufoPackets;

public class SpellInstance {

    //https://tenor.com/view/fire-explosion-meme-fishing-gif-23044892
    public static final SpellInstance EMPTY = new SpellInstance(FufoSpellTypes.EMPTY);
    public final SpellType spellType;
    public SpellCooldown cooldown;
    public boolean selected;
    public int selectedTime;
    public float selectedFadeAnimation;
    public SpellCastMode castMode;
    public SpellEffect effect;

    public SpellInstance(SpellType spellType, SpellCastMode castMode) {
        this.spellType = spellType;
        this.castMode = castMode;
        this.effect = spellType.effect;
    }

    public SpellInstance(SpellType spellType) {
        this.spellType = spellType;
    }

    public SpellInstance setCooldown(SpellCooldown cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public SpellInstance setAndSyncCooldown(ServerPlayer player) {
        setCooldown(spellType.defaultCooldownSupplier.apply(this));
        FufoPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> FufoPackets.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SyncSpellCooldownPacket(player.getUUID(), c.hotbarHandler.spellHotbar.getSelectedSpellIndex(player), cooldown)));
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

    public final void baseTick(Level level) {
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

    public boolean isEmpty() {
        return spellType == null || spellType.equals(FufoSpellTypes.EMPTY);
    }

    public CompoundTag serializeNBT() {
        CompoundTag spellTag = new CompoundTag();
        spellTag.putString("type", spellType.id.toString());
        if (castMode != null) {
            spellTag.put("castMode", castMode.serializeNBT());
        }
        if (isOnCooldown()) {
            spellTag.put("spellCooldown", cooldown.serializeNBT());
        }
        return spellTag;
    }

    public static SpellInstance deserializeNBT(CompoundTag tag) {
        SpellInstance spellInstance = new SpellInstance(FufoSpellTypes.SPELL_TYPES.get(new ResourceLocation(tag.getString("type"))),
                SpellCastMode.deserializeNBT(tag.getCompound("castMode")));
        if (tag.contains("spellCooldown")) {
            spellInstance.setCooldown(SpellCooldown.deserializeNBT(tag.getCompound("spellCooldown")));
        }
        return spellInstance;
    }
}