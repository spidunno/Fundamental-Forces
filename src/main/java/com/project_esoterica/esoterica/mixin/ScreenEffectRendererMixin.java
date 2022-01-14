package com.project_esoterica.esoterica.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.project_esoterica.esoterica.core.systems.meteorfire.MeteorFireHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {
    @Inject(method = "renderScreenEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z"))
    private static void esotericaMeteorFireRendering(Minecraft overlay, PoseStack pMinecraft, CallbackInfo ci)
    {
        MeteorFireHandler.ClientOnly.renderUIMeteorFire(overlay, pMinecraft);
    }
}
