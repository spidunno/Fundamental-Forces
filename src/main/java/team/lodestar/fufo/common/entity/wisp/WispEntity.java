package team.lodestar.fufo.common.entity.wisp;

import team.lodestar.fufo.registry.common.FufoEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import team.lodestar.lodestone.helpers.DataHelper;

import java.util.List;

public class WispEntity extends AbstractWispEntity {
    public int sparksOrbiting;
    public boolean fullyCharged;
    public int fullyChargedTicks;

    public WispEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public WispEntity(Level level) {
        this(FufoEntities.METEOR_FIRE_WISP.get(), level);
    }

    public WispEntity(Level level, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        super(FufoEntities.METEOR_FIRE_WISP.get(), level, posX, posY, posZ, velX, velY, velZ);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("sparksOrbiting", sparksOrbiting);
        pCompound.putBoolean("fullyCharged", fullyCharged);
        pCompound.putInt("fullyChargedTicks", fullyChargedTicks);
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        sparksOrbiting = pCompound.getInt("sparksOrbiting");
        fullyCharged = pCompound.getBoolean("fullyCharged");
        fullyChargedTicks = pCompound.getInt("fullyChargedTicks");
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(getDeltaMovement().multiply(0.98f, 0.98f, 0.98f));
        if (level.getGameTime() % 25L == 0) {
            setDeltaMovement(0.1f-level.random.nextFloat()*0.2f, 0.1f-level.random.nextFloat()*0.2f, 0.1f-level.random.nextFloat()*0.2f);
        }
    }
}