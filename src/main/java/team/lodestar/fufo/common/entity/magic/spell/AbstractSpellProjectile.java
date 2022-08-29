package team.lodestar.fufo.common.entity.magic.spell;

import team.lodestar.fufo.core.element.MagicElement;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import team.lodestar.fufo.registry.common.magic.FufoMagicElements;

import java.awt.*;

public class AbstractSpellProjectile extends Projectile {
    protected static final EntityDataAccessor<Integer> DATA_COLOR = SynchedEntityData.defineId(AbstractSpellProjectile.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_END_COLOR = SynchedEntityData.defineId(AbstractSpellProjectile.class, EntityDataSerializers.INT);

    public MagicElement element;
    public Color startColor = Color.WHITE;
    public Color endColor = Color.BLACK;
    public int lifetime;
    public int age;

    public AbstractSpellProjectile(EntityType<? extends Projectile> entity, Level level) {
        super(entity, level);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_COLOR, Color.WHITE.getRGB());
        this.getEntityData().define(DATA_END_COLOR, Color.BLACK.getRGB());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_COLOR.equals(pKey)) {
            startColor = new Color(entityData.get(DATA_COLOR));
        }
        if (DATA_END_COLOR.equals(pKey)) {
            endColor = new Color(entityData.get(DATA_END_COLOR));
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("elementType", element.id.toString());
        compound.putInt("age", age);
        compound.putInt("lifetime", lifetime);
        compound.putInt("start", startColor.getRGB());
        compound.putInt("end", endColor.getRGB());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        element = FufoMagicElements.ELEMENTS.get(new ResourceLocation(compound.getString("elementType")));
        age = compound.getInt("age");
        lifetime = compound.getInt("lifetime");
        startColor = new Color(compound.getInt("start"));
        endColor = new Color(compound.getInt("end"));
    }

    public AbstractSpellProjectile setLifetime(int lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    public AbstractSpellProjectile setElement(MagicElement element) {
        this.element = element;
        return this;
    }

    public AbstractSpellProjectile setColor(Color color, Color endColor) {
        this.startColor = color;
        getEntityData().set(DATA_COLOR, color.getRGB());
        this.endColor = endColor;
        getEntityData().set(DATA_END_COLOR, endColor.getRGB());
        return this;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 *= 64.0D;
        return pDistance < d0 * d0;
    }

    @Override
    public void tick() {
        baseTick();
        age++;
        if (age > lifetime) {
            this.discard();
        }
    }

    public void baseTick() {
        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }
        BlockHitResult result = level.clip(new ClipContext(position(), position().add(getDeltaMovement()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (result.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = result.getBlockPos();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                BlockEntity blockentity = this.level.getBlockEntity(blockpos);
                if (blockentity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level, blockpos, blockstate, this, (TheEndGatewayBlockEntity) blockentity);
                }
            }
        }
        this.checkInsideBlocks();
        Vec3 movement = this.getDeltaMovement();
        double nextX = this.getX() + movement.x;
        double nextY = this.getY() + movement.y;
        double nextZ = this.getZ() + movement.z;
        double distance = movement.horizontalDistance();
        this.setXRot(lerpRotation(this.xRotO, (float) (Mth.atan2(movement.y, distance) * (double) (180F / (float) Math.PI))));
        this.setYRot(lerpRotation(this.yRotO, (float) (Mth.atan2(movement.x, movement.z) * (double) (180F / (float) Math.PI))));
        this.setPos(nextX, nextY, nextZ);
        ProjectileUtil.rotateTowardsMovement(this, 0.2F);
    }


    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 0.1F;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}