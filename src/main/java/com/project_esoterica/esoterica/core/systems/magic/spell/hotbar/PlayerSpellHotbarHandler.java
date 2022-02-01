package com.project_esoterica.esoterica.core.systems.magic.spell.hotbar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.project_esoterica.esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.esoterica.core.helper.DataHelper;
import com.project_esoterica.esoterica.core.setup.client.KeyBindingRegistry;
import com.project_esoterica.esoterica.core.systems.magic.spell.SpellInstance;
import com.project_esoterica.esoterica.core.systems.rendering.RenderUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class PlayerSpellHotbarHandler {
    public final SpellHotbar spellHotbar;
    public boolean open;
    public boolean unlockedSpellHotbar = true;
    public float animationProgress;
    public int cachedSlot;
    public boolean updateCachedSlot;

    public PlayerSpellHotbarHandler(SpellHotbar spellHotbar) {
        this.spellHotbar = spellHotbar;
    }

    public static void playerInteract(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand().equals(InteractionHand.MAIN_HAND)) {
            if (event.getPlayer() instanceof ServerPlayer player) {
                PlayerDataCapability.getCapability(player).ifPresent(c -> {
                    if (c.hotbarHandler.open) {
                        SpellInstance selectedSpell = c.hotbarHandler.spellHotbar.getSelectedSpell(player);
                        selectedSpell.castBlock(player, event.getPos(), event.getHitVec());
                    }
                });
            }
        }
    }

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer player) {
            PlayerDataCapability.getCapability(player).ifPresent(c -> {
                c.hotbarHandler.spellHotbar.spells.forEach(SpellInstance::tick);
                if (c.hotbarHandler.open && c.rightClickHeld) {
                    SpellInstance selectedSpell = c.hotbarHandler.spellHotbar.getSelectedSpell(player);
                    selectedSpell.cast(player);
                }
            });
        }
    }


    public CompoundTag serializeNBT(CompoundTag tag) {
        if (unlockedSpellHotbar) {
            tag.putBoolean("unlockedSpellHotbar", true);
            tag.putBoolean("spellHotbarOpen", open);
            spellHotbar.serializeNBT(tag);
        }
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("unlockedSpellHotbar")) {
            unlockedSpellHotbar = true;
            open = tag.getBoolean("spellHotbarOpen");
            animationProgress = open ? 1 : 0;
            spellHotbar.deserializeNBT(tag);
        }
    }

    public static class ClientOnly {
        private static final ResourceLocation ICONS_TEXTURE = DataHelper.prefix("textures/spell/hotbar.png");

        public static void moveOverlays(RenderGameOverlayEvent.Pre event) {
            if (event.getType().equals(RenderGameOverlayEvent.ElementType.ALL)) {
                Minecraft minecraft = Minecraft.getInstance();
                LocalPlayer player = minecraft.player;
                PlayerDataCapability capability = PlayerDataCapability.getCapability(player).orElse(new PlayerDataCapability());
                float progress = Math.max(0, capability.hotbarHandler.animationProgress - 0.5f) * 2f;
                float offset = progress * 4;

                ((ForgeIngameGui) Minecraft.getInstance().gui).left_height += offset;
                ((ForgeIngameGui) Minecraft.getInstance().gui).right_height += offset;
            }
        }

        public static void clientTick(TickEvent.ClientTickEvent event) {
            Player player = Minecraft.getInstance().player;
            PlayerDataCapability.getCapability(player).ifPresent(c -> {
                PlayerSpellHotbarHandler handler = c.hotbarHandler;
                float desired = handler.open ? 1 : 0;
                handler.animationProgress = Mth.lerp(0.2f, handler.animationProgress, desired);
                if (handler.updateCachedSlot) {
                    if ((handler.open && handler.animationProgress > 0.5f) || (!handler.open && handler.animationProgress < 0.5f)) {
                        int previousSlot = handler.cachedSlot;
                        handler.cachedSlot = player.getInventory().selected;
                        handler.updateCachedSlot = false;
                        player.getInventory().selected = previousSlot;
                    }
                }
                if (KeyBindingRegistry.swapHotbar.consumeClick()) {
                    PlayerSpellHotbarHandler.ClientOnly.swapHotbar();
                }
            });
        }

        public static void swapHotbar() {
            Player player = Minecraft.getInstance().player;
            PlayerDataCapability.getCapability(player).ifPresent(c -> {
                PlayerSpellHotbarHandler handler = c.hotbarHandler;
                handler.open = !handler.open;
                handler.updateCachedSlot = true;
                PlayerDataCapability.syncServer(player);
            });
        }

        public static float itemHotbarOffset() {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            PlayerDataCapability capability = PlayerDataCapability.getCapability(player).orElse(new PlayerDataCapability());
            float progress = (capability.hotbarHandler.animationProgress) * 2f;
            return progress * 45;
        }

        public static boolean moveVanillaUI(boolean reverse, PoseStack poseStack) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            PlayerDataCapability capability = PlayerDataCapability.getCapability(player).orElse(new PlayerDataCapability());
            boolean visible = capability.hotbarHandler.animationProgress >= 0.5f;
            if (!visible) {
                poseStack.translate(0, itemHotbarOffset() * (reverse ? -1 : 1), 0);
            }
            return visible;
        }

        public static void renderSpellHotbar(RenderGameOverlayEvent.Post event) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && !player.isSpectator()) {
                PlayerDataCapability.getCapability(player).ifPresent(c -> {
                    if (c.hotbarHandler.animationProgress >= 0.5f) {
                        PoseStack poseStack = event.getMatrixStack();
                        float progress = Math.max(0, c.hotbarHandler.animationProgress - 0.5f) * 2f;
                        float offset = (1 - progress) * 45;
                        int left = event.getWindow().getGuiScaledWidth() / 2 - 109;
                        int top = event.getWindow().getGuiScaledHeight() - 31;
                        int slot = player.getInventory().selected;
                        poseStack.pushPose();
                        poseStack.translate(0, offset, 0);
                        RenderSystem.setShaderTexture(0, ICONS_TEXTURE);
                        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                        RenderUtilities.blit(poseStack, left, top, 218, 28, 1, 1, 256f);

                        RenderUtilities.blit(poseStack, left + slot * 24 - 1, top - 1, 28, 30, 1, 30, 256f);
                        for (int i = 0; i < c.hotbarHandler.spellHotbar.size; i++) {
                            SpellInstance instance = c.hotbarHandler.spellHotbar.spells.get(i);
                            if (!instance.isEmpty()) {
                                ResourceLocation background = instance.type.getBackgroundLocation();
                                ResourceLocation icon = instance.type.getIconLocation();
                                RenderSystem.setShaderTexture(0, background);
                                RenderUtilities.blit(poseStack, left + i * 24 + 3, top + 3, 20, 22, 0, 0, 20, 22);
                                RenderSystem.setShaderTexture(0, icon);
                                RenderUtilities.blit(poseStack, left + i * 24 + 3, top + 3, 20, 22, 0, 0, 20, 22);
                            }
                        }
                        poseStack.popPose();
                    }
                });
            }
        }
    }
}