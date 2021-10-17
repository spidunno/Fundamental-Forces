package com.project_esoterica.esoterica.common.entity;

import com.project_esoterica.esoterica.core.registry.EntityRegistry;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class Bibit extends PathfinderMob implements IAnimatable {

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 50.0D).add(Attributes.ARMOR, 1000F).add(Attributes.MOVEMENT_SPEED, 0.15F).build();
    }

    public enum feelingsEnum {
        IDLE("idle"), PANICKED("panicked"), UPSET("upset"), OVERJOYED("overjoyed"),;
        public String feelingsValue;

        feelingsEnum(String feelingsValue) {
            this.feelingsValue = feelingsValue;
        }
        public static feelingsEnum getFeelingsValue(String feelingsValue)
        {
            for (feelingsEnum feelings : values())
            {
                if (feelings.feelingsValue.equals(feelingsValue))
                {
                    return feelings;
                }
            }
            return IDLE;
        }
    }

    private static final EntityDataAccessor<String> FEELINGS_DATA = SynchedEntityData.defineId(Bibit.class, EntityDataSerializers.STRING);
    public feelingsEnum feelings = feelingsEnum.IDLE;
    private int lockExpression = 0;
    private final AnimationFactory factory = new AnimationFactory(this);

    public Bibit(Level p_21684_) {
        super(EntityRegistry.BIBIT.get(), p_21684_);
        this.noCulling = true;
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bibit.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(FEELINGS_DATA, feelingsEnum.IDLE.feelingsValue);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this) {
            @Override
            public void start() {
                overrideFeelings(feelingsEnum.UPSET, 600);
                super.start();
            }
        });
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D) {
            @Override
            public void start() {
                overrideFeelings(feelingsEnum.PANICKED, 600);
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
    public void tick() {
        super.tick();
        if (level.isClientSide)
        {
            feelings = feelingsEnum.getFeelingsValue(getEntityData().get(FEELINGS_DATA));
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (lockExpression > 0) {
            lockExpression--;
        } else {
            setFeelings(feelingsEnum.IDLE, 500);
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putString("feelings", feelings.feelingsValue);
        tag.putInt("lockExpression", lockExpression);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        overrideFeelings(feelingsEnum.valueOf(tag.getString("expression")), tag.getInt("lockExpression"));
    }
    public void setFeelings(feelingsEnum feelings, int lockExpression) {
        if (lockExpression == 0) {
            overrideFeelings(feelings, lockExpression);
        }
    }
    public void overrideFeelings(feelingsEnum feelings, int lockExpression) {
        this.feelings = feelings;
        this.lockExpression = lockExpression;
        this.getEntityData().set(FEELINGS_DATA, Util.make(()->feelings.feelingsValue));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}