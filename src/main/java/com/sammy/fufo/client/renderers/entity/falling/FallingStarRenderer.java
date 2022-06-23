package com.sammy.fufo.client.renderers.entity.falling;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.entity.falling.FallingEntity;
import com.sammy.ortus.OrtusLib;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import com.sammy.ortus.setup.OrtusShaderRegistry;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import com.sammy.ortus.systems.rendering.multipass.DynamicTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.event.TickEvent;
import org.antlr.v4.runtime.misc.Triple;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import static com.sammy.fufo.FufoMod.fufoPath;
import static com.sammy.ortus.handlers.RenderHandler.DELAYED_RENDER;

public class FallingStarRenderer extends EntityRenderer<FallingEntity> {

    private static final ResourceLocation COMA = fufoPath("textures/vfx/uv_test.png");
    private static final RenderType COMA_TYPE = OrtusRenderTypeRegistry.ADDITIVE_TEXTURE.apply(COMA);

    private static final ResourceLocation LIGHT_TRAIL = TextureSurgeon.DEFAULT_PATIENT_TEXTURE;
    private static final RenderType LIGHT_TYPE = OrtusRenderTypeRegistry.ADDITIVE_TEXTURE.apply(LIGHT_TRAIL);

    private static final ResourceLocation FIRE_TRAIL = fufoPath("textures/vfx/fire_trail.png");
    private static final RenderType FIRE_TYPE = OrtusRenderTypeRegistry.SCROLLING_TEXTURE.apply(FIRE_TRAIL);

//    public static final OBJModel METEOR_TEARDROP = OBJLoader.INSTANCE.loadModel(new OBJModel.ModelSettings(fufoPath("models/obj/meteor.obj"), false, false, true, true, null));
//    public static final SimpleRenderable RENDER = METEOR_TEARDROP.bakeRenderable(StandaloneModelConfiguration.INSTANCE);

