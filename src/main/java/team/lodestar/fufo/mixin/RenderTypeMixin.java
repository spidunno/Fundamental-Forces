package team.lodestar.fufo.mixin;

import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderType.class)
public class RenderTypeMixin {
    @Final
    @Shadow
    private static RenderType SOLID;

    @Final
    @Shadow
    private static RenderType CUTOUT;

    @Final
    @Shadow
    private static RenderType CUTOUT_MIPPED;

    @Inject(method = "cutout", at = @At("RETURN"), cancellable = true)
    private static void cutout(CallbackInfoReturnable<RenderType> cir) {
//        cir.setReturnValue(ImpactFramePostProcessor.shouldCutout ? CUTOUT : SOLID);
    }

    @Inject(method = "cutoutMipped", at = @At("RETURN"), cancellable = true)
    private static void cutoutMipped(CallbackInfoReturnable<RenderType> cir) {
//        cir.setReturnValue(ImpactFramePostProcessor.shouldCutout ? CUTOUT_MIPPED : SOLID);
    }
}
