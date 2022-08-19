package team.lodestar.fufo.common.entity.wisp;

import team.lodestar.fufo.registry.common.FufoEntities;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.helpers.EntityHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class SparkEntity extends AbstractWispEntity {
    public final ArrayList<EntityHelper.PastPosition> pastPositions = new ArrayList<>();

    public float distanceMultiplier = 0.75f + random.nextFloat();
    public float speedMultiplier = 0.5f + random.nextFloat()*0.5f;
    public float rotationOffset = random.nextFloat() * 360;
    public boolean isOrbiting;

    public SparkEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public SparkEntity(Level level) {
        this(FufoEntities.METEOR_FIRE_SPARK.get(), level);
    }

    public SparkEntity(Level level, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        super(FufoEntities.METEOR_FIRE_SPARK.get(), level, posX, posY, posZ, velX, velY, velZ);
        findTarget();
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putBoolean("isOrbiting", isOrbiting);
        pCompound.putFloat("distanceMultiplier", distanceMultiplier);
        pCompound.putFloat("rotationOffset", rotationOffset);
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        isOrbiting = pCompound.getBoolean("isOrbiting");
        distanceMultiplier = pCompound.getFloat("distanceMultiplier");
        rotationOffset = pCompound.getFloat("rotationOffset");
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    public void tick() {
        super.tick();
        float friction = fadingOut ? 0.97f : 0.995f - (Math.max(0, 10 - age) / 10f) * 0.2f;
        setDeltaMovement(getDeltaMovement().multiply(friction, friction, friction));
        trackPastPositions();

        if (fadingOut) {
            fadeOut++;
            if (fadeOut < 20) {
                distanceMultiplier += 0.05f;
            }
            if (fadeOut < 100) {
                distanceMultiplier += 0.01f;
            }
            if (fadeOut > 400) {
                discard();
            }
        }
        if (level.isClientSide) {
            return;
        }
        if (targetEntity != null && targetEntity.isAlive()) {
            followTarget();
        }
        if (!fadingOut && !isOrbiting) {
            if (findNearestCooldown == 0) {
                findTarget();
                findNearestCooldown = 5;
            } else {
                findNearestCooldown--;
            }
        }
    }

    @Override
    protected void sparkLockedOn(SparkEntity entity) {
        WispEntity wisp = createWisp(entity);
        wisp.sparkLockedOn(this);
        wisp.sparkLockedOn(entity);
    }

    protected WispEntity createWisp(SparkEntity entity) {
        Vec3 wispPosition = entity.position().add(position()).multiply(0.5f, 0.5f, 0.5f);
        WispEntity wispEntity = new WispEntity(level, wispPosition.x, wispPosition.y + 1f, wispPosition.z, 0, 0.02f, 0);
        wispEntity.setColor(entity.color);
        level.addFreshEntity(wispEntity);
        return wispEntity;
    }

    public void trackPastPositions() {
        removeOldPositions(pastPositions);
        EntityHelper.trackPastPositions(pastPositions, position(), 0.01f);
    }

    public void removeOldPositions(ArrayList<EntityHelper.PastPosition> pastPositions) {
        int amount = pastPositions.size() - 1;
        ArrayList<EntityHelper.PastPosition> toRemove = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            EntityHelper.PastPosition excess = pastPositions.get(i);
            if (excess.time > 10) {
                toRemove.add(excess);
            }
        }
        pastPositions.removeAll(toRemove);
    }

    public void followTarget() {
        float windUp = Math.min(30, this.age / 30f);
        float speed = windUp * 0.035f*speedMultiplier;
        float easing = 0.08f;

        Vec3 positionOffset = targetEntity.position().subtract(position());
        if (targetEntity instanceof WispEntity wispEntity) {
            float multiplier = (wispEntity.sparksOrbiting / 24f);
            float offsetScale = Math.max(0.6f, distanceMultiplier * (1 - multiplier));
            speed *= (1 - multiplier);
            if (offsetScale != 0) {
                positionOffset = DataHelper.rotatingRadialOffset(positionOffset, offsetScale, 0, 1, (long) (level.getGameTime() + rotationOffset), 40+40*(1-speedMultiplier));
            }
        }
        Vec3 desiredMotion = positionOffset.normalize().multiply(speed, speed, speed);
        float xMotion = (float) Mth.lerp(easing, getDeltaMovement().x, desiredMotion.x);
        float yMotion = (float) Mth.lerp(easing, getDeltaMovement().y, desiredMotion.y);
        float zMotion = (float) Mth.lerp(easing, getDeltaMovement().z, desiredMotion.z);
        Vec3 resultingMotion = new Vec3(xMotion, yMotion, zMotion);
        setDeltaMovement(resultingMotion);
    }

    public void findTarget() {
        if (level instanceof ServerLevel) {
            float range = 4;
            List<AbstractWispEntity> entities = level.getEntities(EntityTypeTest.forClass(AbstractWispEntity.class), this.getBoundingBox().inflate(range, range*4, range), e -> e.canBeTargeted(this));
            entities.remove(this);
            AbstractWispEntity nearestEntity = null;
            float windUp = Math.min(20, this.age / 20f);
            double distance = (0.2f + windUp * 2f) * distanceMultiplier;
            boolean foundPriorityTarget = entities.stream().anyMatch(AbstractWispEntity::hasPriority);
            if (targetEntity != null && targetEntity.isAlive() && !foundPriorityTarget) {
                return;
            }
            for (AbstractWispEntity entity : entities) {
                if (foundPriorityTarget && !entity.hasPriority()) {
                    continue;
                }
                if (entity.canBeTargeted(this)) {
                    float distanceTo = entity.distanceTo(this);
                    if (distanceTo < distance) {
                        nearestEntity = entity;
                        distance = distanceTo;
                    }
                }
            }
            targetEntity = nearestEntity;
            if (targetEntity != null) {
                targetEntity.sparkLockedOn(this);
            }
        }
    }
}