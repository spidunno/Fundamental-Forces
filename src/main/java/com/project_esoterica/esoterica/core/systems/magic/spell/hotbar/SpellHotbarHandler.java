package com.project_esoterica.esoterica.core.systems.magic.spell.hotbar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.project_esoterica.esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.esoterica.core.helper.DataHelper;
import com.project_esoterica.esoterica.core.systems.rendering.RenderUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;

public class SpellHotbarHandler {

    public static class ClientOnly {
        private static final ResourceLocation ICONS_TEXTURE = DataHelper.prefix("textures/spell/hotbar.png");

        public static void renderSpellHotbar(RenderGameOverlayEvent.Post event) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
                PlayerDataCapability.getCapability(player).ifPresent(c -> {
                    PoseStack poseStack = event.getMatrixStack();

                    int left = event.getWindow().getGuiScaledWidth() / 2 - 109;
                    int top = event.getWindow().getGuiScaledHeight() - ((ForgeIngameGui) Minecraft.getInstance().gui).left_height;
                    int slot = player.getInventory().selected;
                    poseStack.pushPose();
                    RenderSystem.setShaderTexture(0, ICONS_TEXTURE);
                    RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                    RenderUtilities.blit(poseStack, left, top, 218, 28, 1, 1, 218, 28, 256f);

                    RenderUtilities.blit(poseStack, left+slot*24-1, top-1, 28, 30, 1, 30, 28, 30, 256f);
                    poseStack.popPose();
                });
            }
        }
    }
}