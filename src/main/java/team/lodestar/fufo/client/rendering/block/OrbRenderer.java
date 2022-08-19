package team.lodestar.fufo.client.rendering.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import team.lodestar.fufo.common.blockentity.OrbBlockEntity;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.fufo.FufoMod;

import java.awt.*;

import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;


public class OrbRenderer<T extends OrbBlockEntity> implements BlockEntityRenderer<T> {
    public OrbRenderer(BlockEntityRendererProvider.Context context) {
    }

//    private static final VFXBuilders.WorldVFXBuilder BUILDER = VFXBuilders.createWorld()();
//    private static final ResourceLocation ORB_NOISE = prefix("textures/vfx/noise/orb_noise.png");
//    public static final RenderType ORB_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialNoiseQuadRenderType(ORB_NOISE), () -> {
//        ShaderInstance instance = ShaderRegistry.radialNoise.getInstance().get();
//        instance.safeGetUniform("Speed").set(2500f);
//        instance.safeGetUniform("Intensity").set(35f);
//        instance.safeGetUniform("Falloff").set(2.5f);
//    });
//    private static final ResourceLocation SECONDARY_ORB_NOISE = prefix("textures/vfx/noise/orb_noise_secondary.png");
//    public static final RenderType SECONDARY_ORB_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialNoiseQuadRenderType(SECONDARY_ORB_NOISE), () -> {
//
//        ShaderInstance instance = ShaderRegistry.radialNoise.getInstance().get();
//        instance.safeGetUniform("Speed").set(-1500f);
//        instance.safeGetUniform("Intensity").set(45f);
//        instance.safeGetUniform("Falloff").set(2.5f);
//    });
//    private static final ResourceLocation TRINARY_ORB_NOISE = prefix("textures/vfx/noise/orb_noise_trinary.png");
//    public static final RenderType TRINARY_ORB_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialNoiseQuadRenderType(TRINARY_ORB_NOISE), () -> {
//
//        ShaderInstance instance = ShaderRegistry.radialNoise.getInstance().get();
//        instance.safeGetUniform("Speed").set(2000f);
//        instance.safeGetUniform("Intensity").set(35f);
//        instance.safeGetUniform("Falloff").set(2.5f);
//    });

    @Override
    public void render(T blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        renderOrb(poseStack, Color.YELLOW, Color.CYAN, Color.YELLOW);
        poseStack.popPose();
    }

    private static final ResourceLocation TEST = FufoMod.fufoPath("textures/vfx/uv_test.png");
    public static final RenderType TEST_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.apply(TEST);

    public static void renderOrb(PoseStack poseStack, Color primaryColor, Color secondaryColor, Color trinaryColor) {
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
        VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setAlpha(0.5f).renderQuad(DELAYED_RENDER.getBuffer(TEST_TYPE), poseStack, 1);
    }
}