package com.sammy.fundamental_forces.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.fundamental_forces.core.handlers.ScreenParticleHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.sammy.fundamental_forces.core.systems.rendering.screenparticle.base.ScreenParticle.RenderOrder.BEFORE_TOOLTIPS;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {
    @Inject(at = @At("HEAD"), method = "renderTooltip")
    private void fundamentalForcesBeforeTooltipParticleMixin(PoseStack pPoseStack, int pX, int pY, CallbackInfo ci) {
        ScreenParticleHandler.renderParticles(BEFORE_TOOLTIPS);
    }
}