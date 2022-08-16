package team.lodestar.fufo.client.rendering.block;

import com.mojang.blaze3d.vertex.PoseStack;
import team.lodestar.fufo.common.blockentity.CrudePrimerBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class CrudePrimerRenderer implements BlockEntityRenderer<CrudePrimerBlockEntity> {

    public CrudePrimerRenderer(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void render(CrudePrimerBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.popPose();
    }
}
