package com.sammy.fufo.core.systems.magic.spell;

import com.sammy.fufo.FufoMod;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;

public class SpellType {

    public final String id;

    public SpellType(String id) {
        this.id = id;
    }

    public void castBlock(SpellInstance instance, ServerPlayer player, BlockPos pos, BlockHitResult hitVec) {

    }

    public void cast(SpellInstance instance, ServerPlayer player) {

    }

    public void castCommon(SpellInstance instance, ServerPlayer player) {

    }

    public ResourceLocation getIconLocation() {
        return FufoMod.fufoPath("textures/spell/icon/" + id + ".png");
    }

    public ResourceLocation getBackgroundLocation() {
        return FufoMod.fufoPath("textures/spell/background/" + id + "_background.png");
    }
}