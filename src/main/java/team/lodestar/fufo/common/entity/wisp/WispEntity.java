package team.lodestar.fufo.common.entity.wisp;

import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import team.lodestar.fufo.registry.client.FufoParticles;
import team.lodestar.fufo.registry.common.FufoEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import team.lodestar.fufo.registry.common.FufoItems;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.helpers.ItemHelper;
import team.lodestar.lodestone.helpers.NBTHelper;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;

import java.awt.*;

import static team.lodestar.lodestone.systems.rendering.particle.SimpleParticleOptions.Animator.WITH_AGE;

public class WispEntity extends AbstractWispEntity {

    protected Vec3 gravityCenter = Vec3.ZERO;
    protected int timeOffset = random.nextInt(200);
    public WispEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public WispEntity(Level level) {
        this(FufoEntities.METEOR_FIRE_WISP.get(), level);
    }

    public WispEntity(Level level, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        super(FufoEntities.METEOR_FIRE_WISP.get(), level, posX, posY, posZ, velX, velY, velZ);
        gravityCenter = new Vec3(posX+level.random.nextFloat()-0.5f, posY+2.5f+level.random.nextInt(2), posZ+level.random.nextFloat()-0.5f);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putDouble("gravityCenterX", gravityCenter.x);
        pCompound.putDouble("gravityCenterY", gravityCenter.y);
        pCompound.putDouble("gravityCenterZ", gravityCenter.z);
        pCompound.putInt("timeOffset", timeOffset);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        gravityCenter = new Vec3(pCompound.getDouble("gravityCenterX"),pCompound.getDouble("gravityCenterY"),pCompound.getDouble("gravityCenterZ"));
        timeOffset = pCompound.getInt("timeOffset");
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.getItem().equals(Items.GLASS_BOTTLE)) {
            if (!pPlayer.isCreative()) {
                stack.shrink(1);
            }
            pPlayer.playSound(SoundEvents.BOTTLE_FILL, 1, 1);
            ItemHelper.giveItemToEntity(FufoItems.CRACK.asStack(), pPlayer);
            discard();
            return InteractionResult.SUCCESS;
        }
        return super.interact(pPlayer, pHand);
    }

    @Override
    public void tick() {
        super.tick();
        long time = (level.getGameTime() + timeOffset);
        float sine = Mth.sin(((time*1.5f) % 200L) / 200f) * 0.01f;
        float velocityFactor = 0.1f+ sine;
        setDeltaMovement(getDeltaMovement().subtract(0, 0.002f, 0).multiply(0.98f, 0.96f, 0.98f));
        Vec3 target = DataHelper.rotatingRadialOffset(gravityCenter, 1.5f, 0, 1, time, 200);
        Vec3 velocity = target.subtract(position()).normalize().multiply(velocityFactor, velocityFactor, velocityFactor);
        float xMotion = (float) Mth.lerp(0.02f, getDeltaMovement().x, velocity.x);
        float yMotion = (float) Mth.lerp(0.04f, getDeltaMovement().y, velocity.y);
        float zMotion = (float) Mth.lerp(0.03f, getDeltaMovement().z, velocity.z);
        Vec3 resultingMotion = new Vec3(xMotion, yMotion+sine, zMotion);
        setDeltaMovement(resultingMotion);

        if (level.isClientSide) {
            int amount = 1 + random.nextInt(3);
            for (int i = 0; i < amount; i++) {
                float colorTilt = (0.6f + random.nextFloat() * 0.4f) / 255f;
                int lifetime = (int) ((double) 8 / ((double) random.nextFloat() * 0.8D + 0.2D));
                int spinDirection = (random.nextBoolean() ? 1 : -1);
                int spinOffset = random.nextInt(360);
                float spinStrength = 0.5f + random.nextFloat() * 0.25f;
                Color startingColor = new Color(219 * colorTilt, 88 * colorTilt, 239 * colorTilt);
                Color endingColor = new Color(108 * colorTilt, 38 * colorTilt, 96 * colorTilt).brighter();

                ParticleBuilders.create(FufoParticles.SQUARE)
                        .randomOffset(0.02f)
                        .setScale(0.1f, 0f)
                        .setScaleEasing(Easing.SINE_IN)
                        .setScaleCoefficient(1.25f)
                        .setAlpha(0f, 0.5f, 0)
                        .setAlphaEasing(Easing.CUBIC_OUT, Easing.SINE_IN)
                        .setSpinOffset(spinOffset)
                        .setSpin(0, spinStrength * spinDirection, 0)
                        .setSpinEasing(Easing.SINE_IN, Easing.CUBIC_OUT)
                        .setSpinCoefficient(1.5f)
                        .setLifetime(lifetime)
                        .setColor(startingColor, endingColor)
                        .randomMotion(0.01f)
                        .enableNoClip()
                        .spawn(level, getX(), getY(), getZ());
            }
        }
    }
}