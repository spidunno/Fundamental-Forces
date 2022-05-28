package com.sammy.fufo.common.entity.weave;

import com.sammy.fufo.core.systems.magic.weaving.StandardWeave;
import com.sammy.fufo.core.systems.magic.weaving.Weave;
import com.sammy.fufo.core.systems.magic.weaving.recipe.ItemStackBindable;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.core.Vec3i;
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


public abstract class AbstractWeaveEntity extends Entity {

    public Weave<?> weave = new StandardWeave(new ItemStackBindable(Items.AIR.getDefaultInstance()));
    public Color[] mainColors;
    public Color[] secondaryColors;
    public AbstractWeaveEntity(EntityType<?> entity, Level level){
        super(entity, level);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData() {

    }
    @Override
    public void tick() {
        if (level.isClientSide) {
            if (weave != null) {
                weave.getBindables().forEach(b -> {
                    Vec3i offset = b.getLocation();
                    Random rand = level.getRandom();
                    ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
                            .setAlpha(0.05f, 0.15f, 0f)
                            .setAlphaEasing(Easing.QUINTIC_IN, Easing.QUINTIC_OUT)
                            .setLifetime(15 + rand.nextInt(4))
                            .setSpin(nextFloat(rand, 0.01f, 0.05f), 0.02f)
                            .setScale(0.05f, 0.35f + rand.nextFloat() * 0.15f, 0.2f)
                            .setScaleEasing(Easing.SINE_IN, Easing.SINE_OUT)
                            .setColor(mainColors[0], mainColors[1])
                            .setColorCoefficient(0.5f)
                            .enableNoClip()
                            .repeat(level, position().x() + offset.getX(), position().y() + offset.getY() + 0.45f, position().z() + offset.getZ(), 1);

                    ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
                            .setAlpha(0.01f, 0.08f, 0f)
                            .setAlphaEasing(Easing.QUINTIC_IN, Easing.QUINTIC_OUT)
                            .setLifetime(15 + rand.nextInt(4))
                            .setSpin(nextFloat(rand, 0.05f, 0.1f), 0.2f)
                            .setScale(0.05f, 0.15f + rand.nextFloat() * 0.1f, 0)
                            .setScaleEasing(Easing.SINE_IN, Easing.SINE_OUT)
                            .setColor(secondaryColors[0], secondaryColors[1])
                            .setColorCoefficient(0.5f)
                            .randomOffset(0.04f)
                            .enableNoClip()
                            .randomMotion(0.005f, 0.005f)
                            .repeat(level, position().x() + offset.getX(), position().y() + offset.getY() + 0.45f, position().z() + offset.getZ(), 1);
                });
                weave.getLinks().forEach((p, t) -> {

                });
            }
        }
        super.tick();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }


    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     *
     * @param pCompound
     */
    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.weave = Weave.deserialize(pCompound.getCompound("Weave"));
    }

    @Override
    public void load(CompoundTag pCompound) {
        super.load(pCompound);
        this.weave = Weave.deserialize(pCompound.getCompound("Weave"));
    }

    @Override
    public boolean save(CompoundTag pCompound) {
        pCompound.put("Weave", weave.serialize());
        return super.save(pCompound);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.put("Weave", weave.serialize());
    }

    @Override
    public boolean skipAttackInteraction(Entity pEntity) {
        // only go through if player is severing the link?
        return false;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}