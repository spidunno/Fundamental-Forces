package com.project_esoterica.esoterica.common.entity.falling;

import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.common.packets.ScreenshakePacket;
import com.project_esoterica.esoterica.core.eventhandlers.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

public abstract class FallingEntity extends Entity {
    private static final float notifyRadius = 200f; // players within this radius receive screenshake upon impact
    private static final float screenshakeFactor = 0.9f;
    private static final float screenshakeFalloff = 0.85f;

    private BlockPos targetBlockPos = BlockPos.ZERO;

    public FallingEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    public FallingEntity(EntityType<?> p_19870, Level p_19871_, BlockPos targetBlockPos) {
        super(p_19870, p_19871_);
        this.targetBlockPos = targetBlockPos;
    }

    @Override
    public void tick() {
        if (targetBlockPos == null)
        {
            kill();
            return;
        }
        Vec3 vel = getDeltaMovement();
        move(MoverType.SELF, getDeltaMovement());
        setDeltaMovement(vel);
        if (level.isClientSide) {
            spawnTrail();
        } else if (blockPosition().getY() <= targetBlockPos.getY()) {
            triggerGlobalScreenshake();
            onImpact();
            kill();
        }

        super.tick();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putIntArray("target", new int[]{targetBlockPos.getX(), targetBlockPos.getY(), targetBlockPos.getZ()});
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        int[] coords = nbt.getIntArray("target");
        targetBlockPos = new BlockPos(coords[0], coords[1], coords[2]);
        setDeltaMovement(position().vectorTo(new Vec3(coords[0], coords[1], coords[2])).normalize().multiply(2.0, 2.0, 2.0));
    }

    // TODO: make farther players experience less screenshake
    private void triggerGlobalScreenshake() {
        Vec3 position = position();
        NetworkManager.INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(position.x, position.y, position.z, notifyRadius, level.dimension())), new ScreenshakePacket(screenshakeFactor, screenshakeFalloff));
    }

    private void spawnTrail() {
        Vec3 position = position();
        Vec3 reverseVel = getDeltaMovement().reverse().normalize().multiply(0.02f, 0.02f, 0.02f);

        int particleCount = 15 + level.random.nextInt(10);
        for (int i = 0; i < particleCount; i++) {
            Vec3 particlePos = position.add(EsotericaHelper.randomUnitVec3().multiply(level.random.nextDouble(), level.random.nextDouble(), level.random.nextDouble()));
            level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, particlePos.x, particlePos.y, particlePos.z, reverseVel.x, reverseVel.y, reverseVel.z);
        }
    }

    protected abstract void onImpact();
}