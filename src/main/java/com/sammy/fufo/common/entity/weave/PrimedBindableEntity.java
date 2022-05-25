package com.sammy.fufo.common.entity.weave;

import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import com.sammy.fufo.core.systems.magic.weaving.Bindable;
import com.sammy.fufo.core.systems.magic.weaving.recipe.ItemStackBindable;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.awt.*;
import java.util.Random;

import static net.minecraft.util.Mth.nextFloat;

public class PrimedBindableEntity extends Entity {
    public Bindable bindable = new Random().nextFloat() < 0.5f ? new ItemStackBindable(Items.DIAMOND.getDefaultInstance()) : new ItemStackBindable(Items.DIRT.getDefaultInstance());

    public PrimedBindableEntity(Level level){
        super(EntityRegistry.PRIMED_BINDABLE.get(), level);
        this.noPhysics = true;
    }

    public PrimedBindableEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    public Bindable getBindable() {
        return this.bindable;
    }

    public void setBindable(Bindable bindable) {
        this.bindable = bindable;
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide) {
            if (bindable != null) {
                Random rand = level.getRandom();
                ParticleBuilders.create(OrtusParticleRegistry.STAR_PARTICLE)
                        .setAlpha(0.05f, 0.15f, 0f)
                        .setAlphaEasing(Easing.QUINTIC_IN, Easing.QUINTIC_OUT)
                        .setLifetime(15 + rand.nextInt(4))
                        .setSpin(nextFloat(rand, 0.01f, 0.05f), 0.02f)
                        .setScale(0.05f, 0.35f + rand.nextFloat() * 0.15f, 0.2f)
                        .setScaleEasing(Easing.SINE_IN, Easing.SINE_OUT)
                        .setColor(new Color(250, 195, 100), new Color(169, 14, 81))
                        .setColorCoefficient(0.5f)
                        .enableNoClip()
                        .spawn(level, this.getX(), this.getY() + 0.5, this.getZ());
                ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
                        .setAlpha(0.01f, 0.08f, 0f)
                        .setAlphaEasing(Easing.QUINTIC_IN, Easing.QUINTIC_OUT)
                        .setLifetime(15 + rand.nextInt(2))
                        .setSpin(nextFloat(rand, 0.05f, 0.1f), 0.2f)
                        .setScale(0.05f, 0.15f + rand.nextFloat() * 0.1f, 0)
                        .setScaleEasing(Easing.SINE_IN, Easing.SINE_OUT)
                        .setColor(new Color(255, 187, 132), new Color(84, 40, 215))
                        .setColorCoefficient(0.8f)
                        .randomOffset(0.04f)
                        .enableNoClip()
                        .randomMotion(0.005f, 0.005f)
                        .spawn(level, this.getX(), this.getY() + 0.5, this.getZ());
            }
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     *
     * @param pCompound
     */
    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public boolean skipAttackInteraction(Entity pEntity) {
        return false;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
