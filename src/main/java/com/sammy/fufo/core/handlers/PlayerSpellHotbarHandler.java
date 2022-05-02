package com.sammy.fufo.core.handlers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.capability.FufoPlayerDataCapability;
import com.sammy.fufo.core.setup.client.KeyBindingRegistry;
import com.sammy.fufo.core.systems.magic.spell.SpellCooldownData;
import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import com.sammy.fufo.core.systems.magic.spell.hotbar.SpellHotbar;
import com.sammy.ortus.helpers.RenderHelper;
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
            if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
                FufoPlayerDataCapability.getCapability(serverPlayer).ifPresent(c -> {
                    if (c.hotbarHandler.open) {
                        SpellInstance selectedSpell = c.hotbarHandler.spellHotbar.getSelectedSpell(serverPlayer);
                        selectedSpell.castBlock(serverPlayer, event.getPos(), event.getHitVec());
                        selectedSpell.castCommon(serverPlayer);
                    }
                });
            }
        }
    }

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        FufoPlayerDataCapability.getCapability(player).ifPresent(c -> {
            PlayerSpellHotbarHandler handler = c.hotbarHandler;
            for (int i = 0; i < handler.spellHotbar.spells.size(); i++) {
                int selected = handler.spellHotbar.getSelectedSpellIndex(player);
                SpellInstance instance = handler.spellHotbar.spells.get(i);
                instance.selected = i == selected;
                instance.baseTick(player.level);
                if (player instanceof ServerPlayer serverPlayer) {
                    instance.playerTick(serverPlayer);
                }
            }
            if (event.player instanceof ServerPlayer serverPlayer) {
                if (handler.open && c.rightClickHeld) {
                    SpellInstance selectedSpell = handler.spellHotbar.getSelectedSpell(player);
                    selectedSpell.cast(serverPlayer);
                    selectedSpell.castCommon(serverPlayer);
                }
            }
        });
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
        public static final ResourceLocation ICONS_TEXTURE = FufoMod.prefix("textures/spell/hotbar.png");
        public static void moveOverlays(RenderGameOverlayEvent.Pre event) {
            if (event.getType().equals(RenderGameOverlayEvent.ElementType.ALL)) {
                Minecraft minecraft = Minecraft.getInstance();
                LocalPlayer player = minecraft.player;
                FufoPlayerDataCapability capability = FufoPlayerDataCapability.getCapability(player).orElse(new FufoPlayerDataCapability());
                float progress = Math.max(0, capability.hotbarHandler.animationProgress - 0.5f) * 2f;
                float offset = progress * 4;

                ((ForgeIngameGui) Minecraft.getInstance().gui).left_height += offset;
                ((ForgeIngameGui) Minecraft.getInstance().gui).right_height += offset;
            }
        }

        public static void clientTick(TickEvent.ClientTickEvent event) {
            Player player = Minecraft.getInstance().player;
            FufoPlayerDataCapability.getCapability(player).ifPresent(c -> {
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
            FufoPlayerDataCapability.getCapability(player).ifPresent(c -> {
                PlayerSpellHotbarHandler handler = c.hotbarHandler;
                handler.open = !handler.open;
                handler.updateCachedSlot = true;
                FufoPlayerDataCapability.syncServer(player);
            });
        }

        public static float itemHotbarOffset() {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            FufoPlayerDataCapability capability = FufoPlayerDataCapability.getCapability(player).orElse(new FufoPlayerDataCapability());
            float progress = (capability.hotbarHandler.animationProgress) * 2f;
            return progress * 45;
        }

        public static boolean moveVanillaUI(boolean reverse, PoseStack poseStack) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            FufoPlayerDataCapability capability = FufoPlayerDataCapability.getCapability(player).orElse(new FufoPlayerDataCapability());
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
                FufoPlayerDataCapability.getCapability(player).ifPresent(c -> {
                    if (c.hotbarHandler.animationProgress >= 0.5f) {
                        PoseStack poseStack = event.getMatrixStack();
                        poseStack.pushPose();
                        float progress = Math.max(0, c.hotbarHandler.animationProgress - 0.5f) * 2f;
                        float offset = (1 - progress) * 45;
                        int left = event.getWindow().getGuiScaledWidth() / 2 - 109;
                        int top = event.getWindow().getGuiScaledHeight() - 31;
                        int slot = player.getInventory().selected;
                        poseStack.translate(0, offset, 0);
                        RenderSystem.enableBlend();
                        RenderSystem.setShaderTexture(0, ICONS_TEXTURE);
                        RenderHelper.blit(poseStack, left, top, 218, 28, 0, 0, 256f);

                        RenderHelper.blit(poseStack, left + slot * 24 - 1, top - 1, 28, 30, 0, 28, 256f);
                        for (int i = 0; i < c.hotbarHandler.spellHotbar.size; i++) {
                            SpellInstance instance = c.hotbarHandler.spellHotbar.spells.get(i);
                            if (!instance.isEmpty()) {
                                ResourceLocation background = instance.type.getBackgroundLocation();
                                ResourceLocation icon = instance.type.getIconLocation();
                                RenderSystem.setShaderTexture(0, background);
                                RenderHelper.blit(poseStack, left + i * 24 + 3, top + 3, 20, 22, 0, 0, 20, 22);
                                RenderSystem.setShaderTexture(0, icon);
                                RenderHelper.blit(poseStack, left + i * 24 + 3, top + 3, 20, 22, 0, 0, 20, 22);
                            }
                        }
                        RenderSystem.setShaderTexture(0, ICONS_TEXTURE);
                        for (int i = 0; i < c.hotbarHandler.spellHotbar.size; i++) {
                            SpellInstance instance = c.hotbarHandler.spellHotbar.spells.get(i);
                            if (!instance.isEmpty() && instance.getFade() > 0) {
                                RenderHelper.blit(poseStack, left + i * 24 + 3, top + 3, 20, 22, 1f, 1f, 1f, instance.getFade(), 28, 28, 256f);
                            }
                            if (SpellCooldownData.isOnCooldown(instance.cooldown)) {
                                int cooldownOffset = (int) (22 * instance.cooldown.getProgress());
                                RenderHelper.blit(poseStack, left + i * 24 + 3, top + 3+cooldownOffset, 20, 22-cooldownOffset, 1f, 1f, 1f, 0.5f, 28, 28 + cooldownOffset, 256f);
                            }
                        }
                        RenderSystem.disableBlend();
                        poseStack.popPose();
                    }
                });
            }
        }
    }
}