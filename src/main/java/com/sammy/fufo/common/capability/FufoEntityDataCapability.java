package com.sammy.fufo.common.capability;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.packets.FufoEntityCapabilitySyncPacket;
import com.sammy.ortus.systems.capability.OrtusCapability;
import com.sammy.ortus.systems.capability.OrtusCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;

import static com.sammy.fufo.core.setup.server.PacketRegistry.INSTANCE;

public class FufoEntityDataCapability implements OrtusCapability {

    public static Capability<FufoEntityDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });


    public FufoEntityDataCapability() {

    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(FufoEntityDataCapability.class);
    }

    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        final FufoEntityDataCapability capability = new FufoEntityDataCapability();
        event.addCapability(FufoMod.fufoPath("entity_data"), new OrtusCapabilityProvider<>(FufoEntityDataCapability.CAPABILITY, () -> capability));
    }

    public static void syncEntityCapability(PlayerEvent.StartTracking event) {
        if (event.getEntity().level instanceof ServerLevel) {
            FufoEntityDataCapability.syncTracking(event.getEntityLiving());
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
    }

    public static void syncTrackingAndSelf(Entity entity) {
        sync(entity, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity));
    }

    public static void syncTracking(Entity entity) {
        sync(entity, PacketDistributor.TRACKING_ENTITY.with(() -> entity));
    }

    public static void sync(Entity entity, PacketDistributor.PacketTarget target) {
        getCapability(entity).ifPresent(c -> INSTANCE.send(target, new FufoEntityCapabilitySyncPacket(entity.getId(), c.serializeNBT())));
    }

    public static LazyOptional<FufoEntityDataCapability> getCapability(Entity entity) {
        return entity.getCapability(CAPABILITY);
    }
}