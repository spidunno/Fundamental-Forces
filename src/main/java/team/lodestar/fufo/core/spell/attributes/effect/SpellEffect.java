package team.lodestar.fufo.core.spell.attributes.effect;

import team.lodestar.fufo.core.element.MagicElement;
import team.lodestar.fufo.core.spell.SpellInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;

public abstract class SpellEffect {

    public enum CastLogicHandler {
        ONLY_BLOCK,
        INDEPENDENT,
        ALWAYS_DEFAULT_CAST
    }

    public MagicElement element;
    public final CastLogicHandler handler;
    public int range;
    public int duration;
    public int power;

    public SpellEffect(CastLogicHandler handler) {
        this.handler = handler;
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
            spell.setCooldown(player);
        }
        return !isOnCooldown;
    }

    public SpellEffect range(int range) {
        this.range = range;
        return this;
    }

    public SpellEffect element(MagicElement element) {
        this.element = element;
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