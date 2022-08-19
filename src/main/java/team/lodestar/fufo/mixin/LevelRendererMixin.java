package team.lodestar.fufo.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import team.lodestar.fufo.registry.client.FufoPostProcessingEffects;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Inject(method = "renderChunkLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ShaderInstance;apply()V"))
    private void injectionBeforeChunkShaderApply(RenderType pRenderType, PoseStack pPoseStack, double pCamX, double pCamY, double pCamZ, Matrix4f pProjectionMatrix, CallbackInfo ci) {
        if (!FufoPostProcessingEffects.IMPACT_FRAME.shouldCutout) {
            if (pRenderType == RenderType.cutout() || pRenderType == RenderType.cutoutMipped()) {
                ShaderInstance shader = RenderSystem.getShader();
                if (shader.COLOR_MODULATOR != null) {
                    float[] orgCol = RenderSystem.getShaderColor();
                    shader.COLOR_MODULATOR.set(new float[]{orgCol[0], orgCol[1], orgCol[2], Float.POSITIVE_INFINITY});
                }
            }
        }
    }
}
