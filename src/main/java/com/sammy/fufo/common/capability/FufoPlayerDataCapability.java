package com.sammy.fufo.common.capability;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.packets.SyncFufoPlayerCapabilityDataPacket;
import com.sammy.fufo.common.packets.SyncPlayerCapabilityDataServerPacket;
import com.sammy.fufo.core.handlers.PlayerSpellHotbarHandler;
import com.sammy.fufo.core.systems.magic.spell.hotbar.SpellHotbar;
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

import static com.sammy.fufo.core.setup.server.PacketRegistry.INSTANCE;

public class FufoPlayerDataCapability implements OrtusCapability {

    //shove all player data here, use PlayerDataCapability.getCapability(player) to access data.

    public static Capability<FufoPlayerDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public boolean rightClickHeld;

    public PlayerSpellHotbarHandler hotbarHandler = new PlayerSpellHotbarHandler(new SpellHotbar(9));

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
        FufoPlayerDataCapability.getCapability(event.getOriginal()).ifPresent(o -> FufoPlayerDataCapability.getCapability(event.getPlayer()).ifPresent(c -> {
            c.deserializeNBT(o.serializeNBT());
        }));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        hotbarHandler.serializeNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        hotbarHandler.deserializeNBT(tag);
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
        getCapability(player).ifPresent(c -> INSTANCE.send(target, new SyncFufoPlayerCapabilityDataPacket(player.getUUID(), c.serializeNBT())));
    }

    public static void syncServer(Player player) {
        getCapability(player).ifPresent(c -> INSTANCE.send(PacketDistributor.SERVER.noArg(), new SyncPlayerCapabilityDataServerPacket(player.getUUID(), c.serializeNBT())));
    }

    public static LazyOptional<FufoPlayerDataCapability> getCapability(Player player) {
        return player.getCapability(CAPABILITY);
    }
}