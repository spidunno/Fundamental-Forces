package com.sammy.fufo.core.systems.magic.spell.attributes.effect;

import com.sammy.fufo.common.entity.magic.spell.AbstractSpellProjectile;
import com.sammy.fufo.core.systems.magic.spell.SpellCooldown;
import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.function.Function;

public class ProjectileEffect extends SpellEffect {
    public Function<Level, AbstractSpellProjectile> projectileSupplier;
    public Color firstColor = new Color(16777215);
    public Color secondColor = new Color(16777215);

    public ProjectileEffect(Function<Level, AbstractSpellProjectile> projectileSupplier){
        this.projectileSupplier = projectileSupplier;
    }
    @Override
    public void cast(SpellInstance instance, ServerPlayer player) {
        instance.cooldown = new SpellCooldown(duration);
        AbstractSpellProjectile projectile = projectileSupplier.apply(player.level)
                .setFirstColor(firstColor)
                .setSecondColor(secondColor)
                .setLifetime(duration);
        projectile.setPos(player.getEyePosition());
        projectile.fireImmune();
        projectile.shoot(player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 1, 0);
        player.level.addFreshEntity(projectile);
        player.swing(InteractionHand.MAIN_HAND, true);
    }
}
