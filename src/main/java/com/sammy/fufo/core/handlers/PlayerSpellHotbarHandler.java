package com.sammy.fufo.core.handlers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.capability.FufoPlayerDataCapability;
import com.sammy.fufo.core.setup.client.KeyBindingRegistry;
import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import com.sammy.fufo.core.systems.magic.spell.hotbar.SpellHotbar;
import com.sammy.ortus.capability.OrtusPlayerDataCapability;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
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

//Whoever programmed this class must really like cock and balls like a lot
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
                FufoPlayerDataCapability.getCapabilityOptional(serverPlayer).ifPresent(c -> {
                    if (c.hotbarHandler.open) {
                        SpellInstance selectedSpell = c.hotbarHandler.spellHotbar.getSelectedSpell(serverPlayer);
                        if (!selectedSpell.isEmpty()) {
                            selectedSpell.cast(serverPlayer, event.getPos(), event.getHitVec());
                        }
                    }
                });
            }
        }
    }

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        FufoPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> {
            PlayerSpellHotbarHandler handler = c.hotbarHandler;
            for (int i = 0; i < handler.spellHotbar.spells.size(); i++) {
                int selected = handler.spellHotbar.getSelectedSpellIndex(player);
                SpellInstance instance = handler.spellHotbar.spells.get(i);
                instance.selected = i == selected;
                if (!instance.isEmpty()) {
                    instance.baseTick(player.level);
                    if (player instanceof ServerPlayer serverPlayer) {
                        instance.playerTick(serverPlayer);
                    }
                }
            }
            //TODO: this needs alternative behaviour assuming a player were to trigger the BlockHitResult cast event.
            // The reason for this is that, on a spell like force orb, when a player holds right click, this code runs first, sets the cooldown, executes the spell (does nothing in this case)
            // And only then, the block cast event triggers, and well, the spell is on cooldown, so it fails to cast.
            // There's other ways to get around this, but just performing a raycast here might be best.
            if (event.player instanceof ServerPlayer serverPlayer) {
                if (handler.open && OrtusPlayerDataCapability.getCapability(player).rightClickHeld) {
                    SpellInstance selectedSpell = handler.spellHotbar.getSelectedSpell(player);
                    if (!selectedSpell.isEmpty()) {
                        selectedSpell.cast(serverPlayer);
                    }
                }
            }
        });
    }


    public CompoundTag serializeNBT(CompoundTag tag) {
        CompoundTag spellTag = new CompoundTag();
        if (unlockedSpellHotbar) {
            spellTag.putBoolean("unlockedSpellHotbar", true);
            spellTag.putBoolean("spellHotbarOpen", open);
            spellHotbar.serializeNBT(spellTag);
        }
        tag.put("spellData", spellTag);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        CompoundTag spellTag = tag.getCompound("spellData");

        if (spellTag.contains("unlockedSpellHotbar")) {
            unlockedSpellHotbar = true;
            open = spellTag.getBoolean("spellHotbarOpen");
            spellHotbar.deserializeNBT(spellTag);
        }
    }

    public static class ClientOnly {
        public static final ResourceLocation ICONS_TEXTURE = FufoMod.fufoPath("textures/spell/hotbar.png");

        public static void moveOverlays(RenderGameOverlayEvent.Pre event) {
            if (event.getType().equals(RenderGameOverlayEvent.ElementType.ALL)) {
                Minecraft minecraft = Minecraft.getInstance();
                LocalPlayer player = minecraft.player;
                FufoPlayerDataCapability capability = FufoPlayerDataCapability.getCapability(player);
                float progress = Math.max(0, capability.hotbarHandler.animationProgress - 0.5f) * 2f;
                float offset = progress * 4;

                ((ForgeIngameGui) Minecraft.getInstance().gui).left_height += offset;
                ((ForgeIngameGui) Minecraft.getInstance().gui).right_height += offset;
            }
        }

        public static void clientTick(TickEvent.ClientTickEvent event) {
            Player player = Minecraft.getInstance().player;
            FufoPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> {
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
            FufoPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> {
                PlayerSpellHotbarHandler handler = c.hotbarHandler;
                handler.open = !handler.open;
                handler.updateCachedSlot = true;
                FufoPlayerDataCapability.syncServer(player);
            });
        }

        public static float itemHotbarOffset() {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            float progress = (FufoPlayerDataCapability.getCapability(player).hotbarHandler.animationProgress) * 2f;
            return progress * 45;
        }

        public static boolean moveVanillaUI(boolean reverse, PoseStack poseStack) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            FufoPlayerDataCapability capability = FufoPlayerDataCapability.getCapability(player);
            boolean visible = capability.hotbarHandler.animationProgress >= 0.5f;
            if (!visible) {
                poseStack.translate(0, itemHotbarOffset() * (reverse ? -1 : 1), 0);
            }
            return visible;
        }

        public static Tesselator spellTesselator = new Tesselator();

        public static void renderSpellHotbar(RenderGameOverlayEvent.Post event) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && !player.isSpectator()) {
                FufoPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> {
                    if (c.hotbarHandler.animationProgress >= 0.5f) {
                        float progress = Math.max(0, c.hotbarHandler.animationProgress - 0.5f) * 2f;
                        float offset = (1 - progress) * 45;
                        int left = event.getWindow().getGuiScaledWidth() / 2 - 109;
                        int top = event.getWindow().getGuiScaledHeight() - 31;
                        int slot = player.getInventory().selected;
                        PoseStack poseStack = event.getMatrixStack();
                        poseStack.pushPose();
                        poseStack.translate(0, offset, 0);
                        RenderSystem.enableBlend();

                        VFXBuilders.ScreenVFXBuilder barBuilder = VFXBuilders.createScreen().setPosColorTexDefaultFormat().setShader(GameRenderer::getPositionColorTexShader).setShaderTexture(ICONS_TEXTURE);
                        VFXBuilders.ScreenVFXBuilder spellBuilder = VFXBuilders.createScreen().setPosTexDefaultFormat().overrideBufferBuilder(spellTesselator.getBuilder());
                        barBuilder.setUVWithWidth(0, 0, 218, 28, 256f).setPositionWithWidth(left, top, 218, 28).draw(poseStack);
                        barBuilder.setUVWithWidth(0, 28, 28, 30, 256f).setPositionWithWidth(left + slot * 24 - 1, top - 1, 28, 30).draw(poseStack);

                        barBuilder.setUVWithWidth(28, 28, 20, 22, 256f);
                        for (int i = 0; i < c.hotbarHandler.spellHotbar.size; i++) {
                            SpellInstance instance = c.hotbarHandler.spellHotbar.spells.get(i);
                            if (!instance.isEmpty()) {
                                ResourceLocation background = instance.spellType.getBackgroundLocation();
                                ResourceLocation icon = instance.spellType.getIconLocation();
                                int x = left + i * 24 + 3;
                                int y =  top + 3;

                                spellBuilder.setPositionWithWidth(x, y, 20, 22);
                                spellBuilder.setShaderTexture(background).draw(poseStack);
                                spellBuilder.setShaderTexture(icon).draw(poseStack);

                                barBuilder.setPositionWithWidth(x, y, 20, 22);

                                if (instance.getIconFadeout() > 0) {
                                    barBuilder.setAlpha(instance.getIconFadeout()).draw(poseStack);
                                }
                                if (instance.isOnCooldown()) {
                                    int cooldownOffset = (int) (22 * instance.cooldown.getProgress());
                                    barBuilder.setPositionWithWidth(x, y+cooldownOffset, 20, 22-cooldownOffset).setUVWithWidth(28, 28 + cooldownOffset, 20, 22 - cooldownOffset, 256f).setAlpha(0.5f).draw(poseStack).setUVWithWidth(28, 28, 20, 22, 256f);
                                }
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