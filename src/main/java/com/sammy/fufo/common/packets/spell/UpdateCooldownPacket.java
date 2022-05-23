package com.sammy.fufo.common.packets.spell;

import com.sammy.fufo.common.capability.FufoPlayerDataCapability;
import com.sammy.fufo.core.systems.magic.spell.SpellCooldownData;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.UUID;
import java.util.function.Supplier;

public class UpdateCooldownPacket {
    private final UUID uuid;
    private final int slot;
    private final SpellCooldownData cooldownData;

    public UpdateCooldownPacket(UUID uuid, int slot, SpellCooldownData data) {
        this.uuid = uuid;
        this.slot = slot;
        this.cooldownData = data;
    }

    public static UpdateCooldownPacket decode(FriendlyByteBuf buf) {
        return new UpdateCooldownPacket(buf.readUUID(), buf.readInt(), SpellCooldownData.deserializeNBT(buf.readNbt()));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeInt(slot);
        buf.writeNbt(cooldownData.serializeNBT(new CompoundTag()));
    }

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientOnly.setCooldown(uuid, slot, cooldownData);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, UpdateCooldownPacket.class, UpdateCooldownPacket::encode, UpdateCooldownPacket::decode, UpdateCooldownPacket::execute);
    }

    public static class ClientOnly {
        public static void setCooldown(UUID uuid, int slot, SpellCooldownData cooldownData) {
            Player player = Minecraft.getInstance().player.level.getPlayerByUUID(uuid);
            FufoPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> {
                c.hotbarHandler.spellHotbar.spells.get(slot).cooldown = cooldownData;
            });
        }
    }
}