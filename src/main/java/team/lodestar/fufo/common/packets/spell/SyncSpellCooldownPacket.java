package team.lodestar.fufo.common.packets.spell;

import team.lodestar.fufo.common.capability.FufoPlayerDataCapability;
import team.lodestar.fufo.core.spell.SpellCooldown;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.UUID;
import java.util.function.Supplier;

public class SyncSpellCooldownPacket extends LodestoneClientPacket {
    private final UUID uuid;
    private final int slot;
    private final SpellCooldown cooldownData;

    public SyncSpellCooldownPacket(UUID uuid, int slot, SpellCooldown data) {
        this.uuid = uuid;
        this.slot = slot;
        this.cooldownData = data;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeInt(slot);
        buf.writeNbt(cooldownData.serializeNBT());
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Player player = Minecraft.getInstance().player.level.getPlayerByUUID(uuid);
        FufoPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> {
            c.hotbarHandler.spellHotbar.spells.get(slot).cooldown = cooldownData;
        });
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SyncSpellCooldownPacket.class, SyncSpellCooldownPacket::encode, SyncSpellCooldownPacket::decode, SyncSpellCooldownPacket::execute);
    }

    public static SyncSpellCooldownPacket decode(FriendlyByteBuf buf) {
        return new SyncSpellCooldownPacket(buf.readUUID(), buf.readInt(), SpellCooldown.deserializeNBT(buf.readNbt()));
    }
}