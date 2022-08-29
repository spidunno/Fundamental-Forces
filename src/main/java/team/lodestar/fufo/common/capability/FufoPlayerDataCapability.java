package team.lodestar.fufo.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.packets.FufoPlayerCapabilitySyncPacket;
import team.lodestar.fufo.common.fluid.PipeBuilderAssistant;
import team.lodestar.fufo.core.spell.hotbar.SpellHotbar;
import team.lodestar.fufo.registry.common.FufoPackets;
import team.lodestar.fufo.unsorted.handlers.PlayerSpellHotbarHandler;
import team.lodestar.fufo.unsorted.handlers.ProgressionHandler;
import team.lodestar.lodestone.helpers.NBTHelper;
import team.lodestar.lodestone.systems.capability.LodestoneCapability;
import team.lodestar.lodestone.systems.capability.LodestoneCapabilityProvider;

public class FufoPlayerDataCapability implements LodestoneCapability {

    //shove all player data here, use PlayerDataCapability.getCapability(player) to access data.
    //TODO implement system to store progression unlocks (including runes)

    public static Capability<FufoPlayerDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });


    public PlayerSpellHotbarHandler hotbarHandler = new PlayerSpellHotbarHandler(new SpellHotbar(9));
    public PipeBuilderAssistant pipeHandler = new PipeBuilderAssistant();
    public ProgressionHandler progressHandler = new ProgressionHandler();

    public FufoPlayerDataCapability() {
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(FufoPlayerDataCapability.class);
    }

    public static void attachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            final FufoPlayerDataCapability capability = new FufoPlayerDataCapability();
            event.addCapability(FufoMod.fufoPath("player_data"), new LodestoneCapabilityProvider<>(FufoPlayerDataCapability.CAPABILITY, () -> capability));
        }
    }

    public static void playerJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            syncSelf(serverPlayer);
        }
    }

    public static void syncPlayerCapability(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof Player player) {
            if (player.level instanceof ServerLevel) {
                syncTracking(player);
            }
        }
    }

    public static void playerClone(PlayerEvent.Clone event) {
        event.getOriginal().revive();
        FufoPlayerDataCapability.getCapabilityOptional(event.getOriginal()).ifPresent(o -> FufoPlayerDataCapability.getCapabilityOptional(event.getEntity()).ifPresent(c -> c.deserializeNBT(o.serializeNBT())));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        hotbarHandler.serializeNBT(tag);
        progressHandler.serializeNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        hotbarHandler.deserializeNBT(tag);
        progressHandler.deserializeNBT(tag);
    }

    public static void syncServer(Player player, NBTHelper.TagFilter filter) {
        sync(player, PacketDistributor.SERVER.noArg(), filter);
    }

    public static void syncSelf(ServerPlayer player, NBTHelper.TagFilter filter) {
        sync(player, PacketDistributor.PLAYER.with(() -> player), filter);
    }

    public static void syncTrackingAndSelf(Player player, NBTHelper.TagFilter filter) {
        sync(player, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), filter);
    }

    public static void syncTracking(Player player, NBTHelper.TagFilter filter) {
        sync(player, PacketDistributor.TRACKING_ENTITY.with(() -> player), filter);
    }

    public static void sync(Player player, PacketDistributor.PacketTarget target, NBTHelper.TagFilter filter) {
        getCapabilityOptional(player).ifPresent(c -> FufoPackets.INSTANCE.send(target, new FufoPlayerCapabilitySyncPacket(player.getUUID(), NBTHelper.filterTag(c.serializeNBT(), filter))));
    }

    public static void syncServer(Player player) {
        sync(player, PacketDistributor.SERVER.noArg());
    }

    public static void syncSelf(ServerPlayer player) {
        sync(player, PacketDistributor.PLAYER.with(() -> player));
    }

    public static void syncTrackingAndSelf(Player player) {
        sync(player, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player));
    }

    public static void syncTracking(Player player) {
        sync(player, PacketDistributor.TRACKING_ENTITY.with(() -> player));
    }

    public static void sync(Player player, PacketDistributor.PacketTarget target) {
        getCapabilityOptional(player).ifPresent(c -> FufoPackets.INSTANCE.send(target, new FufoPlayerCapabilitySyncPacket(player.getUUID(), c.serializeNBT())));
    }

    public static LazyOptional<FufoPlayerDataCapability> getCapabilityOptional(Player player) {
        return player.getCapability(CAPABILITY);
    }

    public static FufoPlayerDataCapability getCapability(Player player) {
        return player.getCapability(CAPABILITY).orElse(new FufoPlayerDataCapability());
    }
}