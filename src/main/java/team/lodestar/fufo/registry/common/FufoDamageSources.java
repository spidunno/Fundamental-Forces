package team.lodestar.fufo.registry.common;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class FufoDamageSources {

    public static final DamageSource METEOR_FIRE = new DamageSource("meteor_fire").setIsFire();

    public static DamageSource causeMeteorFireDamage(Entity attacker) {
        return new EntityDamageSource("meteor_fire", attacker).setIsFire();
    }
}
