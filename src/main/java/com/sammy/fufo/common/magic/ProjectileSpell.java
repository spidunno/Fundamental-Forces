package com.sammy.fufo.common.magic;

import com.sammy.fufo.common.entity.magic.spell.tier1.SpellBolt;
import com.sammy.fufo.core.systems.magic.element.MagicElement;
import com.sammy.fufo.core.systems.magic.spell.SpellCooldownData;
import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;

import java.awt.*;

public class ProjectileSpell extends ElementAugmentedSpellType {
    public final EntityType<SpellBolt> projectileType;
    public Color firstColor = new Color(16777215);
    public Color secondColor = new Color(16777215);
    public int duration;
    public MagicElement element;

    public ProjectileSpell(String id, MagicElement element, EntityType<SpellBolt> projectileSupplier) {
        super(id, element);
        this.projectileType = projectileSupplier;
    }

    @Override
    public void castCommon(SpellInstance instance, ServerPlayer player) {
        instance.cooldown = new SpellCooldownData(duration);
        SpellBolt projectile = projectileType.create(player.level)
                .setFirstColor(firstColor)
                .setSecondColor(secondColor)
                .setDuration(duration)
                .setElement(element);
        projectile.setPos(player.getEyePosition());
        projectile.fireImmune();
        //projectile.shootFromRotation(projectile, player.xRotO, player.yRotO, 0, -1, 0);
        projectile.shoot(player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 1, 0);
        player.level.addFreshEntity(projectile);
        player.swing(InteractionHand.MAIN_HAND, true);
    }

    public void setFirstColor(Color color) {
        this.firstColor = color;
    }
    public void setSecondColor(Color color) {
        this.secondColor = color;
    }

}
