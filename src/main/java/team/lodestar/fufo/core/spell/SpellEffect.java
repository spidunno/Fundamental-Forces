package team.lodestar.fufo.core.spell;

import team.lodestar.fufo.core.element.MagicElement;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;

public abstract class SpellEffect {

    public enum CastLogicHandler {
        ONLY_BLOCK,
        INDEPENDENT,
        ALWAYS_DEFAULT_CAST
    }

    public final CastLogicHandler handler;
    public final MagicElement element;
    public int range;
    public int duration;
    public int power;

    public SpellEffect(CastLogicHandler handler, MagicElement element) {
        this.handler = handler;
        this.element = element;
    }

    public void cast(SpellInstance spell, ServerPlayer player) {
        if (canCast(spell, player)) {
            effect(spell, player);
        }
    }

    public void cast(SpellInstance spell, ServerPlayer player, BlockHitResult result) {
        if (canCast(spell, player)) {
            effect(spell, player, result);
        }
    }

    public abstract void effect(SpellInstance spell, ServerPlayer player, BlockHitResult result);

    public abstract void effect(SpellInstance spell, ServerPlayer player);

    public boolean canCast(SpellInstance spell, ServerPlayer player) {
        boolean isOnCooldown = spell.isOnCooldown();
        if (!isOnCooldown) {
            spell.setAndSyncCooldown(player);
        }
        return !isOnCooldown;
    }

    public SpellEffect range(int range) {
        this.range = range;
        return this;
    }

    public SpellEffect duration(int duration) {
        this.duration = duration;
        return this;
    }

    public SpellEffect power(int power) {
        this.power = power;
        return this;
    }
}