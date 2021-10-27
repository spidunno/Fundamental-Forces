package com.project_esoterica.esoterica.mixin;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.project_esoterica.esoterica.core.config.ClientConfig;
import com.project_esoterica.esoterica.core.systems.rendering.RenderTypes;
import com.project_esoterica.esoterica.core.systems.screenshake.ScreenshakeHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.project_esoterica.esoterica.EsotericaHelper.RANDOM;

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