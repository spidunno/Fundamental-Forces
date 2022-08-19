package team.lodestar.fufo.common.entity.wisp;

import team.lodestar.fufo.registry.common.FufoEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;

import java.util.List;

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
        if (fadingOut) {
            fadeOut++;
            if (fadeOut > 400) {
                discard();
            }
        }
        if (fullyCharged) {
            fullyChargedTicks++;
            if (fullyChargedTicks > 60) {
                //complete here
                startFading();
            }
        }
        setDeltaMovement(getDeltaMovement().multiply(0.98f, 0.98f, 0.98f));
    }

    @Override
    public boolean canBeTargeted(SparkEntity entity) {
        return !fullyCharged && super.canBeTargeted(entity);
    }

    @Override
    protected void sparkLockedOn(SparkEntity entity) {
        sparksOrbiting++;
        age = 0;
        entity.targetEntity = this;
        entity.isOrbiting = true;
        if (sparksOrbiting == 16) {
            fullyCharged = true;
            List<SparkEntity> entities = level.getEntities(EntityTypeTest.forClass(SparkEntity.class), this.getBoundingBox().inflate(4, 4, 4), e -> this.equals(e.targetEntity));
            for (SparkEntity spark : entities) {
                spark.startFading();
            }
        }
    }

    @Override
    public boolean hasPriority() {
        return !fullyCharged;
    }
}