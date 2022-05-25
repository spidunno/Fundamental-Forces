package com.sammy.fufo.client.renderers.entity.weave;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sammy.fufo.common.entity.weave.PrimedBindableEntity;
import com.sammy.fufo.core.systems.magic.weaving.Bindable;
import com.sammy.fufo.core.systems.magic.weaving.recipe.EntityTypeBindable;
import com.sammy.fufo.core.systems.magic.weaving.recipe.ItemStackBindable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class PrimedBindableEntityRenderer extends EntityRenderer<PrimedBindableEntity> {
    private final ItemRenderer itemRenderer;

    public PrimedBindableEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(PrimedBindableEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        Minecraft mc = Minecraft.getInstance();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.pushPose();
        Bindable bindable = pEntity.getBindable();
        pMatrixStack.translate(0,0.3D, 0);
        float fac = pEntity.tickCount + pPartialTicks + 5;
        if (bindable instanceof ItemStackBindable itemStackBindable){
            if(!itemStackBindable.getItemStack().isEmpty()){
                pMatrixStack.pushPose();
                pMatrixStack.scale(0.9f, 0.9f, 0.9f);
                pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(fac * 3));
                this.itemRenderer.renderStatic(((ItemStackBindable) bindable).getItemStack(), ItemTransforms.TransformType.GROUND, pPackedLight, OverlayTexture.NO_OVERLAY, pMatrixStack, pBuffer, pEntity.getId());
                pMatrixStack.popPose();
            }
        } else if (bindable instanceof EntityTypeBindable){
            pMatrixStack.pushPose();
            EntityType<?> entityType = ((EntityTypeBindable) bindable).get();
            pMatrixStack.scale(0.3f, 0.3f, 0.3f);
            pMatrixStack.translate(0, -(entityType.getHeight() > 1 ? entityType.getHeight() / 3 : 0f), 0);
            pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(fac * 3));
            mc.getEntityRenderDispatcher().render(entityType.create(pEntity.level), 0, 0, 0, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
            pMatrixStack.popPose();
        }
        pMatrixStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(PrimedBindableEntity pEntity) {
        return null;
    }
}
