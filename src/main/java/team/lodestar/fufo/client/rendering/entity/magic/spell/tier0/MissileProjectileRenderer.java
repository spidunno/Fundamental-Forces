package team.lodestar.fufo.client.rendering.entity.magic.spell.tier0;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.entity.magic.spell.tier1.SpellBolt;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;

public class MissileProjectileRenderer extends EntityRenderer<SpellBolt> {

    private static final ResourceLocation TEST_BEAM = FufoMod.fufoPath("textures/vfx/uv_test.png");
    private static final RenderType TEST_BEAM_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.apply(TEST_BEAM);

    public MissileProjectileRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(SpellBolt pEntity) {
        return null;
    }

    @Override
    public void render(SpellBolt pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {

        pPoseStack.pushPose();
        pPoseStack.translate(0,1,0);
        VertexConsumer consumer = DELAYED_RENDER.getBuffer(TEST_BEAM_TYPE);
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(pEntity.getLevel().getGameTime() * 10));
        VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setOffset(0.5f, 1.5f, 0.5f).renderSphere(consumer, pPoseStack, 1, 6, 6);
        pPoseStack.popPose();

        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }
}
