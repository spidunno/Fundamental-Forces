package team.lodestar.fufo.client.rendering.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.fluid.PipeNodeBlockEntity;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.awt.*;

import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;

public class AnchorRenderer<T extends PipeNodeBlockEntity> implements BlockEntityRenderer<T> {

    private static final ResourceLocation TEST_BEAM = FufoMod.fufoPath("textures/vfx/uv_test.png");
    private static final RenderType TEST_BEAM_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.apply(TEST_BEAM);

    public AnchorRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(T blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        VertexConsumer beamConsumer = DELAYED_RENDER.getBuffer(TEST_BEAM_TYPE);
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        blockEntityIn.nearbyAnchorPositions.forEach(anchor -> {
            VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().renderBeam(beamConsumer, poseStack, BlockHelper.fromBlockPos(blockEntityIn.getBlockPos()), BlockHelper.fromBlockPos(anchor), 0.1f);
        });
        Vec3 start = BlockHelper.fromBlockPos(blockEntityIn.getBlockPos());
        Vec3 end = start.add(0, blockEntityIn.getStoredFluid().getAmount()/50.0, 0);
        VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(blockEntityIn.isOpen() ? Color.red : Color.blue).renderBeam(beamConsumer, poseStack, start, end, 0.1f);
        poseStack.popPose();
    }
}
