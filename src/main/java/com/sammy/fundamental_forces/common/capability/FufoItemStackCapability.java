package com.sammy.fundamental_forces.common.capability;

import com.sammy.fundamental_forces.FufoMod;
import com.sammy.ortus.systems.capability.OrtusCapability;
import com.sammy.ortus.systems.capability.OrtusCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class FufoItemStackCapability implements OrtusCapability {

    public static Capability<FufoItemStackCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public FufoItemStackCapability() {
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(FufoItemStackCapability.class);
    }

    public static void attachItemCapability(AttachCapabilitiesEvent<ItemStack> event) {
        final FufoItemStackCapability capability = new FufoItemStackCapability();
        event.addCapability(FufoMod.prefix("item_data"), new OrtusCapabilityProvider<>(FufoItemStackCapability.CAPABILITY, () -> capability));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
    }
    public static LazyOptional<FufoItemStackCapability> getCapability(ItemStack stack) {
        return stack.getCapability(CAPABILITY);
    }
}