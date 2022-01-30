package com.project_esoterica.esoterica.common.capability;

import com.project_esoterica.esoterica.common.packets.SyncPlayerCapabilityDataPacket;
import com.project_esoterica.esoterica.core.helper.DataHelper;
import com.project_esoterica.esoterica.core.systems.capability.SimpleCapability;
import com.project_esoterica.esoterica.core.systems.capability.SimpleCapabilityProvider;
import com.project_esoterica.esoterica.core.systems.magic.spell.hotbar.PlayerSpellHotbar;
import com.project_esoterica.esoterica.core.systems.magic.spell.hotbar.SpellHotbarHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.network.PacketDistributor;

import static com.project_esoterica.esoterica.core.setup.PacketRegistry.INSTANCE;

public class PlayerDataCapability implements SimpleCapability {

    //shove all player data here, use PlayerDataCapability.getCapability(player) to access data.

    public static Capability<PlayerDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public boolean firstTimeJoin;
    public SpellHotbarHandler hotbarHandler = new SpellHotbarHandler(new PlayerSpellHotbar(9));

    public PlayerDataCapability() {
    }

    public static void attachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(DataHelper.prefix("player_data"), new SimpleCapabilityProvider<>(PlayerDataCapability.CAPABILITY, PlayerDataCapability::new));
        }
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("firstTimeJoin", firstTimeJoin);
        hotbarHandler.serializeNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        firstTimeJoin = tag.getBoolean("firstTimeJoin");
        hotbarHandler.deserializeNBT(tag);
    }

    public static void sync(Player player) {
        getCapability(player).ifPresent(c -> INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new SyncPlayerCapabilityDataPacket(c.serializeNBT())));
    }

    public static LazyOptional<PlayerDataCapability> getCapability(Player player) {
        return player.getCapability(CAPABILITY);
    }
}