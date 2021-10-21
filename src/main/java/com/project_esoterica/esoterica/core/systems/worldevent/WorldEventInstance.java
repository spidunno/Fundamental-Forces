package com.project_esoterica.esoterica.core.systems.worldevent;

import com.mojang.blaze3d.vertex.PoseStack;
import com.project_esoterica.esoterica.common.packets.AddWorldEventToClientPacket;
import com.project_esoterica.esoterica.common.packets.ScreenshakePacket;
import com.project_esoterica.esoterica.core.eventhandlers.NetworkManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.UUID;

public abstract class WorldEventInstance {
    public UUID uuid;
    public String type;
    public boolean invalidated;

    public WorldEventInstance(String type) {
        this.uuid = UUID.randomUUID();
        this.type = type;
    }

    public void start(ServerLevel level) {

    }

    public void tick(ServerLevel level) {

    }

    public void end(ServerLevel level) {
        invalidated = true;
    }

    public boolean existsOnClient()
    {
        return false;
    }
    public void addToClient() {
        NetworkManager.INSTANCE.send(PacketDistributor.ALL.noArg(), new AddWorldEventToClientPacket(type, true, serializeNBT(new CompoundTag())));
    }

    public void addToClient(ServerPlayer player) {
        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(()->player), new AddWorldEventToClientPacket(type, false, serializeNBT(new CompoundTag())));
    }

    public void clientStart(ClientLevel level) {

    }

    public void clientTick(ClientLevel level) {

    }

    public void clientEnd(ClientLevel level) {
        invalidated = true;
    }

    public boolean canRender() {
        return false;
    }
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, float partialTicks) {
    }

    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putUUID("uuid", uuid);
        tag.putString("type", type);
        tag.putBoolean("invalidated", invalidated);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        uuid = tag.getUUID("uuid");
        type = tag.getString("type");
        invalidated = tag.getBoolean("invalidated");
    }
}