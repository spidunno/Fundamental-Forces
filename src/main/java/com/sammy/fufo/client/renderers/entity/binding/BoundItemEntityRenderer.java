package com.sammy.fufo.client.renderers.entity.binding;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sammy.fufo.common.entity.binding.BoundItemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import static com.sammy.fufo.FufoMod.fufoPath;

public class BoundItemEntityRenderer extends EntityRenderer<BoundItemEntity> {

    private static final ResourceLocation BOUND_ITEM_TEXTURE = fufoPath("textures/particle/square.png");
    private final ItemRenderer itemRenderer;
    public BoundItemEntityRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        this.itemRenderer = p_174008_.getItemRenderer();
    }

    public void render(BoundItemEntity entity, float yaw, float partialTicks, PoseStack ps, MultiBufferSource buffer, int packedLight){
        super.render(entity, yaw, partialTicks, ps, buffer, packedLight);
        ps.pushPose();
        Direction direction = entity.getDirection();
        Vec3 vec3 = this.getRenderOffset(entity, partialTicks);
        ps.translate(-vec3.x(), -vec3.y(), -vec3.z());
        double d0 = 0.46875D;
        ps.translate((double)direction.getStepX() * d0, (double)direction.getStepY() * d0, (double)direction.getStepZ() * d0);
        ps.mulPose(Vector3f.XP.rotationDegrees(entity.getXRot()));
        ps.mulPose(Vector3f.YP.rotationDegrees(180.0F - entity.getYRot()));
        ItemStack stack = entity.getItem();
        if (!stack.isEmpty()){
            ps.scale(0.5F,0.5F,0.5F);
            this.itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, packedLight, OverlayTexture.NO_OVERLAY, ps, buffer, entity.getId());
        }
        ps.popPose();
    }

    /**
     * Returns the location of an entity's texture.
     *
     * @param pEntity
     */
    @Override
    public ResourceLocation getTextureLocation(BoundItemEntity pEntity) {
        return BOUND_ITEM_TEXTURE;
    }
}
