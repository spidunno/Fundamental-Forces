package team.lodestar.fufo.common.capability;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.world.registry.FluidPipeNetworkRegistry;
import team.lodestar.lodestone.systems.capability.LodestoneCapability;
import team.lodestar.lodestone.systems.capability.LodestoneCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class FufoWorldDataCapability implements LodestoneCapability {

	private final Level level;
    //shove all level data here, use WorldDataCapability.getCapability(level) to access data.
    //level refers to dimension, not world. Each dimension will have it's own capability instance.
    public static Capability<FufoWorldDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public FufoWorldDataCapability(Level level) {
    	this.level = level;
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(FufoWorldDataCapability.class);
    }

    public static void attachWorldCapability(AttachCapabilitiesEvent<Level> event) {
        final FufoWorldDataCapability capability = new FufoWorldDataCapability(event.getObject());
        event.addCapability(FufoMod.fufoPath("world_data"), new LodestoneCapabilityProvider<>(FufoWorldDataCapability.CAPABILITY, () -> capability));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("networks", FluidPipeNetworkRegistry.getRegistry(level).serialize());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
    	FluidPipeNetworkRegistry.load(level, nbt.getList("networks", Tag.TAG_COMPOUND));
    }

    public static LazyOptional<FufoWorldDataCapability> getCapabilityOptional(Level level) {
        return level.getCapability(CAPABILITY);
    }

    public static FufoWorldDataCapability getCapability(Level level) {
        return level.getCapability(CAPABILITY).orElse(new FufoWorldDataCapability(level));
    }
}