package com.sammy.fufo.core.systems.magic.spell.attributes.effect;

import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import com.sammy.fufo.core.systems.magic.spell.attributes.element.SpellElement;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;

public class SpellEffect {
    public SpellElement element;
    public int range;
    public int duration;
    public int power;

    public SpellEffect() {
    }

    public void cast(SpellInstance instance, ServerPlayer player) {

    }
    public void cast(SpellInstance spell, ServerPlayer player, BlockPos pos, BlockHitResult hitVec){

    }
    public SpellEffect setRange(int range) {
        this.range = range;
        return this;
    }
    public SpellEffect setElement(SpellElement element) {
        this.element = element;
        return this;
    }
    public SpellEffect setDuration(int duration) {
        this.duration = duration;
        return this;
    }
    public SpellEffect setPower(int power) {
        this.power = power;
        return this;
    }
}
