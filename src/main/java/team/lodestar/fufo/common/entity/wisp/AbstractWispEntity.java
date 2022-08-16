package team.lodestar.fufo.common.entity.wisp;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.awt.*;

public abstract class AbstractWispEntity extends Entity {

    public static final int MAX_AGE = 1200;
    protected static final EntityDataAccessor<Integer> DATA_START_COLOR = SynchedEntityData.defineId(AbstractWispEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> DATA_FADING_OUT = SynchedEntityData.defineId(AbstractWispEntity.class, EntityDataSerializers.BOOLEAN);

    public Color color = Color.WHITE;

    public AbstractWispEntity targetEntity;
    public int findNearestCooldown;
    public int age;
    public boolean fadingOut;
    public float fadeOut;

    public AbstractWispEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public AbstractWispEntity(EntityType<?> type, Level level, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        this(type, level);
        setPos(posX, posY, posZ);
        setDeltaMovement(velX, velY, velZ);
    }

    public void setColor(Color color) {
        this.color = color;
        getEntityData().set(DATA_START_COLOR, color.getRGB());
    }

    public void startFading() {
        this.fadingOut = true;
        getEntityData().set(DATA_FADING_OUT, true);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_START_COLOR, Color.WHITE.getRGB());
        this.getEntityData().define(DATA_FADING_OUT, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_START_COLOR.equals(pKey)) {
            color = new Color(entityData.get(DATA_START_COLOR));
        }
        if (DATA_FADING_OUT.equals(pKey)) {
            fadingOut = entityData.get(DATA_FADING_OUT);
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("age", age);
        pCompound.putInt("color", color.getRGB());
        pCompound.putBoolean("fadingOut", fadingOut);
        pCompound.putFloat("fadeOut", fadeOut);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        age = pCompound.getInt("age");
        setColor(new Color(pCompound.getInt("color")));
        fadingOut = pCompound.getBoolean("fadingOut");
        fadeOut = pCompound.getFloat("fadeOut");
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 movement = getDeltaMovement();
        setPos(getX() + movement.x, getY() + movement.y, getZ() + movement.z);
        age++;
        if (level.isClientSide) {
            return;
        }
        if (age >= MAX_AGE) {
            discard();
        }
    }

    public boolean hasPriority() {
        return false;
    }

    public boolean canBeTargeted(SparkEntity entity) {
        return !fadingOut && !entity.fadingOut;
    }

    protected abstract void sparkLockedOn(SparkEntity entity);

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}