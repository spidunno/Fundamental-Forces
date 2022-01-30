package com.project_esoterica.esoterica.common.capability;

import com.project_esoterica.esoterica.common.packets.SyncEntityCapabilityDataPacket;
import com.project_esoterica.esoterica.core.helper.DataHelper;
import com.project_esoterica.esoterica.core.systems.capability.SimpleCapability;
import com.project_esoterica.esoterica.core.systems.capability.SimpleCapabilityProvider;
import com.project_esoterica.esoterica.core.systems.meteorfire.MeteorFireInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;

import static com.project_esoterica.esoterica.core.setup.PacketRegistry.INSTANCE;

public class EntityDataCapability implements SimpleCapability {

    public static Capability<EntityDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public MeteorFireInstance meteorFireInstance;

    public EntityDataCapability() {

    }

    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        final EntityDataCapability capability = new EntityDataCapability();
        event.addCapability(DataHelper.prefix("entity_data"), new SimpleCapabilityProvider<>(EntityDataCapability.CAPABILITY, ()->capability));
    }
    public static void syncEntityCapability(PlayerEvent.StartTracking event) {
        if (event.getEntity().level instanceof ServerLevel) {
            EntityDataCapability.syncTracking(event.getEntityLiving());
        }
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (meteorFireInstance != null) {
            meteorFireInstance.serializeNBT(tag);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        meteorFireInstance = MeteorFireInstance.deserializeNBT(tag);
    }

    public static void syncTrackingAndSelf(Entity entity) {
        sync(entity, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity));
    }
    public static void syncTracking(Entity entity) {
        sync(entity, PacketDistributor.TRACKING_ENTITY.with(() -> entity));
    }
    public static void sync(Entity entity, PacketDistributor.PacketTarget target)
    {
        getCapability(entity).ifPresent(c -> INSTANCE.send(target, new SyncEntityCapabilityDataPacket(entity.getId(), c.serializeNBT())));
    }
    public static LazyOptional<EntityDataCapability> getCapability(Entity entity) {
        return entity.getCapability(CAPABILITY);
    }
}