    public FallingStarRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    //renders the coma obj model
    //        poseStack.scale(beamWidth + 1, beamWidth + 1, beamWidth + 1);
    //        RENDER.render(poseStack, DELAYED_RENDER, (r) -> COMA_TYPE, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, partialTicks, MultipartTransforms.EMPTY);
    @Override
    public void render(FallingEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        Player player = Minecraft.getInstance().player;
        Color[] colors = new Color[]{
                Color.RED, Color.ORANGE, Color.RED, Color.YELLOW, Color.WHITE, Color.WHITE
        };

//        if (entity.level.getGameTime() % 10L == 0) {
//            TextureSurgeon.enableTextureDump();
//
//            TextureSurgeon.operate(new DynamicTexture(fufoPath("textures/block/crude_array.png"), 256), VFXBuilders.createScreen().setPosTexDefaultFormat().setColor(Color.green), GameRenderer.getPositionTexShader());
//            test = false;
//        }
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
//        ArrayList<Vec3> positions =  DataHelper.rotatingRadialOffsets(Vec3.ZERO, 4,80, 0,1);
//        for (int i = 0; i < colors.length; i++) {
//            int finalI = i;
//            VertexConsumer fire = DELAYED_RENDER.getBuffer(queueUniformChanges(copy(i, FIRE_TYPE),
//                    (instance -> instance.safeGetUniform("Speed").set(100f + 300f * finalI))));
//
//            float index = colors.length - i;
//            float size = index * 2 + (float) Math.exp(i * 0.15f);
//            float width = size * 0.75f;
//            float alpha = 0.1f + 0.15f * (float) Math.exp(i * 0.5f);
//            Color color = colors[i];
//            builder.setColor(color)
//                    .setAlpha(alpha * 0.75f)
//                    .renderTrail(DELAYED_RENDER.getBuffer(LIGHT_TYPE), poseStack, positions.stream().map(p -> new Vector4f((float)p.x, (float)p.y, (float)p.z, 1)).collect(Collectors.toList()), f-> f*width)
//                    .setAlpha(alpha)
//                    .setUV(0, 0, 1, positions.size()/4f)
//                    .renderTrail(fire, poseStack, positions.stream().map(p -> new Vector4f((float)p.x, (float)p.y, (float)p.z, 1)).collect(Collectors.toList()), f-> f*width);
//        }
//        for (Vec3 position : positions) {
        RenderSystem.enableBlend();
        builder
                .renderQuad(DELAYED_RENDER.getBuffer(LIGHT_TYPE), poseStack, 2);
//        }
//
//          for (int i = 0; i < colors.length; i++) {
//            int finalI = i;
//            VertexConsumer fire = DELAYED_RENDER.getBuffer(queueUniformChanges(copy(i, FIRE_TYPE),
//                    (instance -> instance.safeGetUniform("Speed").set(100f + 300f * finalI))));

//            float index = colors.length-i;
//            float size = index*2+(float)Math.exp(i*0.15f);
//            float width = size * 0.75f;
//            float length = size * 1.5f;
//            float alpha = 0.1f + 0.15f * (float) Math.exp(i * 0.5f);
//            Color color = colors[i];
//            builder.setColor(color)
//                    .setAlpha(alpha)
//                    .renderBeam(fire, poseStack, entity.position(), entity.position().add(0, length, 0), width)
//                    .setAlpha(alpha*0.75f)
//                    .renderBeam(DELAYED_RENDER.getBuffer(LIGHT_TYPE), poseStack, entity.position(), entity.position().add(0, length, 0), width*0.7f);
//        }

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(FallingEntity p_114482_) {
        return LIGHT_TRAIL;
    }

    public static void renderTick(TickEvent.RenderTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.START)) {
            if (Minecraft.getInstance().level != null) {
                TextureSurgeon textureSurgeon = new TextureSurgeon();
                textureSurgeon
                        .shouldDumpTextures()
                        .setSourceTexture(fufoPath("textures/block/crude_array.png"), 32)
                        .operateWithShaders(VFXBuilders.createScreen().setPosColorTexLightmapDefaultFormat(), OrtusShaderRegistry.ADDITIVE_TEXTURE.instance, OrtusShaderRegistry.TRIANGLE_TEXTURE.instance, OrtusShaderRegistry.SCROLLING_TEXTURE.instance, OrtusShaderRegistry.METALLIC_NOISE.instance);
            }
        }
    }

    public static class TextureSurgeon { //TODO: move this over to ortusLib
        public static final ConcurrentHashMap<Triple<ResourceLocation, Integer, Integer>, DynamicTexture> DRAW_TO_TEXTURES = new ConcurrentHashMap<>();
        public static final ConcurrentHashMap<Triple<ResourceLocation, Integer, Integer>, DynamicTexture> SOURCE_TEXTURES = new ConcurrentHashMap<>();
        public static ResourceLocation DEFAULT_PATIENT_TEXTURE = FufoMod.fufoPath("textures/vfx/patient_texture.png");

        boolean shouldDumpTextures;
        ResourceLocation drawToLocation = DEFAULT_PATIENT_TEXTURE;
        DynamicTexture drawToTexture;
        DynamicTexture sourceTexture;
        Matrix4f oldProjection;

        public TextureSurgeon shouldDumpTextures() {
            this.shouldDumpTextures = true;
            return this;
        }

        public TextureSurgeon setSourceTexture(ResourceLocation sourceTexture, int size) {
            return this.setSourceTexture(sourceTexture, size, size);
        }
        public TextureSurgeon setSourceTexture(ResourceLocation sourceTexture, int width, int height) {
            return setSourceTexture(SOURCE_TEXTURES.computeIfAbsent(new Triple<>(sourceTexture, width, height), p -> new DynamicTexture(sourceTexture, width, height)));
        }
        public TextureSurgeon setSourceTexture(DynamicTexture texture) {
            this.sourceTexture = texture;
            return this;
        }

        public TextureSurgeon updateRenderTarget(boolean clearBuffer) {
            return updateRenderTarget(drawToLocation, sourceTexture, clearBuffer);
        }

        public TextureSurgeon updateRenderTarget(ResourceLocation drawToLocation, DynamicTexture sourceTexture, boolean clearBuffer) {
            drawToTexture = DRAW_TO_TEXTURES.computeIfAbsent(new Triple<>(drawToLocation, sourceTexture.getWidth(), sourceTexture.getHeight()), p -> new DynamicTexture(drawToLocation, p.b, p.c));
            RenderTarget frameBuffer = drawToTexture.getFrameBuffer();
            if (clearBuffer) {
                frameBuffer.clear(Minecraft.ON_OSX);
            }
            frameBuffer.bindWrite(true);
            return this;
        }

        public TextureSurgeon begin(boolean clearBuffer) {
            updateRenderTarget(clearBuffer);

            oldProjection = RenderSystem.getProjectionMatrix();
            Matrix4f matrix4f = Matrix4f.orthographic(0.0F, 16, 0, 16, 1000.0F, ForgeHooksClient.getGuiFarPlane());
            RenderSystem.setProjectionMatrix(matrix4f);

            PoseStack posestack = RenderSystem.getModelViewStack();
            posestack.pushPose();
            posestack.setIdentity();
            posestack.translate(0.0D, 0.0D, 1000F - ForgeHooksClient.getGuiFarPlane());
            return this;
        }

        public TextureSurgeon operateWithShaders(VFXBuilders.ScreenVFXBuilder builder, ShaderInstance... shaders) {
            PoseStack posestack = RenderSystem.getModelViewStack();
            builder.setPositionWithWidth(0, 0, 16, 16);
            RenderSystem.enableBlend();
            return operate(() -> {
                for (int i = 0; i < shaders.length; i++) {
                    begin(i == 0);
                    ShaderInstance shader = shaders[i];
                    builder.setShaderTexture(sourceTexture.getTextureLocation()).setShader(shader).draw(posestack);
                    end(i == shaders.length - 1);
                    if (i == 0) {
                        dumpTexture();
                        setSourceTexture(drawToTexture);
                    }
                }
            });
        }

        public TextureSurgeon operate(Runnable drawCall) {
            drawCall.run();
            return this;
        }

        public TextureSurgeon end(boolean clearBuffer) {
            Minecraft mc = Minecraft.getInstance();
            PoseStack posestack = RenderSystem.getModelViewStack();
            posestack.popPose();
            if (clearBuffer) {
                RenderSystem.applyModelViewMatrix();

                RenderSystem.setProjectionMatrix(oldProjection);
                RenderSystem.clear(256, Minecraft.ON_OSX);

                if (shouldDumpTextures) {
                    dumpTexture();
                }
                mc.getMainRenderTarget().bindWrite(true);
            }
            return this;
        }

        protected void dumpTexture() {
            try {
                Path outputFolder = Paths.get("texture_dump");
                outputFolder = Files.createDirectories(outputFolder);
                drawToTexture.saveTextureToFile(outputFolder);
            } catch (IOException e) {
                OrtusLib.LOGGER.error("Failed to dump texture maps with error.", e);
            }
        }
    }
}