package com.project_esoterica.esoterica.common.entity;

import com.project_esoterica.esoterica.core.registry.EntityRegistry;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
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


public class BibitEntity extends PathfinderMob implements IAnimatable {

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 50.0D).add(Attributes.ARMOR, 1000F).add(Attributes.MOVEMENT_SPEED, 0.15F).build();
    }

    public enum bibitStateEnum {
        IDLE("idle"), PANICKED("panicked"), UPSET("upset"), OVERJOYED("overjoyed"), JEB_("jeb_"), PROUD("proud"), COOL("cool"), QUBIT("qubit"), SUS("sus"), MISSING("missing"),
        ;
        public String stateIdentifier;

        bibitStateEnum(String feelingsValue) {
            this.stateIdentifier = feelingsValue;
        }

        public static bibitStateEnum getFeelingsValue(String feelingsValue) {
            for (bibitStateEnum feelings : values()) {
                if (feelings.stateIdentifier.equals(feelingsValue)) {
                    return feelings;
                }
            }
            return IDLE;
        }
    }

    private static final EntityDataAccessor<String> ACTUAL_STATE = SynchedEntityData.defineId(BibitEntity.class, EntityDataSerializers.STRING);
    public bibitStateEnum actualState = bibitStateEnum.IDLE;
    private static final EntityDataAccessor<String> VISUAL_STATE = SynchedEntityData.defineId(BibitEntity.class, EntityDataSerializers.STRING);
    public bibitStateEnum visualState = bibitStateEnum.IDLE;
    private int stateUpdateLock = 0;
    private final AnimationFactory factory = new AnimationFactory(this);

    public BibitEntity(Level p_21684_) {
        super(EntityRegistry.BIBIT.get(), p_21684_);
        this.noCulling = true;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(ACTUAL_STATE, bibitStateEnum.IDLE.stateIdentifier);
        this.getEntityData().define(VISUAL_STATE, bibitStateEnum.IDLE.stateIdentifier);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this) {
            @Override
            public void start() {
                forceState(bibitStateEnum.UPSET, 300);
                super.start();
            }
        });
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D) {
            @Override
            public void start() {
                setState(bibitStateEnum.PANICKED, 600);
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
        actualState = bibitStateEnum.getFeelingsValue(getEntityData().get(ACTUAL_STATE));
        visualState = bibitStateEnum.getFeelingsValue(getEntityData().get(VISUAL_STATE));
    }

    @Override
    public void setCustomName(@Nullable Component p_20053_) {
        super.setCustomName(p_20053_);
        if (!visualState.equals(actualState))
        {
            return;
        }
        for (bibitStateEnum feelings : bibitStateEnum.values()) {
            if (p_20053_.getString().equals(feelings.stateIdentifier)) {
                setVisualState(feelings);
            }
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (stateUpdateLock > 0) {
            stateUpdateLock--;
        } else {
            setState(bibitStateEnum.IDLE, 0);
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putString("feelings", actualState.stateIdentifier);
        tag.putInt("lockExpression", stateUpdateLock);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        forceState(bibitStateEnum.valueOf(tag.getString("expression")), tag.getInt("lockExpression"));
    }

    public void setState(bibitStateEnum feelings, int lockExpression) {
        if (this.stateUpdateLock == 0) {
            forceState(feelings, lockExpression);
        }
    }

    public void setVisualState(bibitStateEnum feelings) {
        this.getEntityData().set(VISUAL_STATE, Util.make(() -> feelings.stateIdentifier));
    }

    public void forceState(bibitStateEnum state, int lockExpression) {
        if (this.stateUpdateLock == -1) {
            return;
        }
        this.stateUpdateLock = lockExpression;
        if (actualState.equals(visualState)) {
            this.visualState = state;
            this.getEntityData().set(VISUAL_STATE, Util.make(() -> state.stateIdentifier));
        }
        this.actualState = state;
        this.getEntityData().set(ACTUAL_STATE, Util.make(() -> state.stateIdentifier));
    }

    private <E extends IAnimatable> PlayState chooseAnimation(AnimationEvent<E> event) {
        if (getDeltaMovement().x() == 0 && getDeltaMovement().z() == 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bibit.idle", true));
        } else {
            if (actualState.equals(bibitStateEnum.PANICKED)) {
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