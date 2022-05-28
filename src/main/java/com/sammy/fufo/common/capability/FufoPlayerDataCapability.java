package com.sammy.fufo.common.capability;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.packets.FufoPlayerCapabilitySyncPacket;
import com.sammy.fufo.core.handlers.PlayerSpellHotbarHandler;
import com.sammy.fufo.core.handlers.ProgressionHandler;
import com.sammy.fufo.core.setup.server.PacketRegistry;
import com.sammy.fufo.core.systems.logistics.PipeBuilderAssistant;
import com.sammy.fufo.core.systems.magic.spell.hotbar.SpellHotbar;
import com.sammy.ortus.helpers.NBTHelper;
import com.sammy.ortus.systems.capability.OrtusCapability;
import com.sammy.ortus.systems.capability.OrtusCapabilityProvider;
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
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;

public class FufoPlayerDataCapability implements OrtusCapability {

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
            event.addCapability(FufoMod.fufoPath("player_data"), new OrtusCapabilityProvider<>(FufoPlayerDataCapability.CAPABILITY, () -> capability));
        }
    }

    public static void playerJoin(EntityJoinWorldEvent event) {
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
        FufoPlayerDataCapability.getCapabilityOptional(event.getOriginal()).ifPresent(o -> FufoPlayerDataCapability.getCapabilityOptional(event.getPlayer()).ifPresent(c -> c.deserializeNBT(o.serializeNBT())));
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
        getCapabilityOptional(player).ifPresent(c -> PacketRegistry.INSTANCE.send(target, new FufoPlayerCapabilitySyncPacket(player.getUUID(), NBTHelper.filterTag(c.serializeNBT(), filter))));
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
        getCapabilityOptional(player).ifPresent(c -> PacketRegistry.INSTANCE.send(target, new FufoPlayerCapabilitySyncPacket(player.getUUID(), c.serializeNBT())));
    }

    public static LazyOptional<FufoPlayerDataCapability> getCapabilityOptional(Player player) {
        return player.getCapability(CAPABILITY);
    }

    public static FufoPlayerDataCapability getCapability(Player player) {
        return player.getCapability(CAPABILITY).orElse(new FufoPlayerDataCapability());
    }
}