package com.project_esoterica.esoterica.mixin;

import net.minecraft.client.renderer.entity.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

//    This shit basically just replaces the armor glint texture. 
//    @Inject(at = @At("HEAD"), method = "getArmorFoilBuffer", cancellable = true)
//    private static void esotericaArmorGlint(MultiBufferSource source, RenderType type, boolean armor, boolean hasGlint, CallbackInfoReturnable<VertexConsumer> cir) {
//        cir.setReturnValue(hasGlint ? VertexMultiConsumer.create(source.getBuffer(armor ? RenderTypes.TEST : RenderTypes.TEST2), source.getBuffer(type)) : source.getBuffer(type));
//    }
//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;armorEntityGlint()Lnet/minecraft/client/renderer/RenderType;"), method = "getArmorFoilBuffer")
//    private static RenderType esotericaEntityArmorGlint() {
//        return RenderTypes.TEST2;
//    }
//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;armorGlint()Lnet/minecraft/client/renderer/RenderType;"), method = "getArmorFoilBuffer")
//    private static RenderType esotericaArmorGlint() {
//        return RenderTypes.TEST;
//    }
}