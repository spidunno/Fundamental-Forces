package com.sammy.fundamental_forces.common.capability;

import com.sammy.fundamental_forces.common.packets.SyncPlayerCapabilityDataPacket;
import com.sammy.fundamental_forces.common.packets.SyncPlayerCapabilityDataServerPacket;
import com.sammy.fundamental_forces.common.packets.interaction.UpdateLeftClickPacket;
import com.sammy.fundamental_forces.common.packets.interaction.UpdateRightClickPacket;
import com.sammy.fundamental_forces.core.handlers.PlayerSpellHotbarHandler;
import com.sammy.fundamental_forces.core.helper.DataHelper;
import com.sammy.fundamental_forces.core.systems.capability.SimpleCapability;
import com.sammy.fundamental_forces.core.systems.capability.SimpleCapabilityProvider;
import com.sammy.fundamental_forces.core.systems.magic.spell.hotbar.SpellHotbar;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;

import static com.sammy.fundamental_forces.core.setup.server.PacketRegistry.INSTANCE;

public class ItemStackCapability implements SimpleCapability {

    public static Capability<ItemStackCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public ItemStackCapability() {
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ItemStackCapability.class);
    }

    public static void attachItemCapability(AttachCapabilitiesEvent<ItemStack> event) {
        final ItemStackCapability capability = new ItemStackCapability();
        event.addCapability(DataHelper.prefix("item_data"), new SimpleCapabilityProvider<>(ItemStackCapability.CAPABILITY, () -> capability));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
    }
    public static LazyOptional<ItemStackCapability> getCapability(ItemStack stack) {
        return stack.getCapability(CAPABILITY);
    }
}