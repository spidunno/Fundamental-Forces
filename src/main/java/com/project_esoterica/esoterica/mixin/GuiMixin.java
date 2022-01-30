package com.project_esoterica.esoterica.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.project_esoterica.esoterica.core.systems.magic.spell.hotbar.SpellHotbarHandler;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(method = "renderHotbar", at=@At("HEAD"))
    private void esotericaDownwardsHotbarOffset(float partialTicks, PoseStack poseStack, CallbackInfo ci)
    {
        SpellHotbarHandler.ClientOnly.moveItemHotbar(false, partialTicks, poseStack);
    }
    @Inject(method = "renderHotbar", at=@At("RETURN"))
    private void esotericaDownwardsHotbarOffsetPartTwo(float partialTicks, PoseStack poseStack, CallbackInfo ci)
    {
        SpellHotbarHandler.ClientOnly.moveItemHotbar(true, partialTicks, poseStack);
    }
}
