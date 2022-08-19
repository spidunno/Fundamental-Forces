package team.lodestar.fufo.common.entity.wraith;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.registry.common.FufoEntities;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimatedEntity;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimationEffectHandler;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimationState;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.stream.Stream;

public class StoneWraith extends Monster implements AnimatedEntity {

    public static final AnimationState VIBING = new AnimationState(80, FufoMod.fufoPath("stone_wraith.idle"), FufoMod.fufoPath("stone_wraith.twitchlayer"));
    public static final AnimationState WALKING = new AnimationState(25, FufoMod.fufoPath("stone_wraith.walk"), FufoMod.fufoPath("stone_wraith.twitchlayer"));
    private static final AnimationState[] ANIMATIONS = Stream.of(VIBING, WALKING).toArray(AnimationState[]::new);

    private final AnimationEffectHandler effectHandler;
    private AnimationState animationState;
    private int animationTick;

    public StoneWraith(Level level) {
        super(FufoEntities.STONE_WRAITH.get(), level);
        this.effectHandler = new AnimationEffectHandler(this);
        this.animationState = VIBING;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0D).add(Attributes.MOVEMENT_SPEED, 0.2F).add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.ARMOR, 4.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 15.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, StoneWraith.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        this.animationTick();
    }

    @Override
    public void resetAnimationState() {
        this.setAnimationState(VIBING);
    }

    @Override
    public void setAnimationState(AnimationState state) {
        this.onAnimationStop(this.animationState);
        this.animationState = state;
        this.setAnimationTick(0);
    }

    @Override
    public void setTransitionAnimationState(AnimationState state) {

    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource arg) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public int getAnimationTick() {
        return this.animationTick;
    }

    @Override
    public int getAnimationTransitionTick() {
        return 0;
    }

    @Override
    public int getAnimationTransitionLength() {
        return 0;
    }

    @Override
    public AnimationState getAnimationState() {
        return this.animationState;
    }

    @Override
    public AnimationState getTransitionAnimationState() {
        return null;
    }

    @Override
    public AnimationEffectHandler getAnimationEffects() {
        return effectHandler;
    }

    @Override
    public void setAnimationTick(int animationTick) {
        this.animationTick = animationTick;
    }

    @Override
    public void setAnimationTransitionTick(int transitionTick) {

    }

    @Override
    public void setAnimationTransitionLength(int transitionLength) {

    }

    @Override
    public AnimationState[] getAnimationStates() {
        return ANIMATIONS;
    }
}