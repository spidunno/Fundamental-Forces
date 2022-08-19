package team.lodestar.fufo.core.spell.attributes.effect;

import team.lodestar.fufo.common.entity.magic.spell.AbstractSpellProjectile;
import team.lodestar.fufo.core.spell.SpellInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.awt.*;
import java.util.function.Function;

public class ProjectileEffect extends SpellEffect {
    public Function<Level, AbstractSpellProjectile> projectileSupplier;
    public Color firstColor = new Color(16777215);
    public Color secondColor = new Color(16777215);

    public ProjectileEffect(Function<Level, AbstractSpellProjectile> projectileSupplier){
        super(CastLogicHandler.ALWAYS_DEFAULT_CAST);
        this.projectileSupplier = projectileSupplier;
    }
    @Override
    public void effect(SpellInstance spell, ServerPlayer player) {
        AbstractSpellProjectile projectile = projectileSupplier.apply(player.level)
                .setElement(element)
                .setColor(firstColor, secondColor)
                .setLifetime(duration);
        projectile.setOwner(player);
        projectile.setPos(player.getEyePosition());
        projectile.fireImmune();
        projectile.shoot(player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 1, 0);
        player.level.addFreshEntity(projectile);
        player.swing(InteractionHand.MAIN_HAND, true);
    }

    @Override
    public void effect(SpellInstance spell, ServerPlayer player, BlockHitResult result) {}
}
