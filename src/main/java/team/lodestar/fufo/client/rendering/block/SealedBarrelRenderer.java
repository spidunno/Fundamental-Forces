package team.lodestar.fufo.client.rendering.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.fluid.PipeNodeBlockEntity;
import team.lodestar.fufo.common.fluid.sealed_barrel.SealedBarrelBlockEntity;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.awt.*;
import java.util.function.Function;

import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;

public class SealedBarrelRenderer<T extends SealedBarrelBlockEntity> extends AnchorRenderer<T> {

    public static RenderType FLUID_TYPE = LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.applyAndCache(InventoryMenu.BLOCK_ATLAS);

    public SealedBarrelRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(T blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        FluidStack storedFluid = blockEntityIn.getStoredFluid();
        if (storedFluid != null && !storedFluid.isEmpty()) { //TODO: write a dedicated FluidRenderer helper class for this
            Minecraft minecraft = Minecraft.getInstance();
            Fluid fluid = storedFluid.getFluid();
            IClientFluidTypeExtensions clientFluid = IClientFluidTypeExtensions.of(fluid);
            Function<ResourceLocation, TextureAtlasSprite> spriteAtlas = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS);
            TextureAtlasSprite flowTexture = spriteAtlas.apply(clientFluid.getFlowingTexture(storedFluid)); //TODO: flowing textures for some reason are 2x2 tiled, write some code that will shrink it properly.
            TextureAtlasSprite stillTexture = spriteAtlas.apply(clientFluid.getStillTexture(storedFluid));
            Color color = ColorHelper.getColor(clientFluid.getTintColor(fluid.defaultFluidState(), minecraft.level, blockEntityIn.getPos()));
            double fullness = blockEntityIn.getFluidAmount() / blockEntityIn.getCapacity();

            poseStack.pushPose();
            poseStack.translate(0.5, 0.05f + fullness * 0.85f, 0.5);
            float size = 0.5f;
            Vector3f[] positions = new Vector3f[]{new Vector3f(-size, 0, size), new Vector3f(size, 0, size), new Vector3f(size, 0, -size), new Vector3f(-size, 0, -size)};
            VFXBuilders.createWorld()
                    .setColorWithAlpha(color)
                    .setPosColorTexLightmapDefaultFormat()
                    .setUV(stillTexture)
                    .renderQuad(DELAYED_RENDER.getBuffer(FLUID_TYPE), poseStack, positions);

            poseStack.popPose();
        }
        super.render(blockEntityIn, partialTicks, poseStack, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}