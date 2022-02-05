package com.project_esoterica.esoterica.common.entity.robot;

import com.project_esoterica.esoterica.core.setup.EntityRegistry;
import com.project_esoterica.esoterica.core.setup.client.ParticleRegistry;
import com.project_esoterica.esoterica.core.systems.rendering.RenderUtilities;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.awt.*;


public class BibitEntity extends PathfinderMob implements IAnimatable {

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 50.0D).add(Attributes.ARMOR, 1000F).add(Attributes.MOVEMENT_SPEED, 0.15F).build();
    }

    public enum BibitState {
        IDLE("idle"), PANICKED("panicked"), UPSET("upset"), OVERJOYED("overjoyed"), JEB_("jeb_"), PROUD("proud"), COOL("cool"), QUBIT("qubit"), SUS("sus"), MISSING("missing");

        public String stateIdentifier;

        BibitState(String feelingsValue) {
            this.stateIdentifier = feelingsValue;
        }

        public static BibitState getFeelingsValue(String feelingsValue) {
            for (BibitState feelings : values()) {
                if (feelings.stateIdentifier.equals(feelingsValue)) {
                    return feelings;
                }
            }
            return IDLE;
        }
    }

    private static final EntityDataAccessor<String> STATE = SynchedEntityData.defineId(BibitEntity.class, EntityDataSerializers.STRING);
    public BibitState state = BibitState.IDLE;
    private static final EntityDataAccessor<String> VISUAL_STATE = SynchedEntityData.defineId(BibitEntity.class, EntityDataSerializers.STRING);
    public BibitState visualState = BibitState.IDLE;
    private int preventStateChanges = 0;
    private final AnimationFactory factory = new AnimationFactory(this);

    public BibitEntity(Level p_21684_) {
        super(EntityRegistry.BIBIT.get(), p_21684_);
        this.noCulling = true;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(STATE, BibitState.IDLE.stateIdentifier);
        this.getEntityData().define(VISUAL_STATE, BibitState.IDLE.stateIdentifier);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this) {
            @Override
            public void start() {
                forceState(BibitState.UPSET, 300);
                super.start();
            }
        });
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D) {
            @Override
            public void start() {
                setState(BibitState.PANICKED, 600);
                super.start();
            }

            @Override
            public void stop() {
                super.stop();
            }
        });
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
        super.onSyncedDataUpdated(p_21104_);
        state = BibitState.getFeelingsValue(getEntityData().get(STATE));
        visualState = BibitState.getFeelingsValue(getEntityData().get(VISUAL_STATE));
    }

    @Override
    public void setCustomName(@Nullable Component p_20053_) {
        super.setCustomName(p_20053_);
        for (BibitState states : BibitState.values()) {
            if (p_20053_.getString().equals(states.stateIdentifier)) {
                setVisualState(states);
                return;
            }
        }
        setVisualState(BibitState.IDLE);
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide)
        {
            RenderUtilities.create(ParticleRegistry.WISP)
                    .setAlpha(0.5f, 0f)
                    .setLifetime(20 + level.random.nextInt(4))
                    .setSpin(Mth.nextFloat(level.random, 0.05f, 0.1f))
                    .setScale(0.2f + level.random.nextFloat() * 0.05f, 0)
                    .setColor(new Color(54, 54, 227), new Color(233, 197, 255).darker())
                    .randomOffset(0.1f)
                    .enableNoClip()
                    .randomVelocity(0.02f, 0.02f)
                    .repeat(level, getX(), getY()+2, getZ(), 1);
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (preventStateChanges > 0) {
            preventStateChanges--;
        } else {
            setState(BibitState.IDLE, 0);
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putString("state", state.stateIdentifier);
        tag.putInt("preventStateChanges", preventStateChanges);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        forceState(BibitState.valueOf(tag.getString("state")), tag.getInt("preventStateChanges"));
    }

    public void setState(BibitState feelings, int lockExpression) {
        if (this.preventStateChanges == 0) {
            forceState(feelings, lockExpression);
        }
    }

    public void setVisualState(BibitState state) {
        this.visualState = state;
        this.getEntityData().set(VISUAL_STATE, Util.make(() -> state.stateIdentifier));
    }

    public void forceState(BibitState state, int lockExpression) {
        if (this.preventStateChanges == -1) {
            return;
        }
        this.preventStateChanges = lockExpression;
        this.state = state;
        this.getEntityData().set(STATE, Util.make(() -> state.stateIdentifier));
    }

    private <E extends IAnimatable> PlayState chooseAnimation(AnimationEvent<E> event) {
        if (getDeltaMovement().x() == 0 && getDeltaMovement().z() == 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bibit.idle", true));
        } else {
            if (state.equals(BibitState.PANICKED)) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bibit.run", true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bibit.walk", true));
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::chooseAnimation));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}