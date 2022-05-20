package com.sammy.fufo.common.entity.binding;

import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.awt.*;


public class BoundItemEntity extends Entity {
    public static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(BoundItemEntity.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<String> BOUND_UUID = SynchedEntityData.defineId(BoundItemEntity.class, EntityDataSerializers.STRING);
    public BlockPos pos;

    public BoundItemEntity(Level level) {
        super(EntityRegistry.BOUND_ITEM.get(), level);
        this.noPhysics = true;
    }

    public BoundItemEntity(Level level, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        this(level);
        setPos(posX, posY, posZ);
        setDeltaMovement(velX, velY, velZ);
    }

    public BoundItemEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM, ItemStack.EMPTY);
        this.getEntityData().define(BOUND_UUID, "");
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            Color color = new Color(255, 255, 0);
            Color color2 = new Color(0, 182, 182);
            double x = this.pos.getX() + 0.5;
            double y = this.pos.getY() + 0.5;
            double z = this.pos.getZ() + 0.5;
            int lifeTime = 14 + level.random.nextInt(4);
            float scale = 0.17f + level.random.nextFloat() * 0.05f;
            float velocity = 0.04f + level.random.nextFloat() * 0.02f;
            ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
                    .setScale(scale * 2, 0)
                    .setLifetime(lifeTime)
                    .setAlpha(0.8f, 0.5f)
                    .setColor(color, color2)
                    .setColorCoefficient(0.8f)
                    .setAlphaCoefficient(1.5f)
                    .setColorEasing(Easing.CIRC_OUT)
                    .setSpin((level.getGameTime() * 0.2f) % 6.28f)
                    .setSpin(0, 0.4f)
                    .setSpinEasing(Easing.QUARTIC_IN)
                    .addMotion(0, velocity, 0)
                    .enableNoClip()
                    .spawn(level, x, y, z);
        }
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        boolean flag = !this.getItem().isEmpty();
        boolean flag1 = !stack.isEmpty();
        if(!this.level.isClientSide) {
            if (!flag) {
                if (flag1 && this.isRemoved()) {
                    this.setItem(stack);
                    if (!pPlayer.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                }
            }
            return InteractionResult.CONSUME;
        } else {
            return !flag && !flag1 ? InteractionResult.PASS : InteractionResult.SUCCESS;
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        BlockPos blockpos = this.getPos();
        tag.putInt("X", blockpos.getX());
        tag.putInt("Y", blockpos.getY());
        tag.putInt("Z", blockpos.getZ());
        if (!this.getItem().isEmpty()) {
            tag.put("Item", this.getItem().save(new CompoundTag()));
        }
        if (!this.getBoundUUID().isEmpty()) {
            tag.putString("BoundUUID", this.getBoundUUID());
        }
    }

    protected void readAdditionalSaveData(CompoundTag tag) {
        this.pos = new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
        CompoundTag compoundTag = tag.getCompound("Item");
        if (compoundTag != null && !compoundTag.isEmpty()) {
            ItemStack itemstack = ItemStack.of(compoundTag);
            this.setItem(itemstack);
        }

    }

    @Override
    public void kill() {
        super.kill();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    public int getWidth() {
        return 16;
    }

    public int getHeight() {
        return 16;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = 16.0D;
        d0 *= 64.0D * getViewScale();
        return pDistance < d0 * d0;
    }

    public SlotAccess getSlot(int slot) {
        return slot == 0 ? new SlotAccess() {
            @Override
            public ItemStack get() {
                return BoundItemEntity.this.getItem();
            }

            @Override
            public boolean set(ItemStack item) {
                BoundItemEntity.this.setItem(item);
                return true;
            }
        } : super.getSlot(slot);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (key.equals(DATA_ITEM)) {
            ItemStack stack = this.getItem();
            if (!stack.isEmpty()) {
                stack.setEntityRepresentation(this);
            }
        }
    }

    public boolean skipAttackInteraction(Entity entity) {
        // Only return true if the player is holding the binding break item? Not implemented, dont care rn
        return false;
    }

    public ItemStack getItem() {
        return this.getEntityData().get(DATA_ITEM);
    }

    public void setItem(ItemStack item) {
        if (!item.isEmpty()) {
            item = item.copy();
            item.setCount(1);
            item.setEntityRepresentation(this);
        }
        this.getEntityData().set(DATA_ITEM, item);
    }

    public String getBoundUUID() {
        return this.getEntityData().get(BOUND_UUID);
    }

    public void setPos(double x, double y, double z) {
        this.pos = new BlockPos(x, y, z);
    }

    public BlockPos getPos() {
        return this.pos;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}