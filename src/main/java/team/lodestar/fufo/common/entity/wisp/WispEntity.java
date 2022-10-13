package team.lodestar.fufo.common.entity.wisp;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import team.lodestar.fufo.registry.client.FufoParticles;
import team.lodestar.fufo.registry.common.FufoEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;

import java.awt.*;
import java.util.List;

import static team.lodestar.lodestone.systems.rendering.particle.SimpleParticleOptions.Animator.WITH_AGE;

public class WispEntity extends AbstractWispEntity {
    public int sparksOrbiting;
    public boolean fullyCharged;
    public int fullyChargedTicks;

    public WispEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public WispEntity(Level level) {
        this(FufoEntities.METEOR_FIRE_WISP.get(), level);
    }

    public WispEntity(Level level, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        super(FufoEntities.METEOR_FIRE_WISP.get(), level, posX, posY, posZ, velX, velY, velZ);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("sparksOrbiting", sparksOrbiting);
        pCompound.putBoolean("fullyCharged", fullyCharged);
        pCompound.putInt("fullyChargedTicks", fullyChargedTicks);
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        sparksOrbiting = pCompound.getInt("sparksOrbiting");
        fullyCharged = pCompound.getBoolean("fullyCharged");
        fullyChargedTicks = pCompound.getInt("fullyChargedTicks");
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(getDeltaMovement().multiply(0.98f, 0.98f, 0.98f));
        if (level.getGameTime() % 25L == 0) {
            setDeltaMovement(0.1f - level.random.nextFloat() * 0.2f, 0.1f - level.random.nextFloat() * 0.2f, 0.1f - level.random.nextFloat() * 0.2f);
        }
        if (level.isClientSide) {
            if (level.getGameTime() % 2L == 0) {
                int amount = 1 + random.nextInt(3);
                for (int i = 0; i < amount; i++) {
                    float colorTilt = (0.6f + random.nextFloat() * 0.4f) / 255f;
                    int lifetime = (int) ((double) 8 / ((double) random.nextFloat() * 0.8D + 0.2D) * 2.5f);
                    Color startingColor = new Color(66 * colorTilt, 36 * colorTilt, 95 * colorTilt);
                    Color endingColor = new Color(108 * colorTilt, 38 * colorTilt, 96 * colorTilt).brighter();
                    ParticleBuilders.create(FufoParticles.COLORED_SMOKE)
                            .randomOffset(0.2f)
                            .setScale(0.05f)
                            .setAlpha(0.75f)
                            .setLifetime(lifetime)
                            .setColor(startingColor, endingColor)
                            .overwriteAnimator(WITH_AGE)
                            .overwriteRenderType(ParticleRenderType.PARTICLE_SHEET_OPAQUE)
                            .enableNoClip()
                            .spawn(level, getX(), getY(), getZ());
                }
            }
            int amount = 1 + random.nextInt(3);
            for (int i = 0; i < amount; i++) {
                float colorTilt = (0.6f + random.nextFloat() * 0.4f) / 255f;
                int lifetime = (int) ((double) 8 / ((double) random.nextFloat() * 0.8D + 0.2D) * 2.5f);
                int spinDirection = (random.nextBoolean() ? 1 : -1);
                int spinOffset = random.nextInt(360);
                float spinStrength = 0.5f + random.nextFloat() * 0.25f;
                Color startingColor = new Color(219 * colorTilt, 88 * colorTilt, 239 * colorTilt);
                Color endingColor = new Color(108 * colorTilt, 38 * colorTilt, 96 * colorTilt).brighter();

                ParticleBuilders.create(FufoParticles.SQUARE)
                        .randomOffset(0.05f)
                        .setScale(0.1f, 0f)
                        .setAlpha(0.6f, 0)
                        .setSpinOffset(spinOffset)
                        .setSpin(0, spinStrength * spinDirection)
                        .setSpinEasing(Easing.CUBIC_IN)
                        .setLifetime((int) (lifetime * 2f))
                        .randomMotion(0.0025f)
                        .setColor(startingColor, endingColor)
                        .enableNoClip()
                        .spawn(level, getX(), getY(), getZ());
            }
        }
    }
}