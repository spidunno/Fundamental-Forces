package com.sammy.fufo.client.renderers.entity.weave;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.entity.weave.AbstractWeaveEntity;
import com.sammy.fufo.core.systems.magic.weaving.Weave;
import com.sammy.fufo.core.systems.magic.weaving.recipe.EntityTypeBindable;
import com.sammy.fufo.core.systems.magic.weaving.recipe.IngredientBindable;
import com.sammy.fufo.core.systems.magic.weaving.recipe.ItemStackBindable;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public class AbstractWeaveEntityRenderer extends EntityRenderer<AbstractWeaveEntity> {

    private static final ResourceLocation TEST_BEAM = FufoMod.fufoPath("textures/vfx/light_trail.png");
    private static final RenderType TEST_BEAM_TYPE = OrtusRenderTypeRegistry.ADDITIVE_TEXTURE.apply(TEST_BEAM);
    private final ItemRenderer itemRenderer;

    public AbstractWeaveEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    // TODO: make this deserialise the weave from the entity, render items at their respective positions
    public void render(AbstractWeaveEntity entity, float yaw, float partialTicks, PoseStack ps, MultiBufferSource buffer, int packedLight) {
        Minecraft mc = Minecraft.getInstance();
        super.render(entity, yaw, partialTicks, ps, buffer, packedLight);
        ps.pushPose();
        Weave<?> weave = entity.weave;
        ps.translate(0, 0.3D, 0);
        weave.getBindables().forEach(b -> {
            Vec3i offset = b.getLocation();
            ps.translate(offset.getX(), offset.getY(), offset.getZ());
            if (b instanceof ItemStackBindable itemStackBindable) {
                if (!itemStackBindable.getItemStack().isEmpty()) {
                    ps.pushPose();
                    ps.scale(0.9f, 0.9f, 0.9f);
                    ps.mulPose(Vector3f.YP.rotationDegrees((entity.level.getGameTime() + partialTicks) * 3));
                    this.itemRenderer.renderStatic(((ItemStackBindable) b).getItemStack(), ItemTransforms.TransformType.GROUND, packedLight, OverlayTexture.NO_OVERLAY, ps, buffer, entity.getId());
                    ps.popPose();
                }
            } else if (b instanceof IngredientBindable ingredientBindable) {
                ItemStack[] items = ingredientBindable.getIngredient().getItems();
                if (items.length != 0) {
                    // do something based on tick time
                }
            } else if (b instanceof EntityTypeBindable entityTypeBindable) {
                EntityType<?> entityType = entityTypeBindable.get();
                EntityRenderer<?> renderer = mc.getEntityRenderDispatcher().renderers.get(entityType);
                // idk what im doing here, something lol
            }
            ps.translate(-offset.getX(), -offset.getY(), -offset.getZ());
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