package com.sammy.fufo.client.renderers.entity.falling;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.entity.falling.FallingEntity;
import com.sammy.ortus.OrtusLib;
import com.sammy.ortus.helpers.util.Pair;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import com.sammy.ortus.setup.OrtusShaderRegistry;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import com.sammy.ortus.systems.rendering.multipass.DynamicTexture;
import com.sammy.ortus.systems.textureloader.OrtusTextureLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.StandaloneModelConfiguration;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.client.model.renderable.SimpleRenderable;
import net.minecraftforge.event.TickEvent;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static com.sammy.fufo.FufoMod.fufoPath;
import static com.sammy.ortus.handlers.RenderHandler.DELAYED_RENDER;

public class FallingStarRenderer extends EntityRenderer<FallingEntity> {

    private static final ResourceLocation COMA = fufoPath("textures/vfx/uv_test.png");
    private static final RenderType COMA_TYPE = OrtusRenderTypeRegistry.ADDITIVE_TEXTURE.apply(COMA);

    private static final ResourceLocation LIGHT_TRAIL = TextureSurgeon.PATIENT_TEXTURE;
    private static final RenderType LIGHT_TYPE = OrtusRenderTypeRegistry.ADDITIVE_TEXTURE.apply(LIGHT_TRAIL);

    private static final ResourceLocation FIRE_TRAIL = fufoPath("textures/vfx/fire_trail.png");
    private static final RenderType FIRE_TYPE = OrtusRenderTypeRegistry.SCROLLING_TEXTURE.apply(FIRE_TRAIL);

    public static final OBJModel METEOR_TEARDROP = OBJLoader.INSTANCE.loadModel(new OBJModel.ModelSettings(fufoPath("models/obj/meteor.obj"), false, false, true, true, null));
    public static final SimpleRenderable RENDER = METEOR_TEARDROP.bakeRenderable(StandaloneModelConfiguration.INSTANCE);

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


    public static class TextureSurgeon {
        private static boolean dumpTextures = false;

        public static ResourceLocation PATIENT_TEXTURE = FufoMod.fufoPath("textures/vfx/patient_texture.png");

        public static boolean operated = false;
        public static void renderTick(TickEvent.RenderTickEvent event) {
            if (event.phase.equals(TickEvent.Phase.START)) {
                if (Minecraft.getInstance().level != null) {
                    if (Minecraft.getInstance().level.getGameTime() % 200L == 0) {
                        TextureSurgeon.enableTextureDump();
                        TextureSurgeon.operate(new DynamicTexture(fufoPath("textures/block/crude_array.png"), 512), VFXBuilders.createScreen().setPosColorTexLightmapDefaultFormat(), OrtusShaderRegistry.TRIANGLE_TEXTURE.instance, OrtusShaderRegistry.METALLIC_NOISE.instance);
                        operated = true;

                    }
                }
            }
        }
        public static final HashMap<Pair<Integer,Integer>, DynamicTexture> TEXTURES = new HashMap<>();
        public static void operate(DynamicTexture tex, VFXBuilders.ScreenVFXBuilder builder, ShaderInstance... shaders) {
            Minecraft mc = Minecraft.getInstance();

            DynamicTexture drawTo = TEXTURES.computeIfAbsent(Pair.of(tex.getWidth(), tex.getHeight()), p -> new DynamicTexture(PATIENT_TEXTURE, p.getFirst(), p.getSecond()));
            RenderTarget frameBuffer = drawTo.getFrameBuffer();
            frameBuffer.clear(Minecraft.ON_OSX);
            frameBuffer.bindWrite(true);

            int size = 16;
            Matrix4f oldProjection = RenderSystem.getProjectionMatrix();
            Matrix4f matrix4f = Matrix4f.orthographic(0.0F,
                    size, 0, size, 1000.0F, ForgeHooksClient.getGuiFarPlane());
            RenderSystem.setProjectionMatrix(matrix4f);

            PoseStack posestack = RenderSystem.getModelViewStack();
            posestack.pushPose();
            posestack.setIdentity();
            posestack.translate(0.0D, 0.0D, 1000F - ForgeHooksClient.getGuiFarPlane());
            for (ShaderInstance shader : shaders) {
                builder.setShaderTexture(new ResourceLocation("textures/gui/container/villager2.png")).setShader(shader).setPositionWithWidth(0, 0, size, size).draw(posestack);
            }
            posestack.popPose();
            RenderSystem.applyModelViewMatrix();

            RenderSystem.setProjectionMatrix(oldProjection);
            RenderSystem.clear(256, Minecraft.ON_OSX);

            drawTo.download();
            OrtusTextureLoader.applyGrayscale(drawTo.getPixels());
            drawTo.upload();
            if (dumpTextures) {
                try {
                    Path outputFolder = Paths.get("texture_dump");
                    outputFolder = Files.createDirectories(outputFolder);
                    drawTo.saveTextureToFile(outputFolder);
                } catch (IOException e) {
                    OrtusLib.LOGGER.error("Failed to dump texture maps with error.", e);
                }
            }
            mc.getMainRenderTarget().bindWrite(true);

//            PoseStack posestack = RenderSystem.getModelViewStack();
//            Minecraft mc = Minecraft.getInstance();
//            DynamicTexture drawTo = new DynamicTexture(PATIENT_TEXTURE, tex.getWidth(), tex.getHeight());
//            RenderTarget frameBuffer = drawTo.getFrameBuffer();
//
//            posestack.pushPose();
//            posestack.setIdentity();
//            posestack.translate(0.0D, 0.0D, 1000F - ForgeHooksClient.getGuiFarPlane());
//            frameBuffer.clear(Minecraft.ON_OSX);
//            frameBuffer.bindWrite(true);
//
//            RenderSystem.applyModelViewMatrix();
//            Lighting.setupFor3DItems();
//
//            int size = 16;
////            builder.setUVWithWidth(0, 0, 1, 1);
////            for (ShaderInstance shader : shaders) {
//                RenderSystem.setShaderTexture(0,
//                        new ResourceLocation("textures/gui/container/villager2.png")
//                );
//                Gui.blit(posestack,0,0,1000,0,0,
//                        256,256,16,16);
////                builder.setShaderTexture(tex.getTextureLocation()).setShader(shader).setPositionWithWidth(0, 0, size, size).draw(posestack);
////            }
//            posestack.popPose();
//
//            drawTo.download();
//            OrtusTextureLoader.applyGrayscale(drawTo.getPixels());
//            drawTo.upload();
//
//            if (dumpTextures) {
//                try {
//                    Path outputFolder = Paths.get("texture_dump");
//                    outputFolder = Files.createDirectories(outputFolder);
//                    drawTo.saveTextureToFile(outputFolder);
//                } catch (IOException e) {
//                    OrtusLib.LOGGER.error("Failed to dump texture maps with error.", e);
//                }
//            }
//
//            mc.getMainRenderTarget().bindWrite(true);
        }

        public static void enableTextureDump() {
            dumpTextures = true;
        }

        public static void disableTextureDump() {
            dumpTextures = false;
        }
    }
}