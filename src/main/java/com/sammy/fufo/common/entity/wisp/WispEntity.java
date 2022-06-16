package com.sammy.fufo.common.entity.wisp;

import com.sammy.fufo.core.registratation.ItemRegistrate;
import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import com.sammy.ortus.helpers.ColorHelper;
import com.sammy.ortus.helpers.EntityHelper;
import com.sammy.ortus.systems.easing.Easing;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WispEntity extends Entity {

    protected static final int FULL_SIZE = 16;
    public static final int MAX_AGE = 300;
    protected static final EntityDataAccessor<Integer> DATA_START_COLOR = SynchedEntityData.defineId(WispEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_MID_COLOR = SynchedEntityData.defineId(WispEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_END_COLOR = SynchedEntityData.defineId(WispEntity.class, EntityDataSerializers.INT);
    public Color startColor = Color.WHITE;
    public Color midColor = Color.GRAY;
    public Color endColor = Color.BLACK;

    public WispEntity nearestEntity;
    public int findNearestCooldown;
    public int size = 120;
    public int count;
    public int age;
    public final ArrayList<EntityHelper.PastPosition> pastPositions = new ArrayList<>();

    public WispEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public WispEntity(Level level) {
        super(EntityRegistry.WISP.get(), level);
        this.noPhysics = true;
    }

    public WispEntity(Level level, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        this(level);
        setPos(posX, posY, posZ);
        setDeltaMovement(velX, velY, velZ);
    }

    public void setColor(Color color, Color midColor, Color endColor) {
        this.startColor = color;
        getEntityData().set(DATA_START_COLOR, color.getRGB());
        this.midColor = midColor;
        getEntityData().set(DATA_MID_COLOR, midColor.getRGB());
        this.endColor = endColor;
        getEntityData().set(DATA_END_COLOR, endColor.getRGB());
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_START_COLOR, Color.WHITE.getRGB());
        this.getEntityData().define(DATA_MID_COLOR, Color.GRAY.getRGB());
        this.getEntityData().define(DATA_END_COLOR, Color.BLACK.getRGB());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_START_COLOR.equals(pKey)) {
            startColor = new Color(entityData.get(DATA_START_COLOR));
        }
        if (DATA_MID_COLOR.equals(pKey)) {
            endColor = new Color(entityData.get(DATA_MID_COLOR));
        }
        if (DATA_END_COLOR.equals(pKey)) {
            endColor = new Color(entityData.get(DATA_END_COLOR));
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("size", size);
        pCompound.putInt("count", count);
        pCompound.putInt("age", age);

        pCompound.putInt("start", startColor.getRGB());
        pCompound.putInt("mid", midColor.getRGB());
        pCompound.putInt("end", endColor.getRGB());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        size = pCompound.getInt("size");
        count = pCompound.getInt("count");
        age = pCompound.getInt("age");

        startColor = new Color(pCompound.getInt("start"));
        midColor = new Color(pCompound.getInt("mid"));
        endColor = new Color(pCompound.getInt("end"));
    }

    // this doesnt work btw
    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if(!level.isClientSide){
            if(player.getItemInHand(hand).getItem() instanceof BottleItem){
                player.setItemInHand(hand, new ItemStack(ItemRegistrate.WISP_BOTTLE.get()));
                this.discard();
                return InteractionResult.sidedSuccess(false);
            }
        }
        return InteractionResult.sidedSuccess(true);
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
        if (count == 0 && age >= MAX_AGE) {
            //discard();
        }
        if (age <= 60 || count > 0) {
            setDeltaMovement(getDeltaMovement().multiply(0.97f, 0.9f, 0.97f));
        }
        if (nearestEntity != null && nearestEntity.isAlive()) {
            float distanceTo = nearestEntity.distanceTo(this);
            float age = Math.min(10, this.age / 10f);
            float speed = age * 0.04f;
            float easing = 0.02f + age * 0.001f;

            if (distanceTo <= 0.2f) {
                speed *= 1.1f;
                easing *= 1.1f;
            }
            Vec3 desiredMotion = nearestEntity.position().subtract(position()).normalize().multiply(speed * 0.2f, speed, speed * 0.2f);
            float xMotion = (float) Mth.lerp(easing, getDeltaMovement().x, desiredMotion.x);
            float yMotion = (float) Mth.lerp(easing, getDeltaMovement().y, desiredMotion.y);
            float zMotion = (float) Mth.lerp(easing, getDeltaMovement().z, desiredMotion.z);
            if (yMotion < 0) {
                yMotion *= 0.35f;
            }
            Vec3 resultingMotion = new Vec3(xMotion, yMotion, zMotion);
            setDeltaMovement(resultingMotion);
            if (distanceTo <= 0.15f) {
                merge(nearestEntity);
            }
            return;
        }
        if (findNearestCooldown == 0) {
            if (level instanceof ServerLevel) {
                List<WispEntity> entities = level.getEntities(EntityTypeTest.forClass(WispEntity.class), this.getBoundingBox().inflate(6), this::canMerge);
                entities.remove(this);
                WispEntity nearestEntity = null;
                double distance = 621;
                for (WispEntity entity : entities) {
                    float distanceTo = entity.distanceTo(this);
                    if (distanceTo < distance) {
                        nearestEntity = entity;
                        distance = distanceTo;
                    }
                }
                this.nearestEntity = nearestEntity;
                if (this.nearestEntity != null) {
                    this.nearestEntity.nearestEntity = this;
                }
            }
            findNearestCooldown = 4;
        } else {
            findNearestCooldown--;
        }
    }

    public boolean canMerge(WispEntity entity) {
        if (nearestEntity != null && nearestEntity.isAlive()) {
            return false;
        }
        return entity.count < FULL_SIZE;
    }

    private void merge(WispEntity entity) {
        count = Math.max(count + entity.count, 1);
        age = Math.min(age, entity.age);
        size = Math.min(size, entity.size) + 40;
        findNearestCooldown = 10;
        entity.discard();
    }

    protected static float lerpRotation(float p_37274_, float p_37275_) {
        while (p_37275_ - p_37274_ < -180.0F) {
            p_37274_ -= 360.0F;
        }

        while (p_37275_ - p_37274_ >= 180.0F) {
            p_37274_ += 360.0F;
        }

        return Mth.lerp(0.5F, p_37274_, p_37275_);
    }

    public Color getColor() {
        return ColorHelper.multicolorLerp(Easing.LINEAR, (float) age / (float) MAX_AGE, startColor, midColor, endColor);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}