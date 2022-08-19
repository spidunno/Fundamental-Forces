package team.lodestar.fufo.common.entity.magic.spell.tier1;

import team.lodestar.fufo.common.entity.magic.spell.AbstractSpellProjectile;
import team.lodestar.fufo.registry.common.FufoEntities;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class SpellBolt extends AbstractSpellProjectile {

    public final ArrayList<Vec3> pastPositions = new ArrayList<>();

    public float damage = 5;
    public double knockback = 0.4;

    public SpellBolt(Level level) {
        super(FufoEntities.SPELL_BOLT.get(), level);
        lifetime = 100;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        EntityDamageSource playerDamageSource = new EntityDamageSource("player", this.getOwner());
        Entity entity = pResult.getEntity();
        entity.hurt(playerDamageSource.setMagic(), damage);
        if(entity instanceof LivingEntity){
            calculateKnockBack((LivingEntity) entity,knockback);
        }
        this.discard();
    }
    protected void calculateKnockBack(LivingEntity entity,double strength){
        Vec3 movement = this.getDeltaMovement();
        entity.knockback(strength,-movement.x,-movement.z);
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide) {
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();
            float scale = 0.17f + level.random.nextFloat() * 0.03f;
            Vec3 velocity = this.getDeltaMovement();
            ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScale(scale / 2f, 0)
                    .setLifetime(4 + level.random.nextInt(5))
                    .setAlpha(0.8f, 0.5f)
                    .setColor(startColor, endColor)
                    .setColorCoefficient(0.2f)
                    .setColorEasing(Easing.SINE_OUT)
                    .setSpinOffset((level.getGameTime() * 0.2f) % 6.28f)
                    .setSpin(0, 0.3f)
                    .setSpinEasing(Easing.QUARTIC_IN)
                    .addMotion(velocity.x / 15, velocity.y / 15, velocity.z / 15)
                    .spawn(level, x, y, z);
            ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setScale(scale / 3f, 0)
                    .setLifetime(3 + level.random.nextInt(6))
                    .setAlpha(0.8f, 0.5f)
                    .setColor(startColor, endColor)
                    .setColorCoefficient(0.2f)
                    .setColorEasing(Easing.SINE_OUT)
                    .setSpinOffset((level.getGameTime() * 0.2f) % 6.28f)
                    .setSpin(0, 0.3f)
                    .setSpinEasing(Easing.QUARTIC_IN)
                    .addMotion(velocity.x / 20, velocity.y / 20, velocity.z / 20)
                    .spawn(level, x, y, z);
            ParticleBuilders.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setScale(scale / 5f)
                    .setLifetime(2 + level.random.nextInt(2))
                    .setAlpha(0.8f, 0.2f)
                    .setColor(endColor, startColor)
                    .setColorCoefficient(0.3f)
                    .setColorEasing(Easing.SINE_OUT)
                    .setSpinOffset((level.getGameTime() * 0.2f) % 6.28f)
                    .randomOffset(0.4f)
                    .setSpin(0, 0.3f)
                    .addMotion(velocity.x / 3, velocity.y / 3, velocity.z / 3)
                    .spawn(level, x, y, z);
            ParticleBuilders.create(LodestoneParticleRegistry.STAR_PARTICLE)
                    .setScale(scale, 0)
                    .setLifetime(1)
                    .setAlpha(0.8f, 0.5f)
                    .setColor(startColor, endColor)
                    .setColorCoefficient(0.2f)
                    .setColorEasing(Easing.SINE_OUT)
                    .setSpinOffset((level.getGameTime() * 0.2f) % 6.28f)
                    .setSpin(0, 0.3f)
                    .spawn(level, x, y, z);
        }
    }
}