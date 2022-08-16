package team.lodestar.fufo.client.rendering.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.blockentity.UITestBlockEntity;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;

public class UIRenderer implements BlockEntityRenderer<UITestBlockEntity> {

    private static final ResourceLocation TEST_BEAM = FufoMod.fufoPath("textures/vfx/uv_test.png");
    private static final RenderType TEST_BEAM_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.apply(TEST_BEAM);
    public UIRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(UITestBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0,1,0);
        VertexConsumer consumer = DELAYED_RENDER.getBuffer(TEST_BEAM_TYPE);
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(pBlockEntity.getLevel().getGameTime() * 10));
        VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setOffset(0.5f, 1.5f, 0.5f).renderSphere(consumer, pPoseStack, 1, 6, 6);
        pPoseStack.popPose();
    }
}
