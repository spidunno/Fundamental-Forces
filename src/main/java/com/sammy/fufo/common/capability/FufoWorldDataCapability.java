package com.sammy.fufo.common.capability;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.core.systems.logistics.PipeBuilderAssistant;
import com.sammy.ortus.systems.capability.OrtusCapability;
import com.sammy.ortus.systems.capability.OrtusCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class FufoWorldDataCapability implements OrtusCapability {

    //shove all level data here, use WorldDataCapability.getCapability(level) to access data.
    //level refers to dimension, not world. Each dimension will have it's own capability instance.
    public static Capability<FufoWorldDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public FufoWorldDataCapability() {
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(FufoWorldDataCapability.class);
    }
PipeBuilderAssistant
    public static void attachWorldCapability(AttachCapabilitiesEvent<Level> event) {
        final FufoWorldDataCapability capability = new FufoWorldDataCapability();
        event.addCapability(FufoMod.fufoPath("world_data"), new OrtusCapabilityProvider<>(FufoWorldDataCapability.CAPABILITY, () -> capability));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
    }

    public static LazyOptional<FufoWorldDataCapability> getCapability(Level level) {
        return level.getCapability(CAPABILITY);
    }
}