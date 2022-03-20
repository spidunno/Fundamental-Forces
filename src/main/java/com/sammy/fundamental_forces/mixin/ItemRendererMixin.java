package com.sammy.fundamental_forces.mixin;

import com.sammy.fundamental_forces.core.systems.backstreet_hooks.BackstreetHooks;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

//    This shit basically just replaces the armor glint texture. 
//    @Inject(at = @At("HEAD"), method = "getArmorFoilBuffer", cancellable = true)
//    private static void fundamental_forcesArmorGlint(MultiBufferSource source, RenderType type, boolean armor, boolean hasGlint, CallbackInfoReturnable<VertexConsumer> cir) {
//        cir.setReturnValue(hasGlint ? VertexMultiConsumer.create(source.getBuffer(armor ? RenderTypes.TEST : RenderTypes.TEST2), source.getBuffer(type)) : source.getBuffer(type));
//    }
//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;armorEntityGlint()Lnet/minecraft/client/renderer/RenderType;"), method = "getArmorFoilBuffer")
//    private static RenderType fundamental_forcesEntityArmorGlint() {
//        return RenderTypes.TEST2;
//    }
//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;armorGlint()Lnet/minecraft/client/renderer/RenderType;"), method = "getArmorFoilBuffer")
//    private static RenderType fundamental_forcesArmorGlint() {
//        return RenderTypes.TEST;
//    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBuffers;bufferSource()Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;"), method = "renderGuiItem(Lnet/minecraft/world/item/ItemStack;IILnet/minecraft/client/resources/model/BakedModel;)V")
    private void callPreItemGuiRender(ItemStack pStack, int pX, int pY, BakedModel pBakedmodel, CallbackInfo ci) {
        BackstreetHooks.PRE_ITEM_GUI_RENDER.forEach(hook -> hook.onPreGuiItemRender(pStack, pX, pY, pBakedmodel));
    }
}