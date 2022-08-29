package team.lodestar.fufo.client.rendering.entity.weave;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.entity.weave.AbstractWeaveEntity;
import team.lodestar.fufo.core.weaving.Bindable;
import team.lodestar.fufo.core.weaving.Weave;
import team.lodestar.fufo.core.weaving.recipe.EntityTypeBindable;
import team.lodestar.fufo.core.weaving.recipe.IngredientBindable;
import team.lodestar.fufo.core.weaving.recipe.ItemStackBindable;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
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
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class AbstractWeaveEntityRenderer extends EntityRenderer<AbstractWeaveEntity> {

    private static final ResourceLocation TEST_BEAM = FufoMod.fufoPath("textures/vfx/light_trail.png");
    private static final RenderType TEST_BEAM_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.apply(TEST_BEAM);
    public Color beamColor = new Color(0xFF0000);
    private final ItemRenderer itemRenderer;

    public AbstractWeaveEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    // TODO: add lines between items, constraints for all bindables to be within the 3x3x3 cube
    public void render(AbstractWeaveEntity entity, float yaw, float partialTicks, PoseStack ps, MultiBufferSource buffer, int packedLight) {
        Minecraft mc = Minecraft.getInstance();
        super.render(entity, yaw, partialTicks, ps, buffer, packedLight);
        ps.pushPose();
        Weave<?> weave = entity.weave;
        ps.translate(0, 0.3D, 0);
        int i = 0;
        for (Bindable b : weave.getBindables()) {
            Vec3i offset = b.getLocation();
            ps.translate(offset.getX(), offset.getY(), offset.getZ());
            float fac = entity.tickCount + partialTicks + (5 * i);
            // TODO: scale & offset these based on the size of the bindable
            if (b instanceof ItemStackBindable itemStackBindable) {
                if (!itemStackBindable.getItemStack().isEmpty()) {
                    ps.pushPose();
                    ps.scale(0.9f, 0.9f, 0.9f);
                    ps.mulPose(Vector3f.YP.rotationDegrees(fac * 3));

                    this.itemRenderer.renderStatic(((ItemStackBindable) b).getItemStack(), ItemTransforms.TransformType.GROUND, packedLight, OverlayTexture.NO_OVERLAY, ps, buffer, entity.getId());
                    ps.popPose();
                }
                // TODO: scale & offset these based on the size of the bindable
            } else if (b instanceof IngredientBindable ingredientBindable) {
                ItemStack[] items = ingredientBindable.getIngredient().getItems();
                if (items.length != 0) {
                    ps.pushPose();
                    ps.translate(0, -0.05D, 0);
                    ps.scale(0.9f, 0.9f, 0.9f);
                    ps.mulPose(Vector3f.YP.rotationDegrees(fac * 3));
                    this.itemRenderer.renderStatic(items[(entity.tickCount / 20) % items.length], ItemTransforms.TransformType.GROUND, packedLight, OverlayTexture.NO_OVERLAY, ps, buffer, entity.getId());
                    ps.popPose();
                }
            } else if (b instanceof EntityTypeBindable entityTypeBindable) {
                ps.pushPose();
                EntityType<?> entityType = entityTypeBindable.get();
                Vec3 size = new Vec3(b.size().getX() == 1 ? 0 : b.size().getX(), b.size().getY() == 1 ? 0 : b.size().getY(), b.size().getZ() == 1 ? 0 : b.size().getZ());
                ps.scale(0.3f * b.size().getX(), 0.3f * b.size().getY(), 0.3f * b.size().getZ());
                ps.translate((float) size.x() / 2, (float) size.y() / 2, (float) size.z() / 2);
                ps.translate(0, -(entityType.getHeight() > 1 ? entityType.getHeight() / 3 : 0), 0);
                ps.mulPose(Vector3f.YP.rotationDegrees(fac * 3));
                mc.getEntityRenderDispatcher().render(entityType.create(entity.level), 0, 0, 0, 0, 0, ps, buffer, packedLight);
                ps.popPose();
            }
            ps.translate(-offset.getX(), -offset.getY(), -offset.getZ());
            i++;
        }
        // TODO: fix this to not use shimmer!
        /*weave.getLinks().forEach((link, type) -> {
            ps.pushPose();
            ps.translate(link.getFirst().getX(), link.getFirst().getY() + 0.1, link.getFirst().getZ());
            VertexConsumer vc = buffer.getBuffer(TEST_BEAM_TYPE);
            VFXBuilders.WorldVFXBuilder finalC = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(beamColor).renderBeam(vc, ps, new Vec3(link.getFirst().getX(), link.getFirst().getY(), link.getFirst().getZ()), new Vec3(link.getSecond().getX(), link.getSecond().getY(), link.getSecond().getZ()), 0.1f);
            ps.translate(-link.getFirst().getX(), -link.getFirst().getY() - 0.1, -link.getFirst().getZ());
            ps.popPose();
        });*/
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