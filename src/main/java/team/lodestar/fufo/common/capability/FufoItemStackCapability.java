package team.lodestar.fufo.common.capability;

import team.lodestar.fufo.FufoMod;
import team.lodestar.lodestone.systems.capability.LodestoneCapability;
import team.lodestar.lodestone.systems.capability.LodestoneCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class FufoItemStackCapability implements LodestoneCapability {

    public static Capability<FufoItemStackCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public FufoItemStackCapability() {
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(FufoItemStackCapability.class);
    }

    public static void attachItemCapability(AttachCapabilitiesEvent<ItemStack> event) {
        final FufoItemStackCapability capability = new FufoItemStackCapability();
        event.addCapability(FufoMod.fufoPath("item_data"), new LodestoneCapabilityProvider<>(FufoItemStackCapability.CAPABILITY, () -> capability));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
    }

    public static LazyOptional<FufoItemStackCapability> getCapabilityOptional(ItemStack stack) {
        return stack.getCapability(CAPABILITY);
    }

    public static FufoItemStackCapability getCapability(ItemStack stack) {
        return stack.getCapability(CAPABILITY).orElse(new FufoItemStackCapability());
    }
}