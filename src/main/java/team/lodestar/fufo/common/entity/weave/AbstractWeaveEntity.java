package team.lodestar.fufo.common.entity.weave;

import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import team.lodestar.fufo.core.weaving.StandardWeave;
import team.lodestar.fufo.core.weaving.Weave;
import team.lodestar.fufo.core.weaving.recipe.ItemStackBindable;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;

import java.awt.*;
import java.util.Arrays;

import static net.minecraft.util.Mth.nextFloat;


public abstract class AbstractWeaveEntity extends Entity {

    private static final EntityDataAccessor<CompoundTag> DATA_COMPOUND_TAG = SynchedEntityData.defineId(AbstractWeaveEntity.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Integer> DATA_COLOR_M1 = SynchedEntityData.defineId(AbstractWeaveEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_COLOR_M2 = SynchedEntityData.defineId(AbstractWeaveEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_COLOR_S1 = SynchedEntityData.defineId(AbstractWeaveEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_COLOR_S2 = SynchedEntityData.defineId(AbstractWeaveEntity.class, EntityDataSerializers.INT);

    public Weave<?> weave = new StandardWeave(new ItemStackBindable(Items.AIR.getDefaultInstance()));
    public Color[] mainColors;
    public Color[] secondaryColors;
    public AbstractWeaveEntity(EntityType<?> entity, Level level){
        super(entity, level);
        this.noPhysics = true;
    }

    public void setBaseItemBindable(ItemStackBindable item) {
        this.weave.add(Vec3i.ZERO, item);
        getEntityData().set(DATA_COMPOUND_TAG, weave.serialize());
        this.mainColors = new Color[]{new Color(254, 210, 0), new Color(254, 254, 190)};
        getEntityData().set(DATA_COLOR_M1, mainColors[0].getRGB());
        getEntityData().set(DATA_COLOR_M2, mainColors[1].getRGB());
        this.secondaryColors = new Color[]{new Color(255, 210, 0), new Color(255, 255, 190)};
        getEntityData().set(DATA_COLOR_S1, secondaryColors[0].getRGB());
        getEntityData().set(DATA_COLOR_S2, secondaryColors[1].getRGB());
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_COMPOUND_TAG, new StandardWeave(new ItemStackBindable(Items.AIR.getDefaultInstance())).serialize());
        this.getEntityData().define(DATA_COLOR_M1, 16579836);
        this.getEntityData().define(DATA_COLOR_M2, 16579836);
        this.getEntityData().define(DATA_COLOR_S1, 16579836);
        this.getEntityData().define(DATA_COLOR_S2, 16579836);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if(pKey == DATA_COMPOUND_TAG){
            weave = StandardWeave.deserialize(this.getEntityData().get(DATA_COMPOUND_TAG));
        } else if (pKey == DATA_COLOR_M1){
            mainColors[0] = new Color(this.getEntityData().get(DATA_COLOR_M1));
        } else if (pKey == DATA_COLOR_M2){
            mainColors[1] = new Color(this.getEntityData().get(DATA_COLOR_M2));
        } else if (pKey == DATA_COLOR_S1){
            secondaryColors[0] = new Color(this.getEntityData().get(DATA_COLOR_S1));
        } else if (pKey == DATA_COLOR_S2){
            secondaryColors[1] = new Color(this.getEntityData().get(DATA_COLOR_S2));
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (weave != null) {
                weave.getBindables().forEach(b -> {
                    Vec3i offset = b.getLocation();
                    RandomSource rand = level.getRandom();
                    ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
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
                    ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
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
        this.mainColors[0] = new Color(pCompound.getInt("MainColor1"));
        this.mainColors[1] = new Color(pCompound.getInt("MainColor2"));
        this.secondaryColors[0] = new Color(pCompound.getInt("SecondaryColor1"));
        this.secondaryColors[1] = new Color(pCompound.getInt("SecondaryColor2"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("MainColor1", Arrays.stream(mainColors).findFirst().get().getRGB());
        pCompound.putInt("MainColor2", Arrays.stream(mainColors).skip(1).findFirst().get().getRGB());
        pCompound.putInt("SecondaryColor1", Arrays.stream(secondaryColors).findFirst().get().getRGB());
        pCompound.putInt("SecondaryColor2", Arrays.stream(secondaryColors).skip(1).findFirst().get().getRGB());
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