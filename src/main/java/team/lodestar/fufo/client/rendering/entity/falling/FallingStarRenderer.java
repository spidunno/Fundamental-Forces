package team.lodestar.fufo.client.rendering.entity.falling;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import team.lodestar.fufo.common.entity.falling.FallingEntity;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;

import java.awt.*;

import static team.lodestar.fufo.FufoMod.fufoPath;
import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;

public class FallingStarRenderer extends EntityRenderer<FallingEntity> {

    private static final ResourceLocation COMA = fufoPath("textures/vfx/uv_test.png");
    private static final RenderType COMA_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.apply(COMA);

    private static final ResourceLocation LIGHT_TRAIL = fufoPath("textures/vfx/light_trail.png");
    private static final RenderType LIGHT_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.apply(LIGHT_TRAIL);

    private static final ResourceLocation FIRE_TRAIL = fufoPath("textures/vfx/fire_trail.png");
    private static final RenderType FIRE_TYPE = LodestoneRenderTypeRegistry.SCROLLING_TEXTURE.apply(FIRE_TRAIL);

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
//        if (event.phase.equals(TickEvent.Phase.START)) {
//            if (Minecraft.getInstance().level != null) {
//                TextureSurgeon textureSurgeon = new TextureSurgeon();
//                textureSurgeon
//                        .shouldDumpTextures()
//                        .setSourceTexture(fufoPath("textures/block/crude_array.png"), 32)
//                        .setSourceTexture(new DynamicTexture(fufoPath("textures/block/crude_array.png"), 32))
//                        .operateWithShaders(VFXBuilders.createScreen().setPosColorTexLightmapDefaultFormat(), LodestoneShaderRegistry.ADDITIVE_TEXTURE.instance, LodestoneShaderRegistry.TRIANGLE_TEXTURE.instance, LodestoneShaderRegistry.SCROLLING_TEXTURE.instance, LodestoneShaderRegistry.METALLIC_NOISE.instance);
//            }
//        }
    }
}