package com.project_esoterica.esoterica.common.worldevents.starfall;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.common.packets.ScreenshakePacket;
import com.project_esoterica.esoterica.core.eventhandlers.NetworkManager;
import com.project_esoterica.esoterica.core.registry.worldevent.StarfallActors;
import com.project_esoterica.esoterica.core.systems.rendering.RenderManager;
import com.project_esoterica.esoterica.core.systems.rendering.RenderTypes;
import com.project_esoterica.esoterica.core.systems.rendering.RenderUtilities;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventInstance;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import static com.project_esoterica.esoterica.core.systems.rendering.RenderManager.DELAYED_RENDER;

public class StarfallEvent extends WorldEventInstance {

    public static final String ID = "starfall";

    public static WorldEventReader READER = new WorldEventReader() {
        @Override
        public WorldEventInstance createInstance(CompoundTag tag) {
            return fromNBT(tag);
        }
    };

    public StarfallActor actor;

    public BlockPos targetedPos = BlockPos.ZERO;
    public Vec3 position = Vec3.ZERO;
    public Vec3 motion = Vec3.ZERO;
    public float acceleration = 0.01f;

    private StarfallEvent() {
        super(ID);
    }

    public StarfallEvent(StarfallActor actor) {
        super(ID);
        this.actor = actor;
    }

    public static StarfallEvent fromNBT(CompoundTag tag) {
        StarfallEvent instance = new StarfallEvent();
        instance.deserializeNBT(tag);
        return instance;
    }

    public StarfallEvent startPosition(Vec3 position) {
        this.position = position;
        return this;
    }

    public StarfallEvent motion(Vec3 motion) {
        this.motion = motion;
        return this;
    }

    public StarfallEvent targetPosition(BlockPos targetedPos) {
        this.targetedPos = targetedPos;
        return this;
    }

    @Override
    public void start(ServerLevel level) {
        if (existsOnClient()) {
            addToClient();
        }
    }

    @Override
    public void tick(ServerLevel level) {
        move();
        if (position.y() <= targetedPos.getY()) {
            end(level);
        }
    }

    @Override
    public void end(ServerLevel level) {
        actor.act(level, targetedPos);
        super.end(level);
    }

    @Override
    public boolean existsOnClient() {
        return true;
    }

    @Override
    public void clientTick(ClientLevel level) {
        move();
        if (position.y() <= targetedPos.getY()) {
            clientEnd(level);
        }
    }

    @Override
    public void clientEnd(ClientLevel level) {
        triggerGlobalScreenshake(level);
        super.clientEnd(level);
    }

    private static final ResourceLocation STAR_LOCATION = new ResourceLocation(EsotericaMod.MOD_ID, "textures/star.png");
    public static final RenderType RENDER_TYPE = RenderTypes.createGlowingTextureRenderType(STAR_LOCATION);
    @Override
    public boolean canRender() {
        float renderSize = 25;
        return RenderManager.FRUSTUM.isVisible(new AABB(position.subtract(renderSize,renderSize,renderSize), position.add(renderSize,renderSize,renderSize)));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, float partialTicks) {
        poseStack.pushPose();
        LocalPlayer player = Minecraft.getInstance().player;
        float minScale = 9;
        float time = (player.tickCount + partialTicks) / 4f;
        float distanceMultiplier = Math.max(1, 20 - (float) Math.max(0, position.distanceTo(player.position()) / 20f));
        double flicker = (3 % Math.sin(time) - Math.cos(-time)) / 3f;
        float maxScale = (float) (Math.max(minScale, minScale + distanceMultiplier) + flicker * (distanceMultiplier / 2f));
        poseStack.translate(position.x - player.getX(), position.y - player.getY(), position.z - player.getZ()); // move to position
        poseStack.scale(maxScale, maxScale, maxScale);
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
        MultiBufferSource delayedBuffer = DELAYED_RENDER;
        VertexConsumer vertexConsumer = delayedBuffer.getBuffer(RENDER_TYPE);
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();

        RenderUtilities.vertex(vertexConsumer, matrix, -0.5f, -0.5f, 0, 255, 255, 255, 255, 0, 1, 15728880);
        RenderUtilities.vertex(vertexConsumer, matrix, 0.5f, -0.5f, 0, 255, 255, 255, 255, 1, 1, 15728880);
        RenderUtilities.vertex(vertexConsumer, matrix, 0.5f, 0.5f, 0, 255, 255, 255, 255, 1, 0, 15728880);
        RenderUtilities.vertex(vertexConsumer, matrix, -0.5f, 0.5f, 0, 255, 255, 255, 255, 0, 0, 15728880);

        poseStack.popPose();
    }
    private void move() {
        position = position.add(motion.multiply(acceleration, acceleration, acceleration));
        acceleration += 0.01f;
    }

    private static final float notifyRadius = 200f; // players within this radius receive screenshake upon impact
    private static final float screenshakeFactor = 0.9f;
    private static final float screenshakeFalloff = 0.85f;

    // TODO: make farther players experience less screenshake
    private void triggerGlobalScreenshake(ClientLevel level) {
        NetworkManager.INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(targetedPos.getX(), targetedPos.getY(), targetedPos.getZ(), notifyRadius, level.dimension())), new ScreenshakePacket(screenshakeFactor, screenshakeFalloff));
    }

    @Override
    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putString("resultId", actor.id);
        tag.putIntArray("targetedPos", new int[]{targetedPos.getX(), targetedPos.getY(), targetedPos.getZ()});
        tag.putDouble("posX", position.x());
        tag.putDouble("posY", position.y());
        tag.putDouble("posZ", position.z());
        tag.putDouble("motionX", motion.x());
        tag.putDouble("motionY", motion.y());
        tag.putDouble("motionZ", motion.z());
        tag.putFloat("acceleration", acceleration);
        return super.serializeNBT(tag);
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        actor = StarfallActors.STARFALL_RESULTS.get(tag.getString("resultId"));
        int[] positions = tag.getIntArray("targetedPos");
        targetedPos = new BlockPos(positions[0], positions[1], positions[2]);
        position = new Vec3(tag.getDouble("posX"), tag.getDouble("posY"), tag.getDouble("posZ"));
        motion = new Vec3(tag.getDouble("motionX"), tag.getDouble("motionY"), tag.getDouble("motionZ"));
        acceleration = tag.getFloat("acceleration");
        super.deserializeNBT(tag);
    }
}