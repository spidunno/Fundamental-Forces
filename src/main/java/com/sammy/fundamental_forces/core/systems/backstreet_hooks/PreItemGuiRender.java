package com.sammy.fundamental_forces.core.systems.backstreet_hooks;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

public interface PreItemGuiRender {
    void onPreGuiItemRender(ItemStack pStack, int pX, int pY, BakedModel model);
}
