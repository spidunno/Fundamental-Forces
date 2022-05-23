package com.sammy.fufo.common.magic;

import com.sammy.fufo.common.entity.magic.spell.tier0.SpellMissile;
import com.sammy.fufo.core.systems.magic.element.MagicElement;
import com.sammy.fufo.core.systems.magic.spell.SpellCooldownData;
import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;

public class ProjectileSpell extends ElementAugmentedSpellType {
    public final EntityType<SpellMissile> projectileType;
    public float baseDamage = new Random().nextFloat() * 10;
    public float baseSpeed = new Random().nextFloat() * 0.5f + 0.5f;
    public float baseRange = new Random().nextFloat();
    public Color firstColor = new Color(16777215);
    public Color secondColor = new Color(16777215);
    public int duration;
    public MagicElement element;

    public ProjectileSpell(String id, MagicElement element, EntityType<SpellMissile> projectileSupplier) {
        super(id, element);
        this.projectileType = projectileSupplier;
    }

    @Override
    public void castCommon(SpellInstance instance, ServerPlayer player) {
        player.sendMessage(new TextComponent("Casting " + this.id + " spell with " + baseRange + " range, " + baseDamage + " damage, " + baseSpeed + " speed"), player.getUUID());
        instance.cooldown = new SpellCooldownData(duration);
        SpellMissile projectile = projectileType.create(player.level)
                .setFirstColor(firstColor)
                .setSecondColor(secondColor)
                .setDuration(duration)
                .setElement(element);
        projectile.setPos(player.getEyePosition());
        projectile.fireImmune();
        //projectile.shootFromRotation(projectile, player.xRotO, player.yRotO, 0, -1, 0);
        projectile.shoot(player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 0, 0);
        player.level.addFreshEntity(projectile);
        player.swing(InteractionHand.MAIN_HAND, true);
    }

    public void setFirstColor(Color color) {
        this.firstColor = color;
    }
    public void setSecondColor(Color color) {
        this.secondColor = color;
    }

    public EntityType<SpellMissile> getSpellMissile() {
        return this.projectileType;
    }
}
