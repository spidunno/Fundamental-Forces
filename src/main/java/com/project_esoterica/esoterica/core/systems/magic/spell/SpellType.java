package com.project_esoterica.esoterica.core.systems.magic.spell;

import com.project_esoterica.esoterica.core.helper.DataHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;

public class SpellType {

    public final String id;

    public SpellType(String id) {
        this.id = id;
    }

    public void castBlock(SpellInstance instance, ServerPlayer player, BlockPos pos, BlockHitResult hitVec) {
        player.sendMessage(new TextComponent(instance.type.id+"-block"), player.getUUID());
    }
    public void cast(SpellInstance instance, ServerPlayer player) {
        player.sendMessage(new TextComponent(instance.type.id+"-air"), player.getUUID());
    }

    public ResourceLocation getIconLocation() {
        return DataHelper.prefix("textures/spell/icon/" + id + ".png");
    }

    public ResourceLocation getBackgroundLocation() {
        return DataHelper.prefix("textures/spell/background/" + id + "_background.png");
    }
}