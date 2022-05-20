package com.sammy.fufo.client.renderers.entity.weave;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.fufo.common.entity.weave.AbstractWeaveEntity;
import com.sammy.fufo.core.systems.magic.weaving.Weave;
import com.sammy.fufo.core.systems.magic.weaving.recipe.IngredientBindable;
import com.sammy.fufo.core.systems.magic.weaving.recipe.ItemStackBindable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static com.sammy.fufo.FufoMod.fufoPath;

public class AbstractWeaveEntityRenderer extends EntityRenderer<AbstractWeaveEntity> {

    private final ItemRenderer itemRenderer;
    public AbstractWeaveEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    // TODO: make this deserialise the weave from the entity, render items at their respective positions
    public void render(AbstractWeaveEntity entity, float yaw, float partialTicks, PoseStack ps, MultiBufferSource buffer, int packedLight){
        super.render(entity, yaw, partialTicks, ps, buffer, packedLight);
        ps.pushPose();
        Weave<?> weave = entity.weave;
        weave.getBindables().forEach(bindable -> {
            if(bindable instanceof ItemStackBindable){
                Vec3i offset = bindable.getLocation();
                ps.translate(offset.getX(), offset.getY(), offset.getZ());
                if (!((ItemStackBindable) bindable).getItemStack().isEmpty()) {
                    this.itemRenderer.renderStatic(((ItemStackBindable) bindable).getItemStack(), ItemTransforms.TransformType.FIXED, packedLight, OverlayTexture.NO_OVERLAY, ps, buffer, entity.getId());
                }
                ps.translate(-offset.getX(), -offset.getY(), -offset.getZ());
            } else if (bindable instanceof IngredientBindable){
                Vec3i offset = bindable.getLocation();
                ps.translate(offset.getX(), offset.getY(), offset.getZ());
                ItemStack[] items = ((IngredientBindable) bindable).getIngredient().getItems();
                if(items.length != 0){
                    // do something based on tick time
                }
            }
        });
        ps.popPose();
    }

    /**
     * Returns the location of an entity's texture.
     *
     * @param pEntity
     */
    @Override
    public ResourceLocation getTextureLocation(AbstractWeaveEntity pEntity) {
        return null;
    }
}
